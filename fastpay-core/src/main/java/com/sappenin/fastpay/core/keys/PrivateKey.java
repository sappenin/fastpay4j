package com.sappenin.fastpay.core.keys;

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
  String asBase64();
}
