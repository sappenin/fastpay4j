package com.sappenin.fastpay.core;

import com.sappenin.fastpay.core.messages.TransferOrder;

import java.security.Signature;
import java.util.Map;

/**
 * A transfer order that has been certified by a quorum of authorities.
 */
public interface CertifiedTransferOrder {

  /**
   * The {@link TransferOrder} that has been certified by a quorum of authorities.
   *
   * @return A {@link TransferOrder}.
   */
  TransferOrder transferOrder();

  /**
   * The signatures from each authority in the quorum.
   *
   * @return A {@link Map}.
   */
  Map<AuthorityName, Signature> signatures();

}
