package com.sappenin.fastpay.core;

import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import org.immutables.value.Value;

/**
 * A FastPay account is identified by its address, which we instantiate as the cryptographic hash of its public
 * verification key.
 */
public interface FastPayAddress {

  static ImmutableDefaultFastPayAddress.Builder builder() {
    return ImmutableDefaultFastPayAddress.builder();
  }

  static FastPayAddress fromEd25519PublicKeyBase64(final String ed25519PublicKeyBase64) {
    return ImmutableDefaultFastPayAddress.builder()
      .edPublicKey(Ed25519PublicKey.fromBase64(ed25519PublicKeyBase64))
      .build();
  }

  /**
   * The public-key of this fastpay address.
   *
   * @return A {@link Ed25519PublicKey}.
   */
  Ed25519PublicKey edPublicKey();

  @Value.Immutable(intern = true)
  abstract class DefaultFastPayAddress implements FastPayAddress, Comparable<FastPayAddress> {

    @Override
    public abstract Ed25519PublicKey edPublicKey();

    @Override
    public String toString() {
      return edPublicKey().asBase64();
    }

    // TODO: Test this
    @Override
    public int compareTo(FastPayAddress o) {
      return this.edPublicKey().compareTo(o.edPublicKey());
    }
  }
}
