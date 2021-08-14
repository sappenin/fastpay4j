package com.sappenin.fastpay.core.keys;

import com.sappenin.fastpay.core.FastpayModel;

/**
 * A private key.
 */
public interface PrivateKey extends Comparable<PrivateKey>, FastpayModel {

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
