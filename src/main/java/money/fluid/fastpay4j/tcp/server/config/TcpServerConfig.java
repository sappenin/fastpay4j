package money.fluid.fastpay4j.tcp.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import reactor.netty.tcp.TcpServer;

/**
 * All configuration for the TCP server.
 */
@ComponentScan("money.fluid.fastpay4j.tcp.server.config")
public class TcpServerConfig {

  @Autowired
  private HandshakeHandler handshakeHandler;

  @Bean
  @Lazy
  public HandshakeHandler handshakeHandler() {
    return new HandshakeHandler();
  }

  @Bean
  public TcpServer tcpServer() {
    TcpServer server = TcpServer.create()
      .port(7654) // TODO: Make port configurable
      .wiretap(true)
      // The handler function is where the communication flow is managed. It has two input parameters
      // and has to return an empty publisher.
      .handle(handshakeHandler.handleInbound());

    return server;
  }
}
