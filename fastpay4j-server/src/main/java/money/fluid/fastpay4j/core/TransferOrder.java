package money.fluid.fastpay4j.core;

import org.immutables.value.Value;

import java.security.Signature;

/**
 * All transfers initiated by a FastPay account start with a transfer order `𝑂`.
 */
@Value.Immutable
public interface TransferOrder {

  /**
   * Details about the actual transfer.
   *
   * @return A {@link Transfer}.
   */
  Transfer transfer();

  /**
   * A signature over the above data, signed by the sender,
   *
   * @return A {@link Signature}.
   */
  // TODO Consider a wrapper for easier handling & serialization?
  Signature signature();
}
