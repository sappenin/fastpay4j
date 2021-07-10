package money.fluid.fastpay4j.tcp.server.config;

import money.fluid.fastpay4j.server.settings.ServerShardSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.netty.tcp.TcpServer;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @see "https://medium.com/@nikola.babic1/how-to-implement-a-custom-handshaking-protocol-via-tcp-using-reactor-netty-d8bcd2fe6ee7"
 */
@Component
@ConditionalOnProperty(prefix = "fastpay4j.server.authority", name = "network_protocol", havingValue = "Tcp")
public class TcpApplicationStartupEventListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private TcpServer tcpServer;

  @Autowired
  private Supplier<ServerShardSettings> serverShardSettingsSupplier;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Objects.requireNonNull(event);
    startTcpServer();
  }

  private void startTcpServer() {
    logger.info("Starting internal Tcp server (shard {})...", serverShardSettingsSupplier.get().authorityState().shardId());
    tcpServer.bindNow();
    logger.info("Internal Tcp server (shard {}) started", serverShardSettingsSupplier.get().authorityState().shardId());
  }

}
