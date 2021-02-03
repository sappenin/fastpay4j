package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value.Derived;

import java.util.TreeMap;

/**
 * A fixed-membership group of authorities that can authorize off-chain payments based upon a threshold of signatures.
 */
public interface Committee {

  /**
   * The voting rights for each authority.
   */
  TreeMap<AuthorityName, Long> votingRights();

  /**
   * The total votes this committee has (?)
   *
   * @return An {@link UnsignedInteger}.
   */
  @Derived
  default Long totalVotes() {
    return votingRights().values().stream().reduce(0L, (a, b) -> a + b);
  }

  /**
   * TODO
   *
   * @return
   */
  @Derived
  default long quorumThreshold() {
    // If N = 3f + 1 + k (0 <= k < 3)
    // then (2 N + 3) / 3 = 2f + 1 + (2k + 2)/3 = 2f + 1 + k = N - f
    //  2 * self.total_votes / 3 + 1
    return 2L * this.totalVotes() / 3 + 1;
  }

  /**
   * TODO
   *
   * @return
   */
  @Derived
  default long validityThreshold() {
    // If N = 3f + 1 + k (0 <= k < 3)
    // then (N + 2) / 3 = f + 1 + k/3 = f + 1
    return (this.totalVotes() + 2) / 3;
  }

  // TODO
//  /**
//   * Find the highest value than is supported by a quorum of authorities.
//   *
//   * @return A {@link Long}.
//   */
//  static default Long strongMajorityLowerBound(numbers) {
//
//  }
}
