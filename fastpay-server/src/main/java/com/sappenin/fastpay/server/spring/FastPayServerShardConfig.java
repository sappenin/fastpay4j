package com.sappenin.fastpay.server.spring;

import com.sappenin.fastpay.server.spring.config.SpringServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CountDownLatch;

/**
 * Initial Config for an ILP Connector Server.
 */
// A convenience annotation that adds all of the following:
// @Configuration, @EnableAutoConfiguration, @EnableWebMvc,and @ComponentScan
@SpringBootApplication(
  // exclude = ErrorMvcAutoConfiguration.class  // Excluded for `problems` support
)
@Import( {SpringServerConfig.class})
public class FastPayServerShardConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(FastPayServerShardConfig.class);

  /**
   * This server has no intrinsic server, so it needs to block so Spring doesn't terminate the process.
   *
   * @return A {@link CountDownLatch}.
   */
  @Bean
  public CountDownLatch closeLatch() {
    return new CountDownLatch(1);
  }

//  @Autowired
//  private ApplicationContext applicationContext;

//  @Autowired
//  private Supplier<ServerShardSettings> serverShardSettingsSupplier;

//  @Bean
//  Supplier<ServerShardSettings> serverShardSettingsSupplier() {
//    return () -> applicationContext.getBean(ServerShardSettings.class);
//  }

//  /**
//   * Accessor for the port that this server is running on.
//   */
//  public int getPort() {
//    return tcpServer
//      .map($-> $.configuration().bindAddress().get())
//      .orElseGet(udpServer.get().port())
//  }


}
