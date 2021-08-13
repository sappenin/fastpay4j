package com.sappenin.fastpay.core.keys;

import java.util.Base64;

/**
 * A public key.
 */
public interface PublicKey extends Comparable<PublicKey> {

  /**
   * The bytes of this public key.
   *
   * @return A byte array.
   */
  byte[] bytes();

  /**
   * Accessor for a Base64-encoded string representation of this key.
   * @return
   */
  default String asBase64(){
    return Base64.getEncoder().encodeToString(this.bytes());
  }
}
