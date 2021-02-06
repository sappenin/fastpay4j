package money.fluid.fastpay4j.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import money.fluid.fastpay4j.core.keys.PublicKey;
import money.fluid.fastpay4j.immutables.Wrapped;
import money.fluid.fastpay4j.immutables.Wrapper;
import org.immutables.value.Value;

import java.io.Serializable;

/**
 * Wrapped immutable classes for providing type-safe objects.
 */
public class Ids {

  /**
   * A wrapped {@link PublicKey} representing an address for a Primary.
   */
  @Value.Immutable(intern = true)
  @Wrapped
  @JsonSerialize(as = PrimaryAddress.class)
  @JsonDeserialize(as = PrimaryAddress.class)
  abstract static class _PrimaryAddress extends Wrapper<PublicKey> implements Serializable {

    @Override
    public String toString() {
      return this.value().toString();
    }

  }

  /**
   * A wrapped {@link PublicKey} representing the name of an Authority.l
   */
  @Value.Immutable(intern = true)
  @Wrapped
  @JsonSerialize(as = FastPayAddress.class)
  @JsonDeserialize(as = FastPayAddress.class)
  abstract static class _AuthorityName extends Wrapper<PublicKey> implements Serializable {

    @Override
    public String toString() {
      return this.value().toString();
    }

  }

}
