package money.fluid.fastpay4j.core;

import org.immutables.value.Value;

import java.security.Signature;
import java.util.Map;

/**
 * A {@link TransferOrder} that has been certified (i.e., signed) by a quorum of authorities.
 */
@Value.Immutable
public interface CetifiedTransferOrder {

  /**
   * The {@link TransferOrder}.
   *
   * @return A {@link TransferOrder}.
   */
  TransferOrder value();

  /**
   * Authorities respond to valid transfer orders by signing them, and a quorum of such signatures is meant to be
   * aggregated into a transfer certificate.
   *
   * @return A {@link AuthorityName}.
   */
  Map<AuthorityName, Signature> signatures();
}
