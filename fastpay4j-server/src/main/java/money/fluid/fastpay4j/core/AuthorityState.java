package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value.Default;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The current state of a FastPay Authority.
 */
public interface AuthorityState extends Participant {

  /**
   * The name of this authority.
   *
   * @return A {@link AuthorityName}.
   */
  AuthorityName authorityName();

  /**
   * The latest transaction index of the blockchain that the authority has seen.
   *
   * @return An {@link UnsignedLong}.
   */
  AtomicReference<UnsignedLong> lastTransactionIndex();

  /**
   * Offchain states of FastPay accounts.
   *
   * @return A {@link TreeMap}.
   */
  TreeMap<FastPayAddress, OffchainAccountState> accounts();


  /**
   * The sharding ID of this authority shard. 0 if one shard.
   *
   * @return An {@link UnsignedInteger}.
   */
  @Default
  default UnsignedInteger shardId() {
    return UnsignedInteger.ZERO;
  }

  /**
   * The number of shards. 1 if single shard.
   *
   * @return An {@link UnsignedInteger}.
   */
  @Default
  default UnsignedInteger numShards() {
    return UnsignedInteger.ONE;
  }
}
