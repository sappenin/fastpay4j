package com.sappenin.fastpay.server.settings;

import com.sappenin.fastpay.core.AuthorityState;
import org.immutables.value.Value;

/**
 * Settings for a FastPay server shard.
 */
@Value.Immutable
public interface ServerShardSettings {

  String OVERRIDE_BEAN_NAME = "fastpay.server.shard.serverShardSettingsOverride";

  /**
   * A builder.
   *
   * @return A {@link ImmutableServerShardSettings.Builder}.
   */
  static ImmutableServerShardSettings.Builder builder() {
    return ImmutableServerShardSettings.builder();
  }

  /**
   * The network settings for this shard.
   *
   * @return A {@link NetworkSettings}.
   */
  NetworkSettings networkSettings();

  /**
   * @return
   */
  AuthorityState authorityState();
}
