package money.fluid.fastpay4j.core;

import money.fluid.fastpay4j.core.keys.Ed25519PublicKey;
import org.immutables.value.Value;

/**
 * A public key for the primary chain that this FastPay server is a sidechain of.
 */
public interface PrimaryAddress {

  static ImmutableDefaultPrimaryAddress.Builder builder() {
    return ImmutableDefaultPrimaryAddress.builder();
  }

  /**
   * The public-key of this fastpay address.
   *
   * @return A {@link Ed25519PublicKey}.
   */
  Ed25519PublicKey edPublicKey();

  @Value.Immutable(intern = true)
  abstract class DefaultPrimaryAddress implements PrimaryAddress, Comparable<PrimaryAddress> {

    @Override
    public abstract Ed25519PublicKey edPublicKey();

    @Override
    public String toString() {
      return edPublicKey().asBase64();
    }

    // TODO: Test this
    @Override
    public int compareTo(PrimaryAddress o) {
      return this.edPublicKey().compareTo(o.edPublicKey());
    }
  }
}
