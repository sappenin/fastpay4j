package money.fluid.fastpay4j.server.settings;

import money.fluid.fastpay4j.core.FastPayAddress;
import org.immutables.value.Value;

@Value.Immutable
public interface NetworkSettings {

  static ImmutableNetworkSettings.Builder builder() {
    return ImmutableNetworkSettings.builder();
  }

  /**
   * The network protocol that this authority uses to communicate on.
   *
   * @return A {@link NetworkProtocol}.
   */
  NetworkProtocol networkProtocol();

  /**
   * The public-key of this authority.
   *
   * @return A {@link FastPayAddress}.
   */
  FastPayAddress fastPayAddress();

  /**
   * The host address (IPv4 or IPv6) for this authority.
   *
   * @return A {@link String}.
   */
  String host();

  /**
   * The base port that this authority operates at. Note that each shards's port will be this port plus the
   * shard-number.
   *
   * @return An {@link Integer}.
   */
  int port();

}
