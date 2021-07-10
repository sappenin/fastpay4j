package money.fluid.fastpay4j.tcp.server.config;

import money.fluid.fastpay4j.server.settings.ServerShardSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import reactor.netty.tcp.TcpServer;

import java.util.function.Supplier;

/**
 * All configuration for the TCP server.
 */
@ComponentScan("money.fluid.fastpay4j.tcp.server.config")
@ConditionalOnProperty(prefix = "fastpay4j.server.authority", name = "network_protocol", havingValue = "Tcp")
public class TcpServerConfig {

  @Autowired
  private HandshakeHandler handshakeHandler;

  @Autowired
  private Environment environment;

  @Autowired
  private Supplier<ServerShardSettings> serverShardSettingsSupplier;

  @Bean
  @Lazy
  public HandshakeHandler handshakeHandler() {
    return new HandshakeHandler();
  }

  @Bean
  public TcpServer tcpServer() {
    TcpServer server = TcpServer.create()
      .host(serverShardSettingsSupplier.get().networkSettings().host())
      .port(serverShardSettingsSupplier.get().networkSettings().port())
//      .host(environment.getProperty("server.host"))
//      .port(environment.getProperty("server.port", Integer.class))
      .wiretap(true)
      // The handler function is where the communication flow is managed. It has two input parameters
      // and has to return an empty publisher.
      .handle(handshakeHandler.handleInbound());

    return server;
  }
}
