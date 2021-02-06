package money.fluid.fastpay4j.core;

import money.fluid.fastpay4j.core.keys.Ed25519PublicKey;
import org.immutables.value.Value;

/**
 * A FastPay account is identified by its address, which we instantiate as the cryptographic hash of its public
 * verification key.
 */
public interface FastPayAddress {

  static ImmutableDefaultFastPayAddress.Builder builder() {
    return ImmutableDefaultFastPayAddress.builder();
  }

  /**
   * The public-key of this fastpay address.
   *
   * @return A {@link Ed25519PublicKey}.
   */
  Ed25519PublicKey edPublicKey();

  @Value.Immutable
  abstract class DefaultFastPayAddress implements FastPayAddress {

    @Override
    public abstract Ed25519PublicKey edPublicKey();

    @Override
    public String toString() {
      return edPublicKey().asBase64();
    }
  }
}
