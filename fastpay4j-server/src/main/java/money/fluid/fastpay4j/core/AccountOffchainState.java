package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * The current state of an off-chain account.
 */
@Value.Immutable
public interface AccountOffchainState extends Participant {

  /**
   * Balance of the FastPay account.
   *
   * @return A {@link BigInteger}.
   */
  @Default
  default BigInteger balance() {
    return BigInteger.ZERO;
  }

  /**
   * An integer value, written `next_sequence𝑥 (𝛼)`, tracking the expected sequence number for the next spending
   * action to be created. This value starts at 0.
   *
   * @return An {@link UnsignedLong}.
   */
  UnsignedLong nextSequenceNumber();

  /**
   * A field `pending𝑥 (𝛼)` tracking the last transfer order `𝑂` signed by `𝑥` such that the authority `𝛼`
   * considers `𝑂` as pending confirmation, if any; and absent otherwise. In other words, whether we have signed a
   * transfer for this sequence number already.
   *
   * @return An optionally-present {@link SignedTransferOrder}.
   */
  Optional<SignedTransferOrder> pendingConfirmation();

  /**
   * All confirmed certificates for this sender.
   *
   * @return A {@link List} of type {@link CetifiedTransferOrder}.
   */
  List<CetifiedTransferOrder> confirmedLog();

  /// All executed Primary synchronization orders for this recipient.
  //pub synchronization_log: Vec<PrimarySynchronizationOrder>,

  /**
   * All executed Primary synchronization orders for this recipient.
   *
   * @return A {@link List} of type {@link PrimarySynchronizationOrder}.
   */
  List<PrimarySynchronizationOrder> synchronizationLog();

  /**
   * All confirmed certificates as a receiver.
   *
   * @return A {@link List} of type {@link CetifiedTransferOrder}.
   */
  List<CetifiedTransferOrder> receivedLog();
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
