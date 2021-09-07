package com.sappenin.fastpay.core.keys;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.BaseEncoding;
import java.util.stream.Stream;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.junit.jupiter.api.Test;

import javax.security.auth.DestroyFailedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for {@link KeyUtils}.
 */
class KeyUtilsTest {

  // BouncyCastle Encoding
  private static final String ED_PRIVATE_KEY_HEX = "B224AFDCCEC7AA4E245E35452585D4FBBE37519BCA3929578BFC5BBD4640E163";
  private static final String ED_PUBLIC_KEY_HEX = "94F8F262A639D6C88B9EFC29F4AA8B1B8E0B7D9143A17733179A388FD26CC3AE";
  private static final String DESTROYED_ED_PRIVATE_KEY_HEX
    = "0000000000000000000000000000000000000000000000000000000000000000";

  // Fastpay4j Encoding
  private static final String FP_PUBLIC_KEY1_B64 = "AzKoFE7qtEvQk+GHKt1YFJj28Pvk56rjV2T+fLowB+E=";
  private static final String FP_PUBLIC_KEY2_B64 = "A2cEntd9tcAMLsgayRNl6owrMOI5gEFtw1VoDayGAHg=";

  private static final String FP_PRIVATE_KEY1_B64 = "hPDiMD6fD3PNKH8SsEkGiETzmoGudNtNi3zuFGRkuecDMqgUTuq0S9CT4Ycq3VgUmPbw++TnquNXZP58ujAH4Q==";
  private static final byte[] PRIVATE_KEY1_BYTES = {-124, -16, -30, 48, 62, -97, 15, 115, -51, 40, 127, 18, -80, 73, 6, -120, 68, -13, -102, -127, -82, 116, -37, 77, -117, 124, -18, 20, 100, 100, -71, -25};
  private static final byte[] PUBLIC_KEY1_BYTES = {3, 50, -88, 20, 78, -22, -76, 75, -48, -109, -31, -121, 42, -35, 88, 20, -104, -10, -16, -5, -28, -25, -86, -29, 87, 100, -2, 124, -70, 48, 7, -31};

  private static final String FP_PRIVATE_KEY2_B64 = "rcc3gEhS6sXf/0sic9DionRoh2D/hjmw0EhFJ9VpyBwDZwSe1321wAwuyBrJE2XqjCsw4jmAQW3DVWgNrIYAeA==";
  private static final byte[] PRIVATE_KEY2_BYTES = {-83, -57, 55, -128, 72, 82, -22, -59, -33, -1, 75, 34, 115, -48, -30, -94, 116, 104, -121, 96, -1, -122, 57, -80, -48, 72, 69, 39, -43, 105, -56, 28};
  private static final byte[] PUBLIC_KEY2_BYTES = {3, 103, 4, -98, -41, 125, -75, -64, 12, 46, -56, 26, -55, 19, 101, -22, -116, 43, 48, -30, 57, -128, 65, 109, -61, 85, 104, 13, -84, -122, 0, 120};

  @Test
  void edPrivateKeyParametersToPrivateKeyAndBack() {
    Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = new Ed25519PrivateKeyParameters(
      BaseEncoding.base16().decode(ED_PRIVATE_KEY_HEX), 0
    );

    // To PrivateKey
    Ed25519PrivateKey privateKey = KeyUtils.toPrivateKey(ed25519PrivateKeyParameters);
    assertThat(privateKey.isDestroyed()).isFalse();
    assertThat(BaseEncoding.base16().encode(privateKey.value())).isEqualTo(ED_PRIVATE_KEY_HEX);

    // Convert back
    Ed25519PrivateKeyParameters converted = KeyUtils.toEd25519PrivateKeyParams(privateKey);
    assertThat(converted).usingRecursiveComparison().isEqualTo(ed25519PrivateKeyParameters);
  }

  @Test
  void edPublicKeyParametersToPublicKeyAndBack() {
    Ed25519PublicKeyParameters ed25519PublicKeyParameters = new Ed25519PrivateKeyParameters(
      BaseEncoding.base16().decode(ED_PRIVATE_KEY_HEX), 0
    ).generatePublicKey();

    Ed25519PublicKey publicKey = KeyUtils.toPublicKey(ed25519PublicKeyParameters);
    assertThat(BaseEncoding.base16().encode(publicKey.value())).isEqualTo(ED_PUBLIC_KEY_HEX);

    Ed25519PublicKeyParameters converted = KeyUtils.toEd25519PublicKeyParameters(publicKey);
    assertThat(converted).usingRecursiveComparison().isEqualTo(ed25519PublicKeyParameters);
  }

  @Test
  void toPublicKeyEd() {
    Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = new Ed25519PrivateKeyParameters(
      BaseEncoding.base16().decode(ED_PRIVATE_KEY_HEX), 0
    );
    PrivateKey privateKey = KeyUtils.toPrivateKey(ed25519PrivateKeyParameters);

    // To Public Key
    PublicKey publicKey = KeyUtils.toPublicKey(privateKey);
    assertThat(BaseEncoding.base16().encode(publicKey.value())).isEqualTo(ED_PUBLIC_KEY_HEX);
  }

  @Test
  void destroyPrivateKey() throws DestroyFailedException {
    Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = new Ed25519PrivateKeyParameters(
      BaseEncoding.base16().decode(ED_PRIVATE_KEY_HEX), 0
    );

    // To PrivateKey
    PrivateKey privateKey = KeyUtils.toPrivateKey(ed25519PrivateKeyParameters);
    assertThat(privateKey.isDestroyed()).isFalse();
    assertThat(BaseEncoding.base16().encode(privateKey.value())).isEqualTo(ED_PRIVATE_KEY_HEX);

    privateKey.destroy();
    assertThat(privateKey.isDestroyed()).isTrue();
    assertThat(BaseEncoding.base16().encode(privateKey.value())).isEqualTo(DESTROYED_ED_PRIVATE_KEY_HEX);

    privateKey.destroy();
    assertThat(privateKey.isDestroyed()).isTrue();
    assertThat(BaseEncoding.base16().encode(privateKey.value())).isEqualTo(DESTROYED_ED_PRIVATE_KEY_HEX);
  }

  @ParameterizedTest
  @ValueSource(strings = {FP_PUBLIC_KEY1_B64, FP_PUBLIC_KEY2_B64})
  void fastPayPublicKey1ConversionTests(final String key) {
    final Ed25519PublicKeyParameters publicKeyParams = new Ed25519PublicKeyParameters(
      BaseEncoding.base64().decode(key), 0);
    // To publicKey
    final Ed25519PublicKey publicKey = KeyUtils.toPublicKey(publicKeyParams);
    // Convert back
    final Ed25519PublicKeyParameters converted = KeyUtils.toEd25519PublicKeyParameters(publicKey);
    assertThat(BaseEncoding.base64().encode(converted.getEncoded())).isEqualTo(key);
  }

  @Test
  void fastPayPrivateKey1ConversionTests() {
    byte[] secretBytes = BaseEncoding.base64().decode(FP_PRIVATE_KEY1_B64);
    byte[] privateKeyBytes = new byte[32];
    byte[] publicKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 0, privateKeyBytes, 0, 32);
    System.arraycopy(secretBytes, 32, publicKeyBytes, 0, 32);

    Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = new Ed25519PrivateKeyParameters(privateKeyBytes);
    assertThat(BaseEncoding.base64().encode(ed25519PrivateKeyParameters.generatePublicKey().getEncoded()))
      .isEqualTo(FP_PUBLIC_KEY1_B64);
    Ed25519PublicKeyParameters expectedPublicKey = new Ed25519PublicKeyParameters(publicKeyBytes);
    assertThat(BaseEncoding.base64().encode(ed25519PrivateKeyParameters.generatePublicKey().getEncoded()))
      .isEqualTo(BaseEncoding.base64().encode(expectedPublicKey.getEncoded()));
  }

  @ParameterizedTest
  @ValueSource(strings = {FP_PRIVATE_KEY1_B64, FP_PRIVATE_KEY2_B64})
  void fromFastPaySecretKey1B64(final String key) {
    byte[] secretBytes = BaseEncoding.base64().decode(key);
    byte[] privateKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 0, privateKeyBytes, 0, 32);
    byte[] publicKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 32, publicKeyBytes, 0, 32);

    final Ed25519PublicKey expectedPublicKey = Ed25519PublicKey.of(publicKeyBytes);
    final Ed25519PrivateKey expectedPrivateKey = Ed25519PrivateKey.of(privateKeyBytes);

    final KeyPair keyPair = KeyUtils.fromFastpaySecretKeyB64(key);
    assertThat(keyPair.publicKey()).isEqualTo(expectedPublicKey);
    assertThat(keyPair.privateKey()).isEqualTo(expectedPrivateKey);
  }

  @ParameterizedTest
  @MethodSource("providePrivateAndPublicKeyBytes")
  void toFastPaySecretKey1(final byte[] privateKey, final byte[] publicKey, final String expected) {
    final Ed25519PrivateKey expectedPrivateKey = Ed25519PrivateKey.of(privateKey);
    final Ed25519PublicKey expectedPublicKey = Ed25519PublicKey.of(publicKey);

    final String keyPair = KeyUtils.toFastPaySecretKeyB64(expectedPrivateKey, expectedPublicKey);
    assertThat(keyPair).isEqualTo(expected);
  }

  private static Stream<Arguments> providePrivateAndPublicKeyBytes() {
    return Stream.of(
        Arguments.of(PRIVATE_KEY1_BYTES, PUBLIC_KEY1_BYTES, FP_PRIVATE_KEY1_B64),
        Arguments.of(PRIVATE_KEY2_BYTES, PUBLIC_KEY2_BYTES, FP_PRIVATE_KEY2_B64)
    );
  }
}