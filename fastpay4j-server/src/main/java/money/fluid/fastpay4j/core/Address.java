package money.fluid.fastpay4j.core;

import org.immutables.value.Value;

/**
 * A fully-qualified FastPay system address.
 */
@Value.Immutable
public interface Address {

  PrimaryAddress primaryAddress();

  FastPayAddress fastPayAddress();
}
