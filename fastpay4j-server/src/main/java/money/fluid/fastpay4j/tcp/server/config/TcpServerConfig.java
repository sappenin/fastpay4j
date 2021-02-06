package money.fluid.fastpay4j.tcp.server.config;

import money.fluid.fastpay4j.server.settings.ServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import reactor.netty.tcp.TcpServer;

import java.util.function.Supplier;

/**
 * All configuration for the TCP server.
 */
@ComponentScan("money.fluid.fastpay4j.tcp.server.config")
public class TcpServerConfig {

  @Autowired
  private HandshakeHandler handshakeHandler;

  @Autowired
  private Supplier<ServerSettings> serverSettingsSupplier;

  @Bean
  @Lazy
  public HandshakeHandler handshakeHandler() {
    return new HandshakeHandler();
  }

  @Bean
  public TcpServer tcpServer() {
    final ServerSettings serverSettings = serverSettingsSupplier.get();
    TcpServer server = TcpServer.create()
      .host(serverSettings.authority().host())
      .port(serverSettings.authority().basePort())
      .wiretap(true)
      // The handler function is where the communication flow is managed. It has two input parameters
      // and has to return an empty publisher.
      .handle(handshakeHandler.handleInbound());

    return server;
  }
}
