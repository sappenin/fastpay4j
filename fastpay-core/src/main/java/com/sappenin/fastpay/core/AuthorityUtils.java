package com.sappenin.fastpay.core;

import com.google.common.primitives.UnsignedInteger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

// TODO: Consider renaming to AuthorityStateUtils?
public class AuthorityUtils {

  // ed25519 public keys are 32 bytes long, and we want the last 4 bytes.
  private static final int LAST_INTEGER_INDEX = 32 - 4; //28

  // TODO: Move to AuthorityState?
  public static int deriveShardNumber(final int numTotalShards, final FastPayAddress fastPayAddress) {
    // The last integer in the address is the last 4 bytes (32 bits).
    int num = ByteBuffer
      .wrap(fastPayAddress.edPublicKey().bytes(), LAST_INTEGER_INDEX, 4)
      .order(ByteOrder.LITTLE_ENDIAN)
      .getInt();
    long numLong = num & 0XFFFFFFFFL; // Truncate any leading bits > 32
    return UnsignedInteger.valueOf(numLong)
      .mod(UnsignedInteger.valueOf(numTotalShards))
      .intValue();
  }

}
