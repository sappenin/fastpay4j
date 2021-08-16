package com.sappenin.fastpay.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AuthorityUtils}.
 */
class AuthorityUtilsTest {

  @Test
  void deriveShardNumber() {
    int shardNum = AuthorityUtils.deriveShardNumber(4,
      FastPayAddress.fromEd25519PublicKeyBase64("AzKoFE7qtEvQk+GHKt1YFJj28Pvk56rjV2T+fLowB+E="));
    assertThat(shardNum).isEqualTo(0);
  }
}