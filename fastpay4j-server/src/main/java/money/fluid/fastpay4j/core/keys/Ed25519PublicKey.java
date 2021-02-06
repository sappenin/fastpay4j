package money.fluid.fastpay4j.core.keys;

import money.fluid.fastpay4j.core.keys.ImmutableEd25519PublicKey.Builder;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Derived;

import java.util.Base64;


/**
 * A private key.
 */
@Value.Immutable
public interface Ed25519PublicKey extends PublicKey {

  /**
   * Instantiates a new builder.
   *
   * @return A Builder.
   */
  static Builder builder() {
    return ImmutableEd25519PublicKey.builder();
  }

  @Override
  @Default
  default int compareTo(PublicKey publicKey) {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  @Derived
  default String asBase64() {
    return Base64.getEncoder().encodeToString(this.bytes());
  }
}
