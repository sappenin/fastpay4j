package com.sappenin.fastpay.core;

import com.sappenin.fastpay.core.bincode.SequenceNumber;
import com.sappenin.fastpay.core.messages.TransferOrder;
import org.immutables.value.Value.Immutable;

import java.util.Objects;

// TODO: Unit tests!
@Immutable
public interface FastpayError {

  static FastpayError invalidSignature(final String signature) {
    Objects.requireNonNull(signature);
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INVALID_SIGNATURE)
      .errorMessage("Signature is not valid: " + signature)
      .build();
  }

  static FastpayError unknownSigner() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.UNKNOWN_SIGNER)
      .errorMessage("Value was not signed by a known authority")
      .build();
  }

  /**
   * Used for Certificate verification.
   *
   * @return A {@link FastpayError}
   */
  static FastpayError certificateRequiresQuorum() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.CERTIFICATE_REQUIRES_QUORUM)
      .errorMessage("Signatures in a certificate must form a quorum")
      .build();
  }

  /**
   * Used for Transfer processing
   *
   * @return A {@link FastpayError}
   */
  static FastpayError incorrectTransferAmount() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INCORRECT_TRANSFER_AMOUNT)
      .errorMessage("Transfers must have positive amount")
      .build();
  }

  //  UnexpectedSequenceNumber("The given sequence number must match the next expected sequence number of the account"),

  static FastpayError unexpectedSequenceNumber() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.UNEXPECTED_SEQUENCE_NUMBER)
      .errorMessage("The given sequence number must match the next expected sequence number of the account")
      .build();
  }

  static FastpayError insufficientFunding(final String balance) {
    Objects.requireNonNull(balance);
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INSUFFICIENT_FUNDING)
      .errorMessage("The transferred amount must be not exceed the current account balance: " + balance)
      .build();
  }

  static FastpayError previousTransferMustBeConfirmedFirst(final TransferOrder pendingConfirmation) {
    Objects.requireNonNull(pendingConfirmation);
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.PREVIOUS_TRANSFER_MUST_BE_CONFIRMED_FIRST)
      .errorMessage(
        "Cannot initiate transfer while a transfer order is still pending confirmation: {:?}" + pendingConfirmation)
      .build();
  }

  static FastpayError errorWhileProcessingTransfer() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.ERROR_WHILE_PROCESSING_TRANSFERORDER)
      .errorMessage("Transfer order was processed but no signature was produced by authority")
      .build();
  }

  static FastpayError errorWhileRequestingCertificate() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.ERROR_WHILE_REQUESTING_CERTIFICATE)
      .errorMessage("An invalid answer was returned by the authority while requesting a certificate")
      .build();
  }

  static FastpayError missingEarlierConfirmation(final SequenceNumber currentSequenceNumber) {
    Objects.requireNonNull(currentSequenceNumber);
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.MISSING_EALIER_CONFIRMATIONS)
      .errorMessage("Cannot confirm a transfer while previous transfer orders are still pending confirmation: "
        + currentSequenceNumber)
      .build();
  }

  static FastpayError unexpectedTransactionIndex() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.UNEXPECTED_TRANSACTION_INDEX)
      .errorMessage("Transaction index must increase by one")
      .build();
  }

  static FastpayError certificateNotFound() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.CERTIFICATE_NOT_FOUND)
      .errorMessage("No certificate for this account and sequence number")
      .build();
  }

  static FastpayError unknownSenderAccount() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.UNKNOWN_SENDER_ACCOUNT)
      .errorMessage("Unknown sender's account")
      .build();
  }

  static FastpayError certificateAuthorityReuse() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.CERTIFICATE_AUTHORITY_REUSE)
      .errorMessage("Signatures in a certificate must be from different authorities.")
      .build();
  }

  static FastpayError invalidSequenceNumber() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INVALID_SEQUENCE_NUMBER)
      .errorMessage("Sequence numbers above the maximal value are not usable for transfers.")
      .build();
  }

  static FastpayError sequenceOverflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.SEQUENCE_OVERFLOW)
      .errorMessage("Sequence number overflow.")
      .build();
  }

  static FastpayError sequenceUnderflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.SEQUENCE_UNDERFLOW)
      .errorMessage("Sequence number underflow.")
      .build();
  }

  static FastpayError amountOverflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.AMOUNT_OVERFLOW)
      .errorMessage("Amount overflow.")
      .build();
  }

  static FastpayError amountUnderflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.AMOUNT_UNDERFLOW)
      .errorMessage("Amount overflow.")
      .build();
  }

  static FastpayError balanceOverflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.BALANCE_OVERFLOW)
      .errorMessage("Account balance overflow.")
      .build();
  }

  static FastpayError balanceUnderflow() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.BALANCE_UNDERFLOW)
      .errorMessage("Account balance underflow.")
      .build();
  }

  static FastpayError wrongShard() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.WRONG_SHARD)
      .errorMessage("Wrong shard used.")
      .build();
  }

  static FastpayError invalidCrossShardUpdated() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INVALID_CROSS_SHARD_UPDATE)
      .errorMessage("Invalid cross shard update.")
      .build();
  }

  static FastpayError invalidDecoding() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.INVALID_DECODING)
      .errorMessage("Cannot deserialize.")
      .build();
  }

  static FastpayError unexpeectedMessage() {
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.UNEXPECTED_MESSAGE)
      .errorMessage("Unexpected message.")
      .build();
  }

  static FastpayError clientIoError(final String error) {
    Objects.requireNonNull(error);
    return ImmutableFastpayError.builder()
      .errorCode(FastpayErrorCode.CLIENT_IO_ERROR)
      .errorMessage("Network error while querying service " + error)
      .build();
  }

  String errorMessage();

  FastpayErrorCode errorCode();

}
