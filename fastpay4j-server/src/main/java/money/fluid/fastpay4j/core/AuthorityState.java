package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import money.fluid.fastpay4j.core.keys.Ed25519PrivateKey;
import org.immutables.value.Value.Default;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The current state of a FastPay Authority.  The state of an authority consists of the authority name, signature and
 * verification keys; The committee, represented as a set of authorities and their verification keys; ; An integer
 * value, called last_transaction, referring to the last transaction that paid funds into the Primary. This is used by
 * authorities to synchronize FastPay accounts with funds from the Primary (see Section 4.3).
 */
public interface AuthorityState extends Participant {

  /**
   * The name of this authority.
   *
   * @return A {@link AuthorityName}.
   */
  AuthorityName authorityName();

  /**
   * The signature key of the authority.
   *
   * @return A {@link Ed25519PrivateKey}.
   */
  Ed25519PrivateKey secretKey();

  /**
   * The committee, represented as a set of authorities and their verification keys.
   *
   * @return A {@link Committee}.
   */
  Committee committee();

  /**
   * A map accounts(𝛼) tracking the current account state of each FastPay address `𝑥` in use.
   *
   * @return A {@link TreeMap}.
   */
  TreeMap<FastPayAddress, AccountOffchainState> accounts();

  /**
   * The latest transaction index of the blockchain that the authority has seen. In the FastPay paper, this value is
   * noted as `last_transaction(𝛼)`, referring to the last transaction that paid funds into the Primary. This is used
   * by authorities to synchronize FastPay accounts with funds from the Primary.
   *
   * @return An {@link UnsignedLong}.
   */
  AtomicReference<UnsignedLong> lastTransactionIndex();

  /**
   * The sharding ID of this authority shard. 0 if one shard.
   *
   * @return An {@link UnsignedInteger}.
   */
  @Default
  default Integer shardId() {
    return 0;
  }

  /**
   * The number of shards. 1 if single shard.
   *
   * @return An {@link UnsignedInteger}.
   */
  @Default
  default Integer numShards() {
    return 1;
  }
}
