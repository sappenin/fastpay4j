package money.fluid.fastpay4j.fp4jparent;

import com.google.common.io.BaseEncoding;
import money.fluid.fastpay4j.tcp.client.ClientHandshakeState;
import money.fluid.fastpay4j.tcp.client.ClientManagementService;
import money.fluid.fastpay4j.tcp.client.config.TcpClientConfig;
import money.fluid.fastpay4j.tcp.server.config.TcpServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.RetrySpec;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
@Import( {
  TcpClientConfig.class,
  TcpServerConfig.class
})
public class Fp4jParentApplication implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ClientManagementService clientManagementService;

  public Fp4jParentApplication(ClientManagementService clientManagementService) {
    this.clientManagementService = clientManagementService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Fp4jParentApplication.class, args);

//    GreetingWebClient gwc = new GreetingWebClient();
//    System.out.println(gwc.getResult());
  }


  @Override
  public void run(String... args) {
    clientManagementService.retrieveClient("localhost")
      .switchIfEmpty(clientManagementService.addClient("localhost"))
      .flatMap(TcpClient::connect)
      .retryWhen(RetrySpec.backoff(3L, Duration.ofSeconds(1)))
      .flatMap(this::handleHandshake)
      .subscribe();
  }

  // Reactor has a UDP and TCP Stream implementation built-in that will use Netty.
  // See https://objectpartners.com/2014/11/18/udp-server-with-spring-boot-and-reactor/ (old)
  // See https://www.baeldung.com/spring-boot-reactor-netty
  // See https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-ann-requestmapping-registration

  // See here for a Netty-based TCP and UDP server: https://projectreactor.io/docs/netty/snapshot/reference/index.html#udp-server
  // and here: https://medium.com/@nikola.babic1/how-to-implement-a-custom-handshaking-protocol-via-tcp-using-reactor-netty-d8bcd2fe6ee7
  // https://violetagg.github.io/reactor-netty-workshop/

  // Not reactive: https://github.com/ArchangelDesign/WhispersOfFreedom/blob/master/__server/src/main/java/com/whispersoffreedom/WOFApplication.java
  // Maybe: https://www.programmersought.com/article/22466378480/

  // For serialization, consider CBOR (https://github.com/FasterXML/jackson-dataformats-binary/tree/master/cbor)
  // or perhaps another format? Adjusting fasterpay with CBOR might be as trivial as replacing bincode:: with cbor:: from Serde.
  // Alt: MessagePack, which allows zero-copy operations (there's Java and Rust libraries available).
  // DevMode should be JSON.

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


