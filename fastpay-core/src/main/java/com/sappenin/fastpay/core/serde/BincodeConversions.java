package com.sappenin.fastpay.core.serde;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.UnsignedLong;
import com.novi.serde.Unsigned;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.FastpayError;
import com.sappenin.fastpay.core.bincode.EdPublicKeyBytes;
import com.sappenin.fastpay.core.bincode.FastPayError;
import com.sappenin.fastpay.core.bincode.FastPayError.AmountOverflow;
import com.sappenin.fastpay.core.bincode.FastPayError.AmountUnderflow;
import com.sappenin.fastpay.core.bincode.FastPayError.BalanceOverflow;
import com.sappenin.fastpay.core.bincode.FastPayError.BalanceUnderflow;
import com.sappenin.fastpay.core.bincode.FastPayError.CertificateAuthorityReuse;
import com.sappenin.fastpay.core.bincode.FastPayError.CertificateNotfound;
import com.sappenin.fastpay.core.bincode.FastPayError.CertificateRequiresQuorum;
import com.sappenin.fastpay.core.bincode.FastPayError.ClientIoError;
import com.sappenin.fastpay.core.bincode.FastPayError.ErrorWhileProcessingTransferOrder;
import com.sappenin.fastpay.core.bincode.FastPayError.ErrorWhileRequestingCertificate;
import com.sappenin.fastpay.core.bincode.FastPayError.IncorrectTransferAmount;
import com.sappenin.fastpay.core.bincode.FastPayError.InsufficientFunding;
import com.sappenin.fastpay.core.bincode.FastPayError.InvalidCrossShardUpdate;
import com.sappenin.fastpay.core.bincode.FastPayError.InvalidDecoding;
import com.sappenin.fastpay.core.bincode.FastPayError.InvalidSequenceNumber;
import com.sappenin.fastpay.core.bincode.FastPayError.InvalidSignature;
import com.sappenin.fastpay.core.bincode.FastPayError.MissingEalierConfirmations;
import com.sappenin.fastpay.core.bincode.FastPayError.PreviousTransferMustBeConfirmedFirst;
import com.sappenin.fastpay.core.bincode.FastPayError.SequenceOverflow;
import com.sappenin.fastpay.core.bincode.FastPayError.SequenceUnderflow;
import com.sappenin.fastpay.core.bincode.FastPayError.UnexpectedMessage;
import com.sappenin.fastpay.core.bincode.FastPayError.UnexpectedSequenceNumber;
import com.sappenin.fastpay.core.bincode.FastPayError.UnexpectedTransactionIndex;
import com.sappenin.fastpay.core.bincode.FastPayError.UnknownSenderAccount;
import com.sappenin.fastpay.core.bincode.FastPayError.UnknownSigner;
import com.sappenin.fastpay.core.bincode.FastPayError.WrongShard;
import com.sappenin.fastpay.core.bincode.SequenceNumber;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utilities for (de)serialization to/from bincode.
 */
public class BincodeConversions {

  // TODO: Unit Test (if this sticks around)
  public static EdPublicKeyBytes toEdPublicKeyBytes(final Ed25519PublicKey ed25519PublicKey) {
    Objects.requireNonNull(ed25519PublicKey);

    final List<@Unsigned Byte> value = new ArrayList<>(32);
    for (int i = 0; i < ed25519PublicKey.value().length; i++) {
      value.add(i, Byte.valueOf(ed25519PublicKey.value()[i]));
    }

    return new EdPublicKeyBytes(value);
  }

  // TODO: Unit Test (if this sticks around)
  public static com.sappenin.fastpay.core.messages.AccountInfoRequest
  convert(com.sappenin.fastpay.core.bincode.AccountInfoRequest request) {
    Objects.requireNonNull(request);

    return com.sappenin.fastpay.core.messages.AccountInfoRequest.builder()
      .sender(FastPayAddress.fromEd25519PublicKeyBase64(toHexString(request.sender.value)))
      .requestSequenceNumber(request.request_sequence_number
        .map($ -> $.value)
        .map(UnsignedLong::valueOf)
        .map($ -> com.sappenin.fastpay.core.SequenceNumber.of($))
      )
      .requestReceivedTransfersExcludingFirstNth(request.request_received_transfers_excluding_first_nth)
      .build();
  }

  // TODO: Unit Test (if this sticks around)
  public static com.sappenin.fastpay.core.bincode.AccountInfoRequest convert(
    com.sappenin.fastpay.core.messages.AccountInfoRequest request) {
    Objects.requireNonNull(request);

    final EdPublicKeyBytes sender = new EdPublicKeyBytes(fromHexString(request.sender().edPublicKey().asBase16()));
    final Optional<SequenceNumber> requestSequenceNumber = request.requestSequenceNumber()
      .map($ -> new SequenceNumber($.value().longValue()));
    final Optional<@com.novi.serde.Unsigned Long> requestReceivedTransfersExcludingFirstNth = request.requestReceivedTransfersExcludingFirstNth();
    return new com.sappenin.fastpay.core.bincode.AccountInfoRequest(
      sender, requestSequenceNumber, requestReceivedTransfersExcludingFirstNth
    );
  }

  // TODO: Unit Test (if this sticks around)
  public static com.sappenin.fastpay.core.messages.AccountInfoResponse
  convert(com.sappenin.fastpay.core.bincode.AccountInfoResponse response) {
    Objects.requireNonNull(response);

    return com.sappenin.fastpay.core.messages.AccountInfoResponse.builder()
      .sender(FastPayAddress.fromEd25519PublicKeyBase64(toHexString(response.sender.value)))
      .balance(response.balance.value)
      .nextSequenceNumber(com.sappenin.fastpay.core.SequenceNumber.of(
        UnsignedLong.valueOf(response.next_sequence_number.value)
      ))
      // TODO: Finish these!
//      .pendingConfirmations()
//      .requestedCertificate()
//      .addAllRequestedReceivedTransfers()
      .build();
  }

  /**
   * Convert a bincode-encoded error into the standard type used by fp4j.
   *
   * @param error An instance of {@link  com.sappenin.fastpay.core.bincode.FastPayError}.
   *
   * @return An instance of {@link FastpayError}.
   */
  // TODO: Unit Test (if this sticks around)
  public static FastpayError convert(final FastPayError error) {
    Objects.requireNonNull(error);

    if (InvalidSignature.class.isAssignableFrom(error.getClass())) {
      return FastpayError.invalidSignature(((InvalidSignature) error).error);
    } else if (UnknownSigner.class.isAssignableFrom(error.getClass())) {
      return FastpayError.unknownSigner();
    } else if (CertificateRequiresQuorum.class.isAssignableFrom(error.getClass())) {
      return FastpayError.certificateRequiresQuorum();
    } else if (IncorrectTransferAmount.class.isAssignableFrom(error.getClass())) {
      return FastpayError.incorrectTransferAmount();
    } else if (UnexpectedSequenceNumber.class.isAssignableFrom(error.getClass())) {
      return FastpayError.unexpectedSequenceNumber();
    } else if (InsufficientFunding.class.isAssignableFrom(error.getClass())) {
      return FastpayError.insufficientFunding(((InsufficientFunding) error).current_balance.value.toString());
    } else if (PreviousTransferMustBeConfirmedFirst.class.isAssignableFrom(error.getClass())) {
//      TransferOrder transferOrder = TransferOrder.builder()
//        .transfer(Transfer.builder()
//
//          .build())
//        .build();
      // TODO: deserialize transferOrder and inject into here.
      return FastpayError.previousTransferMustBeConfirmedFirst(null);
    } else if (ErrorWhileProcessingTransferOrder.class.isAssignableFrom(error.getClass())) {
      return FastpayError.errorWhileProcessingTransfer();
    } else if (ErrorWhileRequestingCertificate.class.isAssignableFrom(error.getClass())) {
      return FastpayError.errorWhileRequestingCertificate();
    } else if (MissingEalierConfirmations.class.isAssignableFrom(error.getClass())) {
      return FastpayError.missingEarlierConfirmation(((MissingEalierConfirmations) error).current_sequence_number);
    } else if (UnexpectedTransactionIndex.class.isAssignableFrom(error.getClass())) {
      return FastpayError.unexpectedTransactionIndex();
    } else if (CertificateNotfound.class.isAssignableFrom(error.getClass())) {
      return FastpayError.certificateNotFound();
    } else if (UnknownSenderAccount.class.isAssignableFrom(error.getClass())) {
      return FastpayError.unknownSenderAccount();
    } else if (CertificateAuthorityReuse.class.isAssignableFrom(error.getClass())) {
      return FastpayError.certificateAuthorityReuse();
    } else if (InvalidSequenceNumber.class.isAssignableFrom(error.getClass())) {
      return FastpayError.invalidSequenceNumber();
    } else if (SequenceOverflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.sequenceOverflow();
    } else if (SequenceUnderflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.sequenceUnderflow();
    } else if (AmountOverflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.amountOverflow();
    } else if (AmountUnderflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.amountUnderflow();
    } else if (BalanceOverflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.balanceOverflow();
    } else if (BalanceUnderflow.class.isAssignableFrom(error.getClass())) {
      return FastpayError.balanceUnderflow();
    } else if (WrongShard.class.isAssignableFrom(error.getClass())) {
      return FastpayError.wrongShard();
    } else if (InvalidCrossShardUpdate.class.isAssignableFrom(error.getClass())) {
      return FastpayError.invalidCrossShardUpdated();
    } else if (InvalidDecoding.class.isAssignableFrom(error.getClass())) {
      return FastpayError.invalidDecoding();
    } else if (UnexpectedMessage.class.isAssignableFrom(error.getClass())) {
      return FastpayError.unexpeectedMessage();
    } else if (ClientIoError.class.isAssignableFrom(error.getClass())) {
      return FastpayError.clientIoError(((ClientIoError) error).error);
    } else {
      throw new RuntimeException("Unhandled bincode FastpayError type: " + error.getClass());
    }
  }

  /**
   * Convert a bincode-encoded error into the standard type used by fp4j.
   *
   * @param error An instance of {@link  com.sappenin.fastpay.core.bincode.FastPayError}.
   *
   * @return An instance of {@link FastpayError}.
   */
  // TODO: Unit Test (if this sticks around)
  public static FastPayError convert(final FastpayError error) {
    Objects.requireNonNull(error);

    if (FastPayError.InvalidSignature.class.isAssignableFrom(error.getClass())) {
      return new InvalidSignature(error.errorMessage());
    } else if (FastPayError.UnknownSigner.class.isAssignableFrom(error.getClass())) {
      return new UnknownSigner();
    } else if (FastPayError.CertificateRequiresQuorum.class.isAssignableFrom(error.getClass())) {
      return new CertificateRequiresQuorum();
    } else if (FastPayError.IncorrectTransferAmount.class.isAssignableFrom(error.getClass())) {
      return new IncorrectTransferAmount();
    } else if (FastPayError.UnexpectedSequenceNumber.class.isAssignableFrom(error.getClass())) {
      return new UnexpectedSequenceNumber();
    } else if (FastPayError.InsufficientFunding.class.isAssignableFrom(error.getClass())) {
      // TODO: FIXME!
      return new InsufficientFunding(null);
    } else if (FastPayError.PreviousTransferMustBeConfirmedFirst.class.isAssignableFrom(error.getClass())) {
//      TransferOrder transferOrder = TransferOrder.builder()
//        .transfer(Transfer.builder()
//
//          .build())
//        .build();
      // TODO: deserialize transferOrder and inject into here.
      // TODO: Add objects to immutables errors.
      return new PreviousTransferMustBeConfirmedFirst(null);
    } else if (FastPayError.ErrorWhileProcessingTransferOrder.class.isAssignableFrom(error.getClass())) {
      return new ErrorWhileProcessingTransferOrder();
    } else if (FastPayError.ErrorWhileRequestingCertificate.class.isAssignableFrom(error.getClass())) {
      return new ErrorWhileRequestingCertificate();
    } else if (FastPayError.MissingEalierConfirmations.class.isAssignableFrom(error.getClass())) {
      // TODO: SequenceNumber
      return new MissingEalierConfirmations(null);
    } else if (FastPayError.UnexpectedTransactionIndex.class.isAssignableFrom(error.getClass())) {
      return new UnexpectedTransactionIndex();
    } else if (FastPayError.CertificateNotfound.class.isAssignableFrom(error.getClass())) {
      return new CertificateNotfound();
    } else if (FastPayError.UnknownSenderAccount.class.isAssignableFrom(error.getClass())) {
      return new UnknownSenderAccount();
    } else if (FastPayError.CertificateAuthorityReuse.class.isAssignableFrom(error.getClass())) {
      return new CertificateAuthorityReuse();
    } else if (FastPayError.InvalidSequenceNumber.class.isAssignableFrom(error.getClass())) {
      return new InvalidSequenceNumber();
    } else if (FastPayError.SequenceOverflow.class.isAssignableFrom(error.getClass())) {
      return new SequenceOverflow();
    } else if (FastPayError.SequenceUnderflow.class.isAssignableFrom(error.getClass())) {
      return new SequenceUnderflow();
    } else if (FastPayError.AmountOverflow.class.isAssignableFrom(error.getClass())) {
      return new AmountOverflow();
    } else if (FastPayError.AmountUnderflow.class.isAssignableFrom(error.getClass())) {
      return new AmountUnderflow();
    } else if (FastPayError.BalanceOverflow.class.isAssignableFrom(error.getClass())) {
      return new BalanceOverflow();
    } else if (FastPayError.BalanceUnderflow.class.isAssignableFrom(error.getClass())) {
      return new BalanceUnderflow();
    } else if (FastPayError.WrongShard.class.isAssignableFrom(error.getClass())) {
      return new WrongShard();
    } else if (FastPayError.InvalidCrossShardUpdate.class.isAssignableFrom(error.getClass())) {
      return new InvalidCrossShardUpdate();
    } else if (FastPayError.InvalidDecoding.class.isAssignableFrom(error.getClass())) {
      return new InvalidDecoding();
    } else if (FastPayError.UnexpectedMessage.class.isAssignableFrom(error.getClass())) {
      return new UnexpectedMessage();
    } else if (FastPayError.ClientIoError.class.isAssignableFrom(error.getClass())) {
      return new ClientIoError(error.errorMessage());
    } else {
      throw new RuntimeException("Unhandled bincode FastpayError type: " + error.getClass());
    }
  }

  // TODO: Unit Test (if this sticks around)
  public static SequenceNumber toSerializableSequenceNumber(
    final com.sappenin.fastpay.core.SequenceNumber sequenceNumber
  ) {
    Objects.requireNonNull(sequenceNumber);
    return new SequenceNumber(sequenceNumber.value().longValue());
  }

  public static FastPayAddress convert(EdPublicKeyBytes address) {
    return FastPayAddress.builder()
      .edPublicKey(Ed25519PublicKey.fromBase16(toHexString(address.value)))
      .build();
  }

  // TODO Unit test
  public static String toHexString(List<@com.novi.serde.Unsigned Byte> bytes) {
    return BaseEncoding.base16().lowerCase().encode(Bytes.toArray(bytes));
  }

  // TODO Unit test
  public static List<@com.novi.serde.Unsigned Byte> fromHexString(final String hexValue) {
    final byte[] bytes = BaseEncoding.base16().decode(hexValue);
    return IntStream.range(0, bytes.length)
      .mapToObj(i -> bytes[i])
      .collect(Collectors.toList());
  }
}
