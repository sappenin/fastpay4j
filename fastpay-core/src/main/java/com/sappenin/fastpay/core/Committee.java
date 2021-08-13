package com.sappenin.fastpay.core;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.core.immutables.FluentCompareTo;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * A fixed-membership group of authorities that can authorize off-chain payments based upon a threshold of signatures.
 */
@Immutable
public interface Committee {

  static ImmutableCommittee.Builder builder() {
    return ImmutableCommittee.builder();
  }

  UnsignedLong TWO = UnsignedLong.valueOf(2L);
  UnsignedLong THREE = UnsignedLong.valueOf(3L);

  /**
   * The voting rights for each authority.
   */
  TreeMap<AuthorityName, UnsignedLong> votingRights();

  /**
   * The total votes this committee has (?)
   *
   * @return An {@link UnsignedInteger}.
   */
  @Derived
  default UnsignedLong totalVotes() {
    // TODO: test
    return votingRights().values().stream().reduce(UnsignedLong.ZERO, (a, b) -> a.plus(b));
  }

  /**
   * TODO
   *
   * @return
   */
  @Derived
  default UnsignedLong quorumThreshold() {
    // If N = 3f + 1 + k (0 <= k < 3)
    // then (2 N + 3) / 3 = 2f + 1 + (2k + 2)/3 = 2f + 1 + k = N - f
    // 2 * self.total_votes / 3 + 1
    //
    // return 2L * this.totalVotes() / 3 + 1;
    return TWO.times(this.totalVotes()).dividedBy(THREE).plus(UnsignedLong.ONE);
  }

  /**
   * TODO
   *
   * @return
   */
  @Derived
  default UnsignedLong validityThreshold() {
    // If N = 3f + 1 + k (0 <= k < 3)
    // then (N + 2) / 3 = f + 1 + k/3 = f + 1
    //
    // return (this.totalVotes() + 2) / 3;
    return this.totalVotes().plus(TWO).dividedBy(THREE);
  }

  static UnsignedLong weight(final Committee committee, final AuthorityName author) {
    Objects.requireNonNull(committee);
    Objects.requireNonNull(author);

    return Optional.of(committee.votingRights().get(author)).orElse(UnsignedLong.ZERO);
  }

  /**
   * Find the highest value (i.e., next sequence number) that is supported by a quorum of authorities.
   *
   * @param values A {@link List} of type {@link AuthorityName} of the authorities.
   *
   * @return A {@link Long}.
   */
  static UnsignedLong strongMajorityLowerBound(
    final Committee committee,
    final List<AuthoritySequenceNumber> values
  ) {

    Objects.requireNonNull(committee);
    Objects.requireNonNull(values);

    // TODO: This should sort in decreasing (i.e., descending) order. Test this.
    values.sort((o1, o2) -> o2.nextSequenceNumber().compareTo(o1.nextSequenceNumber()));

    UnsignedLong score = UnsignedLong.ZERO;
    for (AuthoritySequenceNumber value : values) {
      score.plus(Committee.weight(committee, value.authorityName()));
      if (FluentCompareTo.is(score).greaterThanEqualTo(committee.quorumThreshold())) {
        return value.nextSequenceNumber();
      }
    }
    return UnsignedLong.ZERO;
  }

  /**
   * TODO Extract if used outside of a committee.
   *
   * A helper object to capture the next sequence number for an authority.
   */
  @Immutable
  interface AuthoritySequenceNumber {

    static ImmutableAuthoritySequenceNumber.Builder builder() {
      return ImmutableAuthoritySequenceNumber.builder();
    }

    AuthorityName authorityName();

    UnsignedLong nextSequenceNumber();

  }
}
