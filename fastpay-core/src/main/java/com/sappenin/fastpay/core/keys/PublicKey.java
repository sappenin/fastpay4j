package com.sappenin.fastpay.core.keys;

import com.google.common.io.BaseEncoding;
import com.sappenin.fastpay.core.FastpayModel;

/**
 * A public key.
 */
public interface PublicKey extends Comparable<PublicKey>, FastpayModel {

  /**
   * The bytes of this public key.
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
    return BaseEncoding.base64().encode(this.bytes());
  }

  /**
   * Accessor for a Base64-encoded string representation of this key.
   *
   * @return
   */
  default String asBase16() {
    return BaseEncoding.base16().encode(this.bytes());
  }
}
