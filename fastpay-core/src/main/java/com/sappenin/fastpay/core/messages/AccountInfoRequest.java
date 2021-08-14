package com.sappenin.fastpay.core.messages;

import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.SequenceNumber;
import org.immutables.value.Value.Immutable;

import java.util.Optional;

@Immutable
public interface AccountInfoRequest {

  static ImmutableAccountInfoRequest.Builder builder() {
    return ImmutableAccountInfoRequest.builder();
  }

  FastPayAddress sender();

  Optional<SequenceNumber> requestSequenceNumber();

  Optional<Long> requestReceivedTransfersExcludingFirstNth();

}
