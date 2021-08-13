package com.sappenin.fastpay.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.primitives.UnsignedLong;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import com.sappenin.fastpay.core.CertifiedTransferOrder;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.UserData;
import com.sappenin.fastpay.core.authority.AuthorityUtils;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.bincode.AccountInfoResponse;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import com.sappenin.fastpay.core.messages.CetifiedTransferOrder;
import com.sappenin.fastpay.core.messages.TransferOrder;
import com.sappenin.fastpay.core.serde.BincodeSerdeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.DatagramPacket;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.resources.LoopResources;
import reactor.netty.udp.UdpClient;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A default instance of {@link AuthorityClient} that supports UDP and TCP interactions.
 */
public class DefaultAuthorityClient implements AuthorityClient {

  private final ClientNetworkOptions clientNetworkOptions;
  private final AuthorityClientState clientState;
  private final List<CetifiedTransferOrder> sentCertificates;
  private final LoopResources resources;

  /**
   * Required-args constructor.
   *
   * @param networkOptions An instance of {@link ClientNetworkOptions}.
   * @param clientState    An instance of {@link AuthorityClientState}.
   */
  public DefaultAuthorityClient(
    final ClientNetworkOptions networkOptions,
    final AuthorityClientState clientState
  ) {
    this.clientNetworkOptions = Objects.requireNonNull(networkOptions);
    this.clientState = Objects.requireNonNull(clientState);

    this.sentCertificates = Lists.newArrayList();
    this.resources = LoopResources.create("fastpayUdpClient");
  }

  @Override
  public Mono<CertifiedTransferOrder> transferToFastpayAccount(
    final UnsignedLong amount,
    final FastPayAddress recipient,
    final UserData userData) {

    Objects.requireNonNull(amount);
    Objects.requireNonNull(recipient);
    Objects.requireNonNull(userData);

    this.getSpendableAmount();
    // TODO: FIXME
    return null;
  }

  // This is called handleAccountInfoRequest in Rust
  @Override
  public Mono<AccountInfoResponse> getAccountInfo(final AccountInfoRequest accountInfoRequest) {
    Objects.requireNonNull(accountInfoRequest);

    try {
      final int shardNumber = AuthorityUtils.deriveShardNumber(
        clientNetworkOptions.numShards(),
        FastPayAddress.builder()
          .edPublicKey(Ed25519PublicKey.fromBase64(BincodeSerdeUtils.toHexString(accountInfoRequest.sender.value)))
          .build()
      );

      return Mono
        .just(
          // TODO: consider inverting this. Even though the accountInfoRequest can do its own serialization, we might want
          //  to pass this object to a service for future separation of concerns (e.g, to support alternate encodings)
          accountInfoRequest.bincodeSerialize()
        )
        .map(Unpooled::copiedBuffer)
        .map(requestByteBuf -> this.sendAndReceiveBytes(shardNumber, requestByteBuf))
        .map(byteBuf -> {
          try {
            // TODO: Detect AccountInfoResponse vs FastpayError (see tuple comment below).
            return AccountInfoResponse.bincodeDeserialize(byteBuf.array());
          } catch (DeserializationError e) {
            throw new RuntimeException(e.getMessage(), e);
          } finally {
            byteBuf.release();
          }
        });
    } catch (SerializationError e) {
      return Mono.error(new RuntimeException(e.getMessage(), e));
    }
  }

  // TODO: Add a FastpayError to the response as a Tuple?

  private ByteBuf sendAndReceiveBytes(
    final int destinationShardNum,
    final ByteBuf requestByteBuf
  ) {
    Objects.requireNonNull(requestByteBuf);

    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<ByteBuf> resultByteBuf = new AtomicReference<>();

    switch (this.clientNetworkOptions.networkProtocol()) {
      case UDP: {
        // TODO: Create a connection pool.
        // TODO: Inject the UdpClient.

//        final Mono<? extends Connection> connection = UdpClient.create()
        Connection connection = UdpClient.create()
          // TODO: consider making host/port an object so that the port computation is only ever done in one spot.
          .host(this.clientNetworkOptions.baseAddress())
          .port(this.clientNetworkOptions.basePort() + destinationShardNum)
          .runOn(resources)
          .option(
            ChannelOption.CONNECT_TIMEOUT_MILLIS,
            Long.valueOf(this.clientNetworkOptions.sendTimeout().toMillis()).intValue()
          )
          .option(
            ChannelOption.SO_TIMEOUT,
            Long.valueOf(this.clientNetworkOptions.receiveTimeout().toMillis()).intValue()
          )
          // TODO: set this intelligently depending on logger?
          // See https://projectreactor.io/docs/netty/release/reference/index.html#client-udp-connection-configurations-wire-logger
          .wiretap(true)
          .handle((udpInbound, udpOutbound) -> {
            udpInbound.receiveObject()
              .subscribe(response -> {
                if (response instanceof DatagramPacket) {
                  DatagramPacket p = (DatagramPacket) response;

                  // TODO: Publish this into the response instead of blocking.

                  // Capture the response bytes.
                  resultByteBuf.set(p.content().retain());
                  latch.countDown();
                } else {
                  throw new RuntimeException("Invalid packet returned from UDP server: " + response);
                }
              });

            return udpOutbound.send(Mono.just(requestByteBuf))
              .neverComplete();
          })
          .wiretap(true)
          .connectNow(Duration.ofSeconds(2));

        // TODO: Return the Mono or else connectNow and then block on onDispose.
        //connection.block(Duration.ofSeconds(30));
//        connection.onDispose().block();

        try {
          // TODO: Set timeout properly.
          latch.await(2, TimeUnit.SECONDS);
          connection.dispose();
        } catch (InterruptedException e) {
          throw new RuntimeException("Response timed out");
        }

        // Wait for the connection to be disposed (TODO: If we use a connection pool this shouldn't happen)
        connection.onDispose().block();
      }
      break;
      case TCP: {
        throw new RuntimeException("Not yet implemented");
      }
//      break;
//      default: {
//        throw new RuntimeException("Unhandled NetworkProtocol: " + this.clientNetworkOptions.networkProtocol());
//      }
    }

    return resultByteBuf.get();
  }

  /**
   * Find how much money can be spent by this client.
   *
   * TODO: Currently, this value only reflects received transfers that were locally processed by
   * `receive_from_fastpay`.
   *
   * @return A {@link Mono} of type {@link BigInteger}.
   */
  @VisibleForTesting
  protected Mono<BigInteger> getSpendableAmount() {

    clientState.pendingTransferOrder().getAndUpdate(transferOrder -> {

      return transferOrder;
    });
// TODO: FIXME
    return null;
  }

  /**
   * Execute (or retry) a transfer order and then update local balance.
   *
   * @param transferOrder    A {@link TransferOrder}.
   * @param withConfirmation A boolean.
   *
   * @return A {@link Mono}.
   */
  protected synchronized Mono<CertifiedTransferOrder> executeTransfer(
    final TransferOrder transferOrder,
    final boolean withConfirmation
  ) {
    Objects.requireNonNull(transferOrder);

    final Optional<TransferOrder> pendingTransferOrder = clientState.pendingTransferOrder().get();

    final boolean hasDifferentPendingTransferOrder = pendingTransferOrder
      .filter($ -> !$.equals(transferOrder))
      .isPresent();
    if (hasDifferentPendingTransferOrder) {
      return Mono.error(
        new RuntimeException("Client state has a different pending transfer")
      );
    }

    final boolean hasDifferentSequenceNumber = pendingTransferOrder
      .map($ -> $.transfer().sequenceNumber())
      .filter(sequenceNumber -> !sequenceNumber.equals(clientState.nextSequenceNumber()))
      .isPresent();
    if (hasDifferentSequenceNumber) {
      return Mono.error(
        new RuntimeException("Unexpected sequence number")
      );
    }

    this.communicateTransfers(
      this.clientState.address(),
      this.sentCertificates,
      transferOrder
    );
    //.and(() -> {});

    // TODO:
    return Mono.empty();
  }

  /**
   * Broadcast confirmation orders and optionally one more transfer order. The corresponding sequence numbers should be
   * consecutive and increasing.
   *
   * @param address          A {@link FastPayAddress}.
   * @param sentCertificates A {@link List} of type {@link CertifiedTransferOrder}.
   * @param transferOrder    A {@link TransferOrder}.
   *
   * @return
   */
  private Mono<Void> communicateTransfers(
    final FastPayAddress address,
    final List<CetifiedTransferOrder> sentCertificates,
    final TransferOrder transferOrder
  ) {
    Objects.requireNonNull(address);
    Objects.requireNonNull(sentCertificates);
    Objects.requireNonNull(transferOrder);

    return communicateTransfers(address, sentCertificates, transferOrder.transfer().sequenceNumber());
  }

  /**
   * Broadcast confirmation orders and optionally one more transfer order. The corresponding sequence numbers should be
   * consecutive and increasing.
   *
   * @param address            A {@link FastPayAddress}.
   * @param sentCertificates   A {@link List} of type {@link CertifiedTransferOrder}.
   * @param nextSequenceNumber An {@link UnsignedLong}.
   *
   * @return
   */
  private Mono<Void> communicateTransfers(
    final FastPayAddress address,
    final List<CetifiedTransferOrder> sentCertificates,
    final UnsignedLong nextSequenceNumber
  ) {
    Objects.requireNonNull(address);
    Objects.requireNonNull(sentCertificates);
    Objects.requireNonNull(nextSequenceNumber);

    // Create a new CertificateRequester...

    // TODO: FIXME
    return null;
  }

}
