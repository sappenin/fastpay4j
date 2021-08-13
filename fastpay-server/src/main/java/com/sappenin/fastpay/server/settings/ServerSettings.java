package com.sappenin.fastpay.server.settings;

import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.core.AuthorityName;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import org.immutables.value.Value.Derived;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A typed version of runtime properties for a Fastpay4j server.
 */
public interface ServerSettings {

  /**
   * @return
   */
  AuthoritySettings authority();

  /**
   * The server-secret for this authority server.
   *
   * @return A {@link Ed25519PrivateKey}.
   *
   * @deprecated This value will be replaced by key-metadata that can be used to locate the server key so that the key
   *   isn't required to be in-memory. We might also consider deriving this value from a seed value, or some other
   *   mechanism.
   */
  Ed25519PrivateKey serverKey();

  /**
   * The authorities that this server communicates with for consensus.
   *
   * @return A {@link List} of type {@link AuthoritySettings}.
   */
  List<AuthoritySettings> committees();

  @Derived
  default TreeMap<AuthorityName, UnsignedLong> votingRights() {
    final Function<AuthoritySettings, AuthorityName> keyMapper = (authoritySettings) -> AuthorityName.builder()
      .edPublicKey(authoritySettings.fastPayAddress().edPublicKey())
      .build();
    final Function<AuthoritySettings, UnsignedLong> valueMapper = $ -> UnsignedLong.ONE;

    return this.committees().stream().collect(Collectors.toMap(
      keyMapper,
      valueMapper,
      (x, y) -> {
        throw new IllegalStateException("Duplicate Committee Addresses are not allowed");
      },
      TreeMap::new
    ));
  }
}
