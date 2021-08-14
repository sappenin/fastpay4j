package com.sappenin.fastpay.core.serde;

import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import com.sappenin.fastpay.core.FastpayError;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
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
import com.sappenin.fastpay.core.bincode.SerializedMessage;
import com.sappenin.fastpay.core.bincode.SerializedMessage.Error;
import com.sappenin.fastpay.core.bincode.SerializedMessage.InfoReq;

import java.util.Objects;

/**
 * Serializes and deserializes bincode to/from Java.
 */
public class BincodeSerde implements Serde {

  @Override
  public <T> byte[] serialize(final T input) {
    Objects.requireNonNull(input);

    if (AccountInfoRequest.class.isAssignableFrom(input.getClass())) {
      return this.serializeAccountInfoRequest((AccountInfoRequest) input);
    }
//    else if (InterledgerRejectPacket.class.isAssignableFrom(this.getClass())) {
//      rejectHandler.accept((InterledgerRejectPacket) this);
//    }
    if (FastPayError.class.isAssignableFrom(input.getClass())) {
      return this.serializeError((FastPayError) input); // Note capitalization delta ("P")
    } else {
      throw new IllegalStateException(String.format("Unsupported bincode serialization type: %s", input.getClass()));
    }
  }

  @Override
  public <T extends Object> T deserialize(final Class<T> clazz, final byte[] bytes) {
    Objects.requireNonNull(clazz);
    Objects.requireNonNull(bytes);

    if (AccountInfoRequest.class.isAssignableFrom(clazz)) {
      return (T) this.deserializeAccountInfoRequest(bytes);
    }
//    else if (InterledgerRejectPacket.class.isAssignableFrom(this.getClass())) {
//      rejectHandler.accept((InterledgerRejectPacket) this);
//    }
    if (FastpayError.class.isAssignableFrom(clazz)) {
      return (T) this.deserializeError(bytes);
    } else {
      throw new IllegalStateException(String.format("Unsupported bincode deserialization type: %s", clazz));
    }
  }

  /**
   * Convert a bincode-encoded error into the standard type used by fp4j.
   *
   * @param error An instance of {@link  com.sappenin.fastpay.core.bincode.FastPayError}.
   *
   * @return An instance of {@link FastpayError}.
   */
  // TODO: Unit test
  private FastpayError fromBincode(final com.sappenin.fastpay.core.bincode.FastPayError error) {
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

  //////////////////
  // Private Helpers
  //////////////////

  /**
   * Serialize an {@link AccountInfoRequest} to an array of bytes.
   *
   * @param accountInfoRequest A {@link AccountInfoRequest} to serialize.
   *
   * @return an array of bytes.
   */
  private byte[] serializeAccountInfoRequest(final AccountInfoRequest accountInfoRequest) {
    Objects.requireNonNull(accountInfoRequest);

    try {
      return new InfoReq(accountInfoRequest).bincodeSerialize();
    } catch (SerializationError e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Deserialize an {@link AccountInfoRequest} to an array of bincode-encoded bytes.
   *
   * @param accountInfoRequestBytes An array of bytes.
   *
   * @return A deserialized {@link AccountInfoRequest}.
   */
  private AccountInfoRequest deserializeAccountInfoRequest(final byte[] accountInfoRequestBytes) {
    Objects.requireNonNull(accountInfoRequestBytes);
    try {
      final SerializedMessage serializedMessage = SerializedMessage.bincodeDeserialize(accountInfoRequestBytes);
      final InfoReq infoReq = (InfoReq) serializedMessage;
      final AccountInfoRequest accountInfoRequest = infoReq.value;
      return accountInfoRequest;
    } catch (DeserializationError e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Serialize an {@link FastpayError} to an array of bincode-encoded bytes.
   *
   * @param fastPayError A {@link FastpayError} to serialize.
   *
   * @return an array of bytes.
   */
  private byte[] serializeError(final FastPayError fastPayError) {
    Objects.requireNonNull(fastPayError);
    try {
      return new Error(fastPayError).bincodeSerialize();
    } catch (SerializationError e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * Deserialize an {@link FastpayError} from an array of bincode-encoded bytes.
   *
   * @param errorBytes An array of bytes.
   *
   * @return A deserialized {@link FastpayError}.
   */
  private FastpayError deserializeError(final byte[] errorBytes) {
    Objects.requireNonNull(errorBytes);

    try {
      final SerializedMessage serializedMessage = SerializedMessage.bincodeDeserialize(errorBytes);
      final Error error = (Error) serializedMessage;
      final FastPayError fastPayError = error.value;
      return this.fromBincode(fastPayError);
    } catch (DeserializationError e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


}
