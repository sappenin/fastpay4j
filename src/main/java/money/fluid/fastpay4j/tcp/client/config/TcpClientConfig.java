package money.fluid.fastpay4j.tcp.client.config;

import money.fluid.fastpay4j.tcp.client.ClientManagementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * All configuration for the TCP server.
 */
@ComponentScan("money.fluid.fastpay4j.tcp.client.config")
public class TcpClientConfig {

  @Bean
  ClientManagementService clientManagementService() {
    return new ClientManagementService();
  }

}
