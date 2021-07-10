package money.fluid.fastpay4j.core;

import money.fluid.fastpay4j.core.keys.PublicKey;

/**
 * FastPay involves two types of participants: (i) authorities, and (ii) account owners (users, for short). All
 * participants generate a key pair consisting of a private signature key and the corresponding public verification
 * key.
 */
// TODO: Delete this interface if it's only used on AccountState since it's not in Rust and maybe doesn't add value.
public interface Participant {

  /**
   * The public key of this participant.
   *
   * @return A {@link PublicKey}.
   */
  PublicKey publicKey();
}
