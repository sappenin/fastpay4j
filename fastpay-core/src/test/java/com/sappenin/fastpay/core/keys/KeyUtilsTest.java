package com.sappenin.fastpay.core.keys;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.BaseEncoding;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.junit.jupiter.api.Test;

import javax.security.auth.DestroyFailedException;

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
  private static final String FP_PRIVATE_KEY2_B64 = "rcc3gEhS6sXf/0sic9DionRoh2D/hjmw0EhFJ9VpyBwDZwSe1321wAwuyBrJE2XqjCsw4jmAQW3DVWgNrIYAeA==";

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

  @Test
  void fastPayPublicKey1ConversionTests() {
    Ed25519PublicKeyParameters publicKeyParams = new Ed25519PublicKeyParameters(
      BaseEncoding.base64().decode(FP_PUBLIC_KEY1_B64), 0);
    // To publicKey
    Ed25519PublicKey publicKey = KeyUtils.toPublicKey(publicKeyParams);
    // Convert back
    Ed25519PublicKeyParameters converted = KeyUtils.toEd25519PublicKeyParameters(publicKey);
    assertThat(BaseEncoding.base64().encode(converted.getEncoded())).isEqualTo(FP_PUBLIC_KEY1_B64);
  }

  @Test
  void fastPayPublicKey2ConversionTests() {
    Ed25519PublicKeyParameters publicKeyParams = new Ed25519PublicKeyParameters(
      BaseEncoding.base64().decode(FP_PUBLIC_KEY2_B64), 0);
    // To publicKey
    Ed25519PublicKey publicKey = KeyUtils.toPublicKey(publicKeyParams);
    // Convert back
    Ed25519PublicKeyParameters converted = KeyUtils.toEd25519PublicKeyParameters(publicKey);
    assertThat(BaseEncoding.base64().encode(converted.getEncoded())).isEqualTo(FP_PUBLIC_KEY2_B64);
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

  @Test
  void fromFastpaySecretKey1B64() {
    byte[] secretBytes = BaseEncoding.base64().decode(FP_PRIVATE_KEY1_B64);
    byte[] privateKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 0, privateKeyBytes, 0, 32);
    byte[] publicKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 32, publicKeyBytes, 0, 32);

    Ed25519PublicKey expectedPublicKey = Ed25519PublicKey.of(publicKeyBytes);
    Ed25519PrivateKey expectedPrivateKey = Ed25519PrivateKey.of(privateKeyBytes);

    KeyPair keyPair = KeyUtils.fromFastpaySecretKeyB64(FP_PRIVATE_KEY1_B64);
    assertThat(keyPair.publicKey()).isEqualTo(expectedPublicKey);
    assertThat(keyPair.privateKey()).isEqualTo(expectedPrivateKey);
  }

  @Test
  void fromFastpaySecretKey2B64() {
    byte[] secretBytes = BaseEncoding.base64().decode(FP_PRIVATE_KEY2_B64);
    byte[] privateKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 0, privateKeyBytes, 0, 32);
    byte[] publicKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 32, publicKeyBytes, 0, 32);

    Ed25519PublicKey expectedPublicKey = Ed25519PublicKey.of(publicKeyBytes);
    Ed25519PrivateKey expectedPrivateKey = Ed25519PrivateKey.of(privateKeyBytes);

    KeyPair keyPair = KeyUtils.fromFastpaySecretKeyB64(FP_PRIVATE_KEY2_B64);
    assertThat(keyPair.publicKey()).isEqualTo(expectedPublicKey);
    assertThat(keyPair.privateKey()).isEqualTo(expectedPrivateKey);
  }

}