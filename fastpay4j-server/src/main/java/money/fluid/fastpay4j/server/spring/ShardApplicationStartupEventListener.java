package money.fluid.fastpay4j.server.spring;

import com.google.common.io.BaseEncoding;
import money.fluid.fastpay4j.server.settings.ServerShardSettings;
import money.fluid.fastpay4j.tcp.client.ClientHandshakeState;
import money.fluid.fastpay4j.tcp.client.ClientManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.RetrySpec;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @see "https://medium.com/@nikola.babic1/how-to-implement-a-custom-handshaking-protocol-via-tcp-using-reactor-netty-d8bcd2fe6ee7"
 */
@Component
public class ShardApplicationStartupEventListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  Environment environment;

  @Autowired
  @Deprecated
  private ClientManagementService clientManagementService;

  @Autowired
  private Supplier<ServerShardSettings> serverShardSettingsSupplier;

  @Autowired
  CountDownLatch closeLatch;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) throws InterruptedException {
    Objects.requireNonNull(event);

    final ServerShardSettings serverShardSettings = serverShardSettingsSupplier.get();
    clientManagementService
      .retrieveClient(serverShardSettings.networkSettings().host(), serverShardSettings.networkSettings().port())
      .switchIfEmpty(
        clientManagementService
          .addClient(serverShardSettings.networkSettings().host(), serverShardSettings.networkSettings().port())
      )
      .flatMap(TcpClient::connect)
      .retryWhen(RetrySpec.backoff(3L, Duration.ofSeconds(1)))
      .flatMap(this::handleHandshake)
      .subscribe();
  }

  /**
   * @deprecated Used only to validate TcpServer -- will be removed once FasterPay network is enabled.
   */
  @Deprecated
  public Mono<Void> handleHandshake(Connection connection) {
    AtomicReference<ClientHandshakeState> handshakeState = new AtomicReference<>(ClientHandshakeState.SEND_INIT_REQ);

    logger.info("Client is initiating custom handshake with payload: {} (HEX)",
      BaseEncoding.base16().encode(handshakeState.get().getPayload()));

    connection.outbound().sendByteArray(Mono.just(handshakeState.get().getPayload())).then().subscribe();

    return connection.inbound().receive()
      .asByteArray()
      .flatMap(bytes -> {
        logger.info("Client received HEX payload: {}", BaseEncoding.base16().encode(bytes));

        if (handshakeState.get().receivedPayloadMatchesExpected(bytes)) {

          if (handshakeState.get().getNextStateOrdinal() != null) {
            ClientHandshakeState nextState = ClientHandshakeState.values()[handshakeState.get().getNextStateOrdinal()];
            logger.info("Client handshake state will become: {}", nextState);
            handshakeState.set(nextState);

            logger.info("Client is sending new request payload: {} (HEX)",
              BaseEncoding.base16().encode(handshakeState.get().getPayload()));

            connection.outbound().sendByteArray(Mono.just(handshakeState.get().getPayload()))
              .then()
              .subscribe();
          } else {
            logger.info("Client has completed the custom handshake.");
          }

        } else {
          return Mono.error(new IllegalStateException("Data received is not valid!"));
        }

        return Mono.empty();
      })
      .onErrorResume(e -> {
        logger.error("Error occurred: {}", e.getMessage(), e);
        return Mono.empty();
      })
      .then();
  }
}
