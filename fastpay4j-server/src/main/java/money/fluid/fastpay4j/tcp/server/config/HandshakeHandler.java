package money.fluid.fastpay4j.tcp.server.config;

import com.google.common.io.BaseEncoding;
import money.fluid.fastpay4j.tcp.server.ServerHandshakeState;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class HandshakeHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public BiFunction<NettyInbound, NettyOutbound, Publisher<Void>> handleInbound() {

    AtomicReference<ServerHandshakeState> handshakeState = new AtomicReference<>(ServerHandshakeState.RCV_INIT_REQ);

    return (nettyInbound, nettyOutbound) -> nettyInbound.receive()
      .asByteArray()
      .flatMap(bytes -> {
        logger.info("Server received HEX payload: {}", BaseEncoding.base16().encode(bytes));

        if (handshakeState.get().receivedPayloadMatchesExpected(bytes)) {

          logger.info(
            "Server responding with HEX payload: {} ...",
            BaseEncoding.base16().encode(handshakeState.get().getResponsePayload())
          );
          nettyOutbound.sendByteArray(Mono.just(handshakeState.get().getResponsePayload()))
            .then()
            .subscribe();

          if (handshakeState.get().getNextStateOrdinal() != null) { // there are more states
            ServerHandshakeState nextState = ServerHandshakeState.values()[handshakeState.get().getNextStateOrdinal()];

            logger.info("Server handshake state will become: {}", nextState);
            handshakeState.set(nextState);
          } else {
            logger.info("Server has completed the custom handshake.");
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