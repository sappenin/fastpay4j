package com.sappenin.fastpay.core.messages;

import com.sappenin.fastpay.core.CertifiedTransferOrder;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.SequenceNumber;
import org.immutables.value.Value.Immutable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Immutable
public interface AccountInfoResponse {

  static ImmutableAccountInfoResponse.Builder builder() {
    return ImmutableAccountInfoResponse.builder();
  }

  FastPayAddress sender();

  BigInteger balance();

  SequenceNumber nextSequenceNumber();

  Optional<SignedTransferOrder> pendingConfirmations();

  Optional<CertifiedTransferOrder> requestedCertificate();

  List<SignedTransferOrder> requestedReceivedTransfers();

}
