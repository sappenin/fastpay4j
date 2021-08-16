package com.sappenin.fastpay.it;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.client.AuthorityClient;
import com.sappenin.fastpay.client.AuthorityClientState;
import com.sappenin.fastpay.client.ClientNetworkOptions;
import com.sappenin.fastpay.client.DefaultAuthorityClient;
import com.sappenin.fastpay.core.AuthorityName;
import com.sappenin.fastpay.core.Committee;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.FastpayException;
import com.sappenin.fastpay.core.NetworkProtocol;
import com.sappenin.fastpay.core.SequenceNumber;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.messages.AccountInfoRequest;
import com.sappenin.fastpay.core.serde.BincodeSerde;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * An integration test that validates the functionality of the {@link DefaultAuthorityClient}.
 */
public class DefaultAuthorityClientIT extends AbstractIT {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  // These correspond to the default accounts in Fastpay Rust.
  private FastPayAddress CLIENT_ADDRESS =
    FastPayAddress.fromEd25519PublicKeyBase64("AzKoFE7qtEvQk+GHKt1YFJj28Pvk56rjV2T+fLowB+E=");

  private Ed25519PrivateKey CLIENT_PRIVATE_KEY = Ed25519PrivateKey.fromBase64(
    "D5siqT/vgcwKGCgK5peCDKolzUcmOYbdqxNXcGJ8RaR27XiIi/bUjEdba007WG3O4QxAGVeuOwrumn7JIY7YCQ=="
  );

  private FastPayAddress AUTHORITY_TWO =
    FastPayAddress.fromEd25519PublicKeyBase64("AQButm+RAgo/LOJLdpMwHItLbjX7ZOIExN3PkQYHlHI=");

  private FastPayAddress AUTHORITY_THREE =
    FastPayAddress.fromEd25519PublicKeyBase64("ATzH4SukNH+jkHwglWUlx9PizEwy+oMxTYda/HFpkDA=");

  private FastPayAddress AUTHORITY_FOUR =
    FastPayAddress.fromEd25519PublicKeyBase64("AUzCYx8H6hDxxaIe8b+O8FdmaYXUW/NMBWrGdNF6eV0=");

  /**
   * @deprecated Will go away once the Environment is wired up via Docker.
   */
  @Deprecated
  //private String HOST = "0.0.0.0";
  private String HOST = "localhost";

  /**
   * @deprecated Will go away once the Environment is wired up via Docker.
   */
  @Deprecated
  private int PORT = 9100; // Shard 0.

  private AuthorityClient authorityClient;

  @BeforeEach
  void setUp() {
    this.authorityClient = constructAuthorityClient(HOST, PORT);
  }


  /**
   * Tries to connect to an invalid address.
   */
  @Test
  void getAccountInfoWhenNoServer() {
    AuthorityClient authorityClient = constructAuthorityClient("example.com", PORT);

    AccountInfoRequest request = AccountInfoRequest.builder()
      .sender(CLIENT_ADDRESS)
      .requestSequenceNumber(Optional.of(SequenceNumber.of(UnsignedLong.ONE)))
      .build();

    try {
      authorityClient.getAccountInfo(request)
        .doOnSuccess($ -> {
          fail("Should have failed but didn't");
        })
        .doOnError(throwable -> {
          LOGGER.error(throwable.getMessage(), throwable);
          assertThat(throwable.getMessage()).isEqualTo("No result from Fastpay Server");
        })
        .block();
    } catch (FastpayException e) {
      // TODO: A Mono with an error shouldn't throw an exception, but reactor-netty does. Is this correct?
      assertThat(e.getMessage()).isEqualTo("No result from Fastpay Server");
    }
  }

  @Test
  void getAccountInfo() {
    AccountInfoRequest request = AccountInfoRequest.builder()
      .sender(CLIENT_ADDRESS)
      .requestSequenceNumber(Optional.of(SequenceNumber.of(UnsignedLong.ONE)))
      .build();

    this.authorityClient.getAccountInfo(request)
      .doOnSuccess($ -> {
        assertThat($.balance()).isEqualTo(BigInteger.TEN);
      })
      .doOnError(throwable -> {
        fail("Shouldn't have thrown an exception, but did", throwable);
      })
      .onErrorStop()
      .block();
  }

  //////////////////
  // Private Helpers
  //////////////////

  private TreeMap<AuthorityName, UnsignedLong> constructVotingRights() {

    final TreeMap<AuthorityName, UnsignedLong> votingRights = new TreeMap<>();

    // TODO: Does the client go into the voting rights maps?

    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_TWO.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );
    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_THREE.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );
    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_FOUR.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );

    return votingRights;
  }

  private AuthorityClient constructAuthorityClient(final String host, final int port) {
    Objects.requireNonNull(host);

    return new DefaultAuthorityClient(
      ClientNetworkOptions.builder()
        .baseAddress(host)
        .basePort(port)
        .networkProtocol(NetworkProtocol.UDP)
        .numShards(1)
        .build(),
      AuthorityClientState.builder()
        .address(CLIENT_ADDRESS)
        .secret(CLIENT_PRIVATE_KEY)
        .balance(BigInteger.TEN)
        .nextSequenceNumber(SequenceNumber.of(UnsignedLong.ONE))
        .authorityClients(Map.of())
        .committee(Committee.builder()
          .votingRights(constructVotingRights())
          .build())
        .build(),
      new BincodeSerde()
    );

  }
}
