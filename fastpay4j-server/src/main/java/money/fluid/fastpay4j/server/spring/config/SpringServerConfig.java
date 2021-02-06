package money.fluid.fastpay4j.server.spring.config;

import money.fluid.fastpay4j.server.settings.ServerSettings;
import money.fluid.fastpay4j.tcp.client.config.TcpClientConfig;
import money.fluid.fastpay4j.tcp.server.config.TcpServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Objects;
import java.util.function.Supplier;

@Configuration
@EnableConfigurationProperties( {ServerSettingsFromPropertyFile.class})
@Import( {
  ConverterConfig.class,
  TcpClientConfig.class,
  TcpServerConfig.class
})
public class SpringServerConfig implements ApplicationListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

//  @Autowired
//  private ApplicationContext applicationContext;
//
  @Autowired
  Supplier<ServerSettings> serverSettingsSupplier;

//  @Bean
//  Supplier<ServerSettings> serverSettingsSupplier() {
//    return () -> applicationContext.getBean(ServerSettings.class);
//  }

  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(final ApplicationEvent event) {
    Objects.requireNonNull(event);
    if (event instanceof ApplicationPreparedEvent) {
      logger.info("ServerSettings: {}", this.serverSettingsSupplier.get());
    }
  }


}
