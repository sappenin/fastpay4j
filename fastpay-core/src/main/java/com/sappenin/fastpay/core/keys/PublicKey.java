package com.sappenin.fastpay.core.keys;

import com.google.common.io.BaseEncoding;
import com.sappenin.fastpay.core.FastpayModel;
import org.checkerframework.checker.units.qual.K;

/**
 * A public key.
 */
public interface PublicKey<K> extends FastpayModel, Comparable<K> {

  /**
   * The bytes of this public key.
   *
   * @return A byte array.
   */
  byte[] value();

  /**
   * Accessor for a Base64-encoded string representation of this key.
   *
   * @return
   */
  default String asBase64() {
    return BaseEncoding.base64().encode(this.value());
  }

  /**
   * Accessor for a Base64-encoded string representation of this key.
   *
   * @return
   */
  default String asBase16() {
    return BaseEncoding.base16().encode(this.value());
  }
}