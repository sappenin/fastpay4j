package com.sappenin.fastpay.core.keys;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Derived;

import java.util.Base64;

/**
 * An ed25519 public key.
 */
public interface Ed25519PublicKey extends PublicKey<Ed25519PublicKey> {

  /**
   * Instantiates a new builder.
   *
   * @return A Builder.
   */
  static Ed25519PublicKey of(final byte[] value) {
    return ImmutableDefaultEd25519PublicKey.builder().value(value).build();
  }

  static Ed25519PublicKey fromBase64(final String base64EncodedKey) {
    return Ed25519PublicKey.of(BaseEncoding.base64().decode(base64EncodedKey));
  }

  static Ed25519PublicKey fromBase16(final String base64EncodedKey) {
    return Ed25519PublicKey.of(BaseEncoding.base16().decode(base64EncodedKey));
  }

  /**
   * To satisfy immutables.
   */
  @Value.Immutable
  abstract class DefaultEd25519PublicKey implements Ed25519PublicKey {

    @Override
    @Derived
    public String asBase64() {
      return Base64.getEncoder().encodeToString(this.value());
    }

    @Override
    public String toString() {
      return this.asBase64();
    }

    @Check
    public void check() {
      Preconditions.checkArgument(value().length == 32, "Ed25519 Public Keys must have 32 bytes");
    }

    @Override
    public int compareTo(Ed25519PublicKey publicKey) {
      return BaseEncoding.base16().encode(this.value()).compareTo(BaseEncoding.base16().encode(publicKey.value()));
    }
  }
}
