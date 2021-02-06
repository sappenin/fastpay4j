package money.fluid.fastpay4j.core.keys;

import org.immutables.value.Value;
import org.immutables.value.Value.Lazy;
import org.immutables.value.Value.Redacted;

import java.util.Base64;

/**
 * A private key.
 */
public interface Ed25519PrivateKey extends PrivateKey {

  /**
   * Instantiates a new builder.
   *
   * @return A Builder.
   */
  static ImmutableDefaultEd25519PrivateKey.Builder builder() {
    return ImmutableDefaultEd25519PrivateKey.builder();
  }

  /**
   * To satisfy immutables.
   */
  @Value.Immutable
  abstract class DefaultEd25519PrivateKey implements Ed25519PrivateKey {

    /**
     * The bytes of this private key.
     *
     * @return A byte array.
     */
    @Override
    @Redacted
    public abstract byte[] bytes();

    @Override
    @Lazy
    @Redacted
    public String asBase64() {
      return Base64.getEncoder().encodeToString(this.bytes());
    }

    @Override
    public int compareTo(PrivateKey privateKey) {
      return privateKey.toString().compareTo(this.toString());
    }

  }
}
