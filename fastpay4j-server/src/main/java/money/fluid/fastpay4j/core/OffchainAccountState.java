package money.fluid.fastpay4j.core;

import org.immutables.value.Value.Default;

import java.math.BigInteger;

/**
 * The current state of an off-chain account.
 */
public interface OffchainAccountState extends Participant {

  /**
   * Balance of the FastPay account.
   *
   * @return A {@link BigInteger}.
   */
  @Default
  default BigInteger balance() {
    return BigInteger.ZERO;
  }

  // TODO

//  /// Sequence number tracking spending actions.
//  pub next_sequence_number: SequenceNumber,
//  /// Whether we have signed a transfer for this sequence number already.
//  pub pending_confirmation: Option<SignedTransferOrder>,
//  /// All confirmed certificates for this sender.
//  pub confirmed_log: Vec<CertifiedTransferOrder>,
//  /// All executed Primary synchronization orders for this recipient.
//  pub synchronization_log: Vec<PrimarySynchronizationOrder>,
//  /// All confirmed certificates as a receiver.
//  pub received_log: Vec<CertifiedTransferOrder>,

  // TODO: Defaults
//  next_sequence_number: SequenceNumber::new(),
//  pending_confirmation: None,
//  confirmed_log: Vec::new(),
//  synchronization_log: Vec::new(),
//  received_log: Vec::new(),
}
