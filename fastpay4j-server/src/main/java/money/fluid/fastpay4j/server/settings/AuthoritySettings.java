package money.fluid.fastpay4j.server.settings;

import money.fluid.fastpay4j.core.FastPayAddress;
import org.immutables.value.Value;

/**
 * A typed version of runtime properties for a FastPay authority, which jointly maintain account balances among a
 * committee of authorities.
 */
public interface AuthoritySettings {

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
  int basePort();

  /**
   * The number of shards this authority employs.
   *
   * @return An {@link Integer}.
   */
  int numShards();

  /**
   * Abstract implementation to satisfy immutables.
   */
  @Value.Immutable
  abstract class DefaultAuthoritySettings implements AuthoritySettings {

    @Override
    public abstract NetworkProtocol networkProtocol();

    @Override
    public abstract FastPayAddress fastPayAddress();

    @Override
    public abstract String host();

    @Override
    public abstract int basePort();

    @Override
    public abstract int numShards();
  }


}
