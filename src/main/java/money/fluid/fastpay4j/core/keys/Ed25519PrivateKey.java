package money.fluid.fastpay4j.core.keys;

import money.fluid.fastpay4j.core.keys.ImmutableEd25519PrivateKey.Builder;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Derived;

import java.util.Base64;

/**
 * A private key.
 */
@Value.Immutable
public interface Ed25519PrivateKey extends PrivateKey {

  /**
   * Instantiates a new builder.
   *
   * @return A Builder.
   */
  static Builder builder() {
    return ImmutableEd25519PrivateKey.builder();
  }

  @Override
  @Default
  default int compareTo(PrivateKey privateKey) {
    throw new RuntimeException("Not yet implemented");
  }

  @Override
  @Derived
  default String asBase64() {
    return Base64.getEncoder().encodeToString(this.bytes());
  }
}
