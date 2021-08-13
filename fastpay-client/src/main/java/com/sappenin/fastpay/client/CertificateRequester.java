package com.sappenin.fastpay.client;

import com.sappenin.fastpay.core.CertifiedTransferOrder;
import com.sappenin.fastpay.core.Committee;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.SequenceNumber;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.serde.BincodeSerdeUtils;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 *
 */
public class CertificateRequester {

  private final Random random;
  private final Committee committee;
  private final List<AuthorityClient> authorityClients;
  private final FastPayAddress senderAddress;

  public CertificateRequester(
    final Committee committee,
    final List<AuthorityClient> authorityClients,
    final FastPayAddress senderAddress
  ) throws NoSuchAlgorithmException {
    this.random = SecureRandom.getInstanceStrong();
    this.committee = Objects.requireNonNull(committee);
    this.authorityClients = Objects.requireNonNull(authorityClients);
    this.senderAddress = Objects.requireNonNull(senderAddress);
  }

  /**
   * Try to find a certificate for the given sender and sequence number.
   *
   * @param sequenceNumber
   *
   * @return
   */
  public Mono<CertifiedTransferOrder> query(
    final SequenceNumber sequenceNumber
  ) {
    Objects.requireNonNull(sequenceNumber);

    final AccountInfoRequest.Builder builder = new AccountInfoRequest.Builder();
    builder.sender = BincodeSerdeUtils.toSerializableKey(senderAddress.edPublicKey());
    builder.request_sequence_number = Optional.of(BincodeSerdeUtils.toSerializableSequenceNumber(sequenceNumber));
    builder.request_received_transfers_excluding_first_nth = Optional.empty();
    final AccountInfoRequest request = builder.build();

    // Sequentially try each authority in random order.
    Collections.shuffle(this.authorityClients, random);

    for (AuthorityClient client : authorityClients) {
//      client.handleAccountInfoRequest(request)
      // TODO:
      return null;
    }
    return null;
  }

}
