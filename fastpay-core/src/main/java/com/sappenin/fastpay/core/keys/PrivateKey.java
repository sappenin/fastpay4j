package com.sappenin.fastpay.core.keys;

import com.sappenin.fastpay.core.FastpayModel;

import javax.security.auth.Destroyable;

/**
 * A private key.
 */
public interface PrivateKey extends Destroyable, FastpayModel {

  /**
   * Accessor for this key's value.
   *
   * @return A copy of this key's byte array.
   */
  byte[] value();
}
