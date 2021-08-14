package com.sappenin.fastpay.core;

import com.sappenin.fastpay.core.FastPayAddress;

import java.nio.ByteBuffer;

// TODO: Consider renaming to AuthorityStateUtils?
public class AuthorityUtils {

  // ed25519 public keys are 32 bytes long, and we want the last 4 bytes.
  private static final int LAST_INTEGER_INDEX = 32 - 4; //28

  // TODO: Move to AuthorityState?
  public static int deriveShardNumber(final int numTotalShards, final FastPayAddress fastPayAddress) {
    // The last integer in the address is the last 4 bytes (32 bits).
    final int num = ByteBuffer.wrap(fastPayAddress.edPublicKey().bytes(), LAST_INTEGER_INDEX, 4).getInt();
    return num % numTotalShards;
  }

}
