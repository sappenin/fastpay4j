package com.sappenin.fastpay.core.serde;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.novi.serde.Unsigned;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.bincode.AccountInfoResponse;
import com.sappenin.fastpay.core.bincode.EdPublicKeyBytes;
import com.sappenin.fastpay.core.bincode.SequenceNumber;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utilities for (de)serialization to/from bincode.
 */
public class BincodeSerdeUtils {

  // TODO: Unit test back and forth.
  public static EdPublicKeyBytes toEdPublicKeyBytes(final Ed25519PublicKey ed25519PublicKey) {
    Objects.requireNonNull(ed25519PublicKey);

    final List<@Unsigned Byte> value = new ArrayList<>(32);
    for (int i = 0; i < ed25519PublicKey.bytes().length; i++) {
      value.add(i, Byte.valueOf(ed25519PublicKey.bytes()[i]));
    }

    return new EdPublicKeyBytes(value);
  }

  public static SequenceNumber toSerializableSequenceNumber(
    final com.sappenin.fastpay.core.SequenceNumber sequenceNumber
  ) {
    Objects.requireNonNull(sequenceNumber);
    return new SequenceNumber(sequenceNumber.value().longValue());
  }

  public static FastPayAddress from(EdPublicKeyBytes address) {
    return FastPayAddress.builder()
      .edPublicKey(Ed25519PublicKey.fromBase64(toHexString(address.value)))
      .build();
  }

  // TODO Unit test
  public static String toHexString(List<@com.novi.serde.Unsigned Byte> bytes) {
    return BaseEncoding.base16().lowerCase().encode(Bytes.toArray(bytes));
  }

  // TODO Unit test
  public static List<@com.novi.serde.Unsigned Byte> fromHexString(final String hexValue) {
    final byte[] bytes = BaseEncoding.base16().decode(hexValue);
    return IntStream.range(0, bytes.length)
      .mapToObj(i -> bytes[i])
      .collect(Collectors.toList());
  }

  public static Optional<AccountInfoResponse> tryDeserialize(ByteBuf bytes) {
    // TODO: Implement this!
    return Optional.empty();
  }
}
