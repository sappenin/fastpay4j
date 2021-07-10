package money.fluid.fastpay4j.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedLong;
import money.fluid.fastpay4j.core.keys.PublicKey;
import money.fluid.fastpay4j.immutables.Wrapped;
import money.fluid.fastpay4j.immutables.Wrapper;
import org.immutables.value.Value;

import java.io.Serializable;

/**
 * Wrapped immutable classes for providing type-safe objects.
 */
public class Ids {

//  /**
//   * A wrapped {@link PublicKey} representing an address for a Primary ledger (e.g., Libra, XRPL, or an RTGS).
//   */
//  @Value.Immutable(intern = true)
//  @Wrapped
//  @JsonSerialize(as = PrimaryAddress.class)
//  @JsonDeserialize(as = PrimaryAddress.class)
//  abstract static class _PrimaryAddress extends Wrapper<PublicKey> implements Serializable {
//
//    @Override
//    public String toString() {
//      return this.value().toString();
//    }
//
//  }
//
//  /**
//   * A wrapped {@link PublicKey} representing the name of an Authority.
//   */
//  @Value.Immutable(intern = true)
//  @Wrapped
//  @JsonSerialize(as = AuthorityName.class)
//  @JsonDeserialize(as = AuthorityName.class)
//  abstract static class _AuthorityName extends Wrapper<PublicKey> implements Serializable {
//
//    @Override
//    public String toString() {
//      return this.value().toString();
//    }
//
//  }

  /**
   * A wrapped {@link UnsignedLong} representing the index of a transaction.
   */
  @Value.Immutable(intern = true)
  @Wrapped
  @JsonSerialize(as = TransactionIndex.class)
  @JsonDeserialize(as = TransactionIndex.class)
  abstract static class _TransactionIndex extends Wrapper<UnsignedLong> implements Serializable {

    @Override
    public String toString() {
      return this.value().toString();
    }

  }

  /**
   * A wrapped {@link UnsignedLong} representing the index of a transaction.
   */
  @Value.Immutable(intern = true)
  @Wrapped
  @JsonSerialize(as = SequenceNumber.class)
  @JsonDeserialize(as = SequenceNumber.class)
  abstract static class _SequenceNumber extends Wrapper<UnsignedLong> implements Serializable {

    @Override
    public String toString() {
      return this.value().toString();
    }

  }


}
