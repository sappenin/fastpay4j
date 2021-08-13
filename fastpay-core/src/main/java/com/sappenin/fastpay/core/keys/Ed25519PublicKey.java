package com.sappenin.fastpay.core.keys;

import org.immutables.value.Value;
import org.immutables.value.Value.Derived;

import java.util.Base64;

/**
 * An ed25519 public key.
 */
public interface Ed25519PublicKey extends PublicKey {

  /**
   * Instantiates a new builder.
   *
   * @return A Builder.
   */
  static ImmutableDefaultEd25519PublicKey.Builder builder() {
    return ImmutableDefaultEd25519PublicKey.builder();
  }

  static Ed25519PublicKey fromBase64(final String base64EncodedKey) {
    return builder().bytes(Base64.getDecoder().decode(base64EncodedKey)).build();
  }

  /**
   * To satisfy immutables.
   */
  @Value.Immutable
  abstract class DefaultEd25519PublicKey implements Ed25519PublicKey {

    @Override
    @Derived
    public String asBase64() {
      return Base64.getEncoder().encodeToString(this.bytes());
    }

    @Override
    public int compareTo(PublicKey publicKey) {
      return publicKey.toString().compareTo(this.toString());
    }

    @Override
    public String toString() {
      return this.asBase64();
    }
  }
}
