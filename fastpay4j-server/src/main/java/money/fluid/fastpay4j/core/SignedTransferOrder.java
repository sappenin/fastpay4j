package money.fluid.fastpay4j.core;

import org.immutables.value.Value;

import java.security.Signature;

/**
 * A container for holding everything about a signed {@link TransferOrder}.
 */
@Value.Immutable
public interface SignedTransferOrder {

  /**
   * The {@link TransferOrder}.
   *
   * @return A {@link TransferOrder}.
   */
  TransferOrder value();

  /**
   * The public-key of the authority that is signing this transfer order in order to validate it.
   *
   * @return A {@link AuthorityName}.
   */
  AuthorityName authority();

  /**
   * The signature of the the authority identified by {@link #authority()} provided as a validation signal. Authorities
   * respond to valid transfer orders by signing them, and a quorum of such signatures is meant to be aggregated into a
   * transfer certificate.
   *
   * @return A {@link Signature}.
   */
  Signature signature();

}
