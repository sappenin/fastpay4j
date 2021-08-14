package com.sappenin.fastpay.core.serde;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.io.BaseEncoding;
import com.sappenin.fastpay.core.FastpayError;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.bincode.FastPayError;
import com.sappenin.fastpay.core.bincode.FastPayError.InvalidSignature;
import com.sappenin.fastpay.core.bincode.FastPayError.UnknownSigner;
import com.sappenin.fastpay.core.bincode.SequenceNumber;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Unit tests for {@link BincodeSerde}.
 */
public class BincodeSerializerTest {

  private BincodeSerde bincodeSerializer;

  @BeforeEach
  public void setUp() {
    this.bincodeSerializer = new BincodeSerde();
  }

  @Test
  void bincodeSerdeAccountInfoRequest() {
    String request1BytesHex = "0500000020202020202020202020202020202020202020202020202020202020202020200000";
    String request2BytesHex
      = "05000000202020202020202020202020202020202020202020202020202020202020202001810000000000000000";

    //////
    // Ser
    AccountInfoRequest request1 = createAccountInfoRequest1();
    String actual = BaseEncoding.base16().encode(bincodeSerializer.serialize(request1));
    assertThat(actual).isEqualTo(request1BytesHex);

    AccountInfoRequest request2 = createAccountInfoRequest2();
    actual = BaseEncoding.base16().encode(bincodeSerializer.serialize(request2));
    assertThat(actual).isEqualTo(request2BytesHex);

    //////
    // Deser
    byte[] accountInfoRequestBytes = BaseEncoding.base16().decode(request1BytesHex);
    AccountInfoRequest actualAccountInfoRequest = this.bincodeSerializer.deserialize(
      AccountInfoRequest.class, accountInfoRequestBytes
    );
    AccountInfoRequest expectedAccountInfoRequest = createAccountInfoRequest1();
    assertThat(actualAccountInfoRequest).isEqualTo(expectedAccountInfoRequest);

    accountInfoRequestBytes = BaseEncoding.base16().decode(request2BytesHex);
    actualAccountInfoRequest = this.bincodeSerializer.deserialize(
      AccountInfoRequest.class, accountInfoRequestBytes
    );
    expectedAccountInfoRequest = createAccountInfoRequest2();
    assertThat(actualAccountInfoRequest).isEqualTo(expectedAccountInfoRequest);
  }

  @Test
  void bincodeSerdeFastpayError() {
    String errorHex = "0400000001000000";

    // Ser
    FastPayError error = new UnknownSigner();
    String actual = BaseEncoding.base16().encode(bincodeSerializer.serialize(error));
    assertThat(actual).isEqualTo(errorHex);

    // Deser
    byte[] errorBytes = BaseEncoding.base16().decode(errorHex);
    FastpayError actualFastpayError = this.bincodeSerializer.deserialize(FastpayError.class, errorBytes);
    FastpayError expectedFastpayError = FastpayError.unknownSigner();
    assertThat(actualFastpayError).isEqualTo(expectedFastpayError);
  }

  @Test
  void bincodeSerdeFastpayErrorWithCustomMessage() {
    String errorHex = "04000000000000000300000000000000666F6F";

    // Ser
    FastPayError error = new InvalidSignature("foo");
    String actual = BaseEncoding.base16().encode(bincodeSerializer.serialize(error));
    assertThat(actual).isEqualTo(errorHex);

    // Deser
    byte[] errorBytes = BaseEncoding.base16().decode(errorHex);
    FastpayError actualFastpayError = this.bincodeSerializer.deserialize(FastpayError.class, errorBytes);
    FastpayError expectedFastpayError = FastpayError.invalidSignature("foo");
    assertThat(actualFastpayError).isEqualTo(expectedFastpayError);
  }

  //////////////////
  // Private Helpers
  //////////////////

  private AccountInfoRequest createAccountInfoRequest1() {
    final AccountInfoRequest.Builder builder = new AccountInfoRequest.Builder();

    builder.sender = BincodeConversions.toEdPublicKeyBytes(
      Ed25519PublicKey.builder()
        .bytes(BaseEncoding.base16().decode("2020202020202020202020202020202020202020202020202020202020202020"))
        .build());
    builder.request_sequence_number = Optional.empty();
    builder.request_received_transfers_excluding_first_nth = Optional.empty();
    final AccountInfoRequest request = builder.build();
    return request;
  }

  private AccountInfoRequest createAccountInfoRequest2() {
    final AccountInfoRequest.Builder builder = new AccountInfoRequest.Builder();

    builder.sender = BincodeConversions.toEdPublicKeyBytes(
      Ed25519PublicKey.builder()
        .bytes(BaseEncoding.base16().decode("2020202020202020202020202020202020202020202020202020202020202020"))
        .build());
    builder.request_sequence_number = Optional.of(new SequenceNumber(Long.valueOf(129L)));
    builder.request_received_transfers_excluding_first_nth = Optional.empty();
    final AccountInfoRequest request = builder.build();
    return request;
  }
}