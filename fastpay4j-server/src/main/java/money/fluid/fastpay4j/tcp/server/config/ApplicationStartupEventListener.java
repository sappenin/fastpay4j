package money.fluid.fastpay4j.tcp.server.config;

import money.fluid.fastpay4j.server.settings.ServerSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.netty.tcp.TcpServer;

import java.util.function.Supplier;

/**
 * @see "https://medium.com/@nikola.babic1/how-to-implement-a-custom-handshaking-protocol-via-tcp-using-reactor-netty-d8bcd2fe6ee7"
 */
@Component
public class ApplicationStartupEventListener {

  @Autowired
  private Supplier<ServerSettings> serverSettings;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final TcpServer tcpServer;

  public ApplicationStartupEventListener(TcpServer tcpServer) {
    this.tcpServer = tcpServer;
  }

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    startTcpServer(); // TODO: Toggle based on CLI input?
  }

  private void startTcpServer() {
    // TODO: Depending on the number of shards specified via the command-line, construct that many TCP servers.

    logger.info("Starting TCP Server on port 7654 ...");
    tcpServer.bindNow();
  }

}
