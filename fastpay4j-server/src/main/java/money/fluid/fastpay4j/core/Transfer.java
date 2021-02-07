package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

/**
 * Holds all information to model a transfer of funds by a FastPay account.
 */
@Value.Immutable
public interface Transfer {

  /**
   * A builder.
   *
   * @return A {@link ImmutableTransfer.Builder}.
   */
  static ImmutableTransfer.Builder builder() {
    return ImmutableTransfer.builder();
  }

  /**
   * The sender’s FastPay address, written `sender(𝑂)`.
   *
   * @return A {@link FastPayAddress}.
   */
  FastPayAddress sender();

  /**
   * The recipient, either a FastPay or a Primary address, written `recipient(𝑂)`.
   *
   * @return An {@link Address}.
   */
  Address recipient();

  /**
   * A non-negative amount to transfer, written `amount(𝑂)`.
   *
   * @return An {@link UnsignedLong}.
   */
  UnsignedLong amount();

  /**
   * A sequence number `sequence(𝑂)`.
   *
   * @return An {link {@link UnsignedLong}.
   */
  UnsignedLong sequenceNumber();

  /**
   * Optional user-provided data.
   *
   * @return A {@link UserData}.
   */
  @Default
  default UserData userData() {
    return UserData.NO_USER_DATA;
  }

}
