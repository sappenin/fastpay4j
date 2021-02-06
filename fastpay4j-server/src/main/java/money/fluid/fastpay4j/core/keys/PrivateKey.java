package money.fluid.fastpay4j.core.keys;

import java.util.Base64;

/**
 * A private key.
 */
public interface PrivateKey extends Comparable<PrivateKey> {

  /**
   * The bytes of this private key.
   *
   * @return A byte array.
   */
  byte[] bytes();

  /**
   * Accessor for a Base64-encoded string representation of this key.
   *
   * @return
   */
  default String asBase64() {
    return Base64.getEncoder().encodeToString(this.bytes());
  }
}
