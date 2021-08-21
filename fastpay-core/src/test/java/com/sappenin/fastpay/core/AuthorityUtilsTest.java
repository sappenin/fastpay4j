package com.sappenin.fastpay.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AuthorityUtils}.
 */
class AuthorityUtilsTest {

  @Test
  void deriveShardNumber() {

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("A2cEntd9tcAMLsgayRNl6owrMOI5gEFtw1VoDayGAHg=")
      )
    ).isEqualTo(0);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("GCRtmYafSWOnYHDh9YukgfN2KSYaIe4YDFAVW9VXt+4=")
      )
    ).isEqualTo(1);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("AzKoFE7qtEvQk+GHKt1YFJj28Pvk56rjV2T+fLowB+E=")
      )
    ).isEqualTo(2);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("BWR/Bvn4VWTsuLKqtLVPT7Q3yEJzKXyC/1GIFJ/AHLw=")
      )
    ).isEqualTo(3);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("Cm8jsuewLGo/YWGRMEzzyVdzxzEkOMwwvmXxkmqykY0=")
      )
    ).isEqualTo(2);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("DYQIqYnnYpHcl3xd6MYfmpJiWxd6I2VSUSyi2itvm34=")
      )
    ).isEqualTo(3);

    assertThat(
      AuthorityUtils.deriveShardNumber(
        4, FastPayAddress.fromEd25519PublicKeyBase64("EUmqG4EBBq/A7SJwTure9BOfTs5HqBPWw57QOUtFXsg=")
      )
    ).isEqualTo(3);
  }
}