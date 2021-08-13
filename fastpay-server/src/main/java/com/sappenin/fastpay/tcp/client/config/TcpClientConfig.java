package com.sappenin.fastpay.tcp.client.config;

import com.sappenin.fastpay.tcp.client.ClientManagementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * All configuration for the TCP server.
 */
@ComponentScan("com.sappenin.fastpay.tcp.client.config")
public class TcpClientConfig {

  @Bean
  ClientManagementService clientManagementService() {
    return new ClientManagementService();
  }

}
