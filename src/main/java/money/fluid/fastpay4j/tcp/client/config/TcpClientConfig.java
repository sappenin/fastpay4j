package money.fluid.fastpay4j.tcp.client.config;

import money.fluid.fastpay4j.tcp.client.ClientManagementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All configuration for the TCP server.
 */
@Configuration
public class TcpClientConfig {

  @Bean
  ClientManagementService clientManagementService() {
    return new ClientManagementService();
  }

}
