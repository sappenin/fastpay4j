package com.sappenin.fastpay.core.keys;

import com.google.common.io.BaseEncoding;
import java.util.Base64;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.Objects;

/**
 * Utilities to convert to/from public/private key types.
 */
public class KeyUtils {

  static {
    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
      final BouncyCastleProvider bcProvider = new BouncyCastleProvider();
      if (Security.addProvider(bcProvider) == -1) {
        throw new RuntimeException("Could not configure BouncyCastle provider");
      }
    }
  }

  /**
   * No-args Constructor to prevent instantiation.
   */
  private KeyUtils() {
  }

  /**
   * Convert from a {@link PrivateKey} to a {@link PublicKey}.
   *
   * @param privateKey A {@link PrivateKey}.
   *
   * @return A {@link PublicKey}.
   */
  public static PublicKey toPublicKey(final PrivateKey privateKey) {
    Objects.requireNonNull(privateKey);

    if (Ed25519PrivateKey.class.isAssignableFrom(privateKey.getClass())) {
      final Ed25519PrivateKeyParameters ed25519PrivateKeyParameters = toEd25519PrivateKeyParams(
        (Ed25519PrivateKey) privateKey
      );
      return toPublicKey(ed25519PrivateKeyParameters.generatePublicKey());
    } else {
      throw new IllegalArgumentException("Invalid PrivateKey type: " + privateKey.getClass());
    }
  }

  /**
   * Convert from a {@link Ed25519PrivateKeyParameters} to a {@link PrivateKey}.
   *
   * @param ed25519PrivateKeyParameters A {@link Ed25519PrivateKeyParameters}.
   *
   * @return A {@link PrivateKey}.
   */
  public static Ed25519PrivateKey toPrivateKey(final Ed25519PrivateKeyParameters ed25519PrivateKeyParameters) {
    Objects.requireNonNull(ed25519PrivateKeyParameters);
    return Ed25519PrivateKey.of(ed25519PrivateKeyParameters.getEncoded());
  }

  /**
   * Convert from a {@link Ed25519PrivateKeyParameters} to a {@link PrivateKey}.
   *
   * @param ed25519PrivateKeyParameters A {@link Ed25519PrivateKeyParameters}.
   *
   * @return A {@link PrivateKey}.
   */
  public static Ed25519PublicKey toPublicKey(final Ed25519PrivateKeyParameters ed25519PrivateKeyParameters) {
    Objects.requireNonNull(ed25519PrivateKeyParameters);
    return toPublicKey(ed25519PrivateKeyParameters.generatePublicKey());
  }

  /**
   * Convert from a {@link PublicKey} to a {@link Ed25519PublicKeyParameters}.
   *
   * @param ed25519PublicKey A {@link PublicKey} with
   *
   * @return A {@link Ed25519PublicKeyParameters}.
   */
  public static Ed25519PublicKeyParameters toEd25519PublicKeyParameters(final Ed25519PublicKey ed25519PublicKey) {
    Objects.requireNonNull(ed25519PublicKey);
    return new Ed25519PublicKeyParameters(ed25519PublicKey.value(), 0);
  }

  /**
   * Convert from a {@link Ed25519PublicKeyParameters} to a {@link PublicKey}.
   *
   * @param ed25519PublicKeyParameters A {@link Ed25519PublicKeyParameters}.
   *
   * @return A {@link PublicKey}.
   */
  public static Ed25519PublicKey toPublicKey(final Ed25519PublicKeyParameters ed25519PublicKeyParameters) {
    Objects.requireNonNull(ed25519PublicKeyParameters);
    return Ed25519PublicKey.of(ed25519PublicKeyParameters.getEncoded());
  }


  /**
   * Convert from a {@link PrivateKey} to a {@link Ed25519PrivateKeyParameters}.
   *
   * @param privateKey A {@link PrivateKey}.
   *
   * @return A {@link Ed25519PrivateKeyParameters}.
   */
  public static Ed25519PrivateKeyParameters toEd25519PrivateKeyParams(Ed25519PrivateKey privateKey) {
    Objects.requireNonNull(privateKey);
    return new Ed25519PrivateKeyParameters(privateKey.value());
  }

  /**
   * Given a base64-encoded `SecretKey` from Fastpay (which contains 64-bytes, the first 32 bytes being the private key,
   * and the next 32 bytes containing the public key), transform this data into a {@link KeyPair}.
   *
   * @param fastpaySecretKeyB64 A a base64-encoded `SecretKey` from Fastpay (which contains 64-bytes, the first 32 bytes
   *                            being the private key, and the next 32 bytes containing the public key.
   *
   * @return A {@link KeyPair}.
   */
  public static KeyPair fromFastpaySecretKeyB64(final String fastpaySecretKeyB64) {
    Objects.requireNonNull(fastpaySecretKeyB64);

    byte[] secretBytes = BaseEncoding.base64().decode(fastpaySecretKeyB64);
    byte[] privateKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 0, privateKeyBytes, 0, 32);
    byte[] publicKeyBytes = new byte[32];
    System.arraycopy(secretBytes, 32, publicKeyBytes, 0, 32);

    return KeyPair.builder()
      .publicKey(Ed25519PublicKey.of(publicKeyBytes))
      .privateKey(Ed25519PrivateKey.of(privateKeyBytes))
      .build();
  }

  /**
   * Given private and public keys, transform it into serialized base64-encoded `SecretKey` from Fastpay.
   */
  public static String toFastPaySecretKeyB64(final Ed25519PrivateKey privateKey, final Ed25519PublicKey publicKey) {
    final byte[] privateKeyBytes = privateKey.value();
    final byte[] publicKeyBytes = publicKey.value();

    final byte[] secretBytes = new byte[privateKeyBytes.length + publicKeyBytes.length];
    System.arraycopy(privateKeyBytes, 0, secretBytes, 0, privateKeyBytes.length);
    System.arraycopy(publicKeyBytes, 0, secretBytes, privateKeyBytes.length, publicKeyBytes.length);

    return Base64.getEncoder().encodeToString(secretBytes);
  }
}
