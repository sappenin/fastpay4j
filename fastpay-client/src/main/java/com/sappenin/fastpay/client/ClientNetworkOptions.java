package com.sappenin.fastpay.client;

import com.sappenin.fastpay.core.NetworkProtocol;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import java.io.File;
import java.time.Duration;

/**
 * Network options for a Fastpay client (either an Authority Client or a Primary Client).
 */
@Immutable
public interface ClientNetworkOptions {

  static ImmutableClientNetworkOptions.Builder builder() {
    return ImmutableClientNetworkOptions.builder();
  }

  NetworkProtocol networkProtocol();

  String baseAddress();

  int basePort();

  int numShards();

  /**
   * The file storing the state of our user accounts (an empty one will be created if missing)
   *
   * @return A {@link File}.
   */
  //File accounts();

  /**
   *
   * @return
   */
  //File committee();

  /**
   * Timeout for sending queries.
   *
   * @return
   */
  @Default
  default Duration sendTimeout() {
    return Duration.ofMillis(400); // 400000 us
  }

  /**
   * Timeout for receiving responses (us).
   *
   * @return
   */
  @Default
  default Duration receiveTimeout() {
    return Duration.ofMillis(400); // 400000 us
  }

  // TODO: buffer size? Why is it a String? What is `transport::DEFAULT_MAX_DATAGRAM_SIZE`?
}
