package com.sappenin.fastpay.core.serde;

import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import com.sappenin.fastpay.core.FastpayError;
import com.sappenin.fastpay.core.FastpayException;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.bincode.AccountInfoResponse;
import com.sappenin.fastpay.core.bincode.FastPayError;
import com.sappenin.fastpay.core.bincode.SerializedMessage;
import com.sappenin.fastpay.core.bincode.SerializedMessage.Error;
import com.sappenin.fastpay.core.bincode.SerializedMessage.InfoReq;
import com.sappenin.fastpay.core.bincode.SerializedMessage.InfoResp;

import java.util.Objects;

/**
 * Serializes and deserializes bincode to/from Java.
 */
public class BincodeSerde implements Serde {

  @Override
  public <T> byte[] serialize(final T input) {
    Objects.requireNonNull(input);

    if (com.sappenin.fastpay.core.messages.AccountInfoRequest.class.isAssignableFrom(input.getClass())) {
      com.sappenin.fastpay.core.bincode.AccountInfoRequest converted = BincodeConversions.convert(
        (com.sappenin.fastpay.core.messages.AccountInfoRequest) input
      );
      return this.serializeAccountInfoRequest(converted);
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
    try {
      if (com.sappenin.fastpay.core.messages.AccountInfoRequest.class.isAssignableFrom(clazz)) {
        AccountInfoRequest bincode = this.deserializeAccountInfoRequest(bytes);
        return (T) BincodeConversions.convert(bincode);
      }
      if (com.sappenin.fastpay.core.messages.AccountInfoResponse.class.isAssignableFrom(clazz)) {
        AccountInfoResponse bincode = this.deserializeAccountInfoResponse(bytes);
        return (T) BincodeConversions.convert(bincode);
      }
      if (FastpayError.class.isAssignableFrom(clazz)) {
        return (T) BincodeConversions.convert(this.deserializeError(bytes));
      } else {
        throw new IllegalStateException(String.format("Unsupported bincode deserialization type: %s", clazz));
      }
    } catch (DeserializationError | ClassCastException e) {
      FastPayError fastPayError = this.deserializeError(bytes);
      FastpayError fastpayError = BincodeConversions.convert(fastPayError);
      throw new FastpayException(fastpayError);
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
  private AccountInfoRequest deserializeAccountInfoRequest(final byte[] accountInfoRequestBytes)
    throws DeserializationError {
    Objects.requireNonNull(accountInfoRequestBytes);
    final SerializedMessage serializedMessage = SerializedMessage.bincodeDeserialize(accountInfoRequestBytes);
    final InfoReq infoReq = (InfoReq) serializedMessage;
    return infoReq.value;
  }

  private AccountInfoResponse deserializeAccountInfoResponse(byte[] bytes) throws DeserializationError {
    Objects.requireNonNull(bytes);
    final SerializedMessage serializedMessage = SerializedMessage.bincodeDeserialize(bytes);
    final InfoResp infoResp = (InfoResp) serializedMessage;
    return infoResp.value;
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
  private FastPayError deserializeError(final byte[] errorBytes) {
    Objects.requireNonNull(errorBytes);

    try {
      final SerializedMessage serializedMessage = SerializedMessage.bincodeDeserialize(errorBytes);
      final Error error = (Error) serializedMessage;
      return error.value;
    } catch (DeserializationError e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


}
