package money.fluid.fastpay4j.server.settings;

import money.fluid.fastpay4j.core.keys.Ed25519PrivateKey;

import java.util.List;

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
}
