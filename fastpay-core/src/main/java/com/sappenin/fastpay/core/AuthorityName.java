package com.sappenin.fastpay.core;

import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import org.immutables.value.Value;

/**
 * The public key for a FastPay authority.
 */
public interface AuthorityName extends FastpayModel{

  static ImmutableDefaultAuthorityName.Builder builder() {
    return ImmutableDefaultAuthorityName.builder();
  }

  /**
   * The public-key of this fastpay address.
   *
   * @return A {@link Ed25519PublicKey}.
   */
  Ed25519PublicKey edPublicKey();

  @Value.Immutable(intern = true)
  abstract class DefaultAuthorityName implements AuthorityName, Comparable<AuthorityName> {

    @Override
    public abstract Ed25519PublicKey edPublicKey();

    @Override
    public String toString() {
      return edPublicKey().asBase64();
    }

    // TODO: Test this
    @Override
    public int compareTo(AuthorityName o) {
      return this.edPublicKey().compareTo(o.edPublicKey());
    }
  }
}
