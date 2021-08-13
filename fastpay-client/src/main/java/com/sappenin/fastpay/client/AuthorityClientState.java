package com.sappenin.fastpay.client;

import com.sappenin.fastpay.core.AuthorityName;
import com.sappenin.fastpay.core.Committee;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.SequenceNumber;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.messages.TransferOrder;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.SecretKey;

/**
 * The current state of an Authority client.
 */
@Immutable
public interface AuthorityClientState {

  static ImmutableAuthorityClientState.Builder builder() {
    return ImmutableAuthorityClientState.builder();
  }

  /**
   * Our FastPay address.
   *
   * @return A {@link FastPayAddress}.
   */
  FastPayAddress address();

  /**
   * Our signature key.
   *
   * @return A {@link SecretKey}.
   */
  Ed25519PrivateKey secret();

  /**
   * Our FastPay committee.
   *
   * @return A {@link Committee}.
   */
  Committee committee();

  /**
   * How to talk to this committee.
   *
   * @return A {@link Map} with keys of type {@link AuthorityName} and values of type {@link AuthorityClient}.
   */
  Map<AuthorityName, AuthorityClient> authorityClients();

  /**
   * Expected sequence number for the next certified transfer. This is also the number of transfer certificates that we
   * have created.
   *
   * @return A {@link SequenceNumber}.
   */
  SequenceNumber nextSequenceNumber();

  /**
   * A pending transfer that has not yet been signed by all authorities.
   *
   * @return A {@link TransferOrder}.
   */
  // TODO: Consider instead making this class mutable, or better a service that can locate/store pending transfer orders
  // that accepts a client state as a key...
  @Default
  default AtomicReference<Optional<TransferOrder>> pendingTransferOrder() {
    return new AtomicReference<>(Optional.empty());
  }

  /**
   * The known spendable balance (including a possible initial funding, excluding unknown sent or received
   * certificates).
   *
   * @return A {@link BigInteger}.
   */
  @Default
  default BigInteger balance() {
    return BigInteger.ZERO;
  }
}
