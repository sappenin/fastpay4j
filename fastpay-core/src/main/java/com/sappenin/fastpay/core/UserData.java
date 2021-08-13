package com.sappenin.fastpay.core;

import org.immutables.value.Value;
import org.immutables.value.Value.Default;

/**
 * Any data supplied by a "user" (e.g., an actual end-user or financial institution) of a FastPay account.
 */
@Value.Immutable
public interface UserData {

  byte[] EMPTY_BYTES = new byte[0];

  UserData NO_USER_DATA = builder().value(EMPTY_BYTES).build();

  /**
   * A builder.
   *
   * @return A {@link ImmutableUserData.Builder}.
   */
  static ImmutableUserData.Builder builder() {
    return ImmutableUserData.builder();
  }

  /**
   * Custom, user-supplied data.
   *
   * @return A byte array.
   */
  @Default
  default byte[] value() {
    return EMPTY_BYTES;
  }
}
