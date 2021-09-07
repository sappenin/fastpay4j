package com.sappenin.fastpay.client.cli;

import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import com.sappenin.fastpay.core.keys.KeyUtils;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;

public final class Utils {

  public static UserAccount generateUserAccountWithInitialFunding(final long initialFunding,
      final SecureRandom random) {
    final Ed25519PrivateKeyParameters parameters = new Ed25519PrivateKeyParameters(random);
    final Ed25519PublicKey publicKey = KeyUtils.toPublicKey(parameters);
    final Ed25519PrivateKey privateKey = KeyUtils.toPrivateKey(parameters);

    final String address = publicKey.asBase64();
    final String keyPair = KeyUtils.toFastPaySecretKeyB64(privateKey, publicKey);

    return new UserAccount(address, keyPair, initialFunding);
  }
}
