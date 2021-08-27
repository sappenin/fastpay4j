package com.sappenin.fastpay.core.keys;

import java.util.Arrays;
import java.util.Objects;

/**
 * An Ed25519 private key.
 */
public class Ed25519PrivateKey implements PrivateKey {

  private final byte[] value;
  private boolean destroyed;

  /**
   * Instantiates a new builder.
   *
   * @return A {@link PrivateKey}.
   */
  public static Ed25519PrivateKey of(final byte[] value) {
    return new Ed25519PrivateKey(value);
  }

  /**
   * Required-args Constructor.
   *
   * @param value A byte-array containing this key's value.
   */
  private Ed25519PrivateKey(final byte[] value) {
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public byte[] value() {
    final byte[] dest = new byte[value.length];
    System.arraycopy(value, 0, dest, 0, value.length);
    return dest;
  }

  @Override
  public final void destroy() {
    if (!destroyed) {
      Arrays.fill(value, (byte) 0);
      this.destroyed = true;
    }
  }

  @Override
  public final boolean isDestroyed() {
    return this.destroyed;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Ed25519PrivateKey that = (Ed25519PrivateKey) obj;
    return destroyed == that.isDestroyed() && Arrays.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(destroyed);
    result = 31 * result + Arrays.hashCode(value);
    return result;
  }

}
