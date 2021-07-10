package money.fluid.fastpay4j.server.spring.config;

import money.fluid.fastpay4j.server.settings.ServerShardSettings;
import money.fluid.fastpay4j.tcp.client.config.TcpClientConfig;
import money.fluid.fastpay4j.tcp.server.config.TcpServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Objects;
import java.util.function.Supplier;

@Configuration
@Import( {
  ConverterConfig.class,
  TcpServerConfig.class,
  TcpClientConfig.class
})
public class SpringServerConfig implements ApplicationListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ApplicationContext applicationContext;

  //  @Autowired
//  Supplier<ServerSettings> serverSettingsSupplier;

//  @Bean
//  Supplier<ServerSettings> serverSettingsSupplier() {
//    return () -> applicationContext.getBean(ServerSettings.class);
//  }

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
//    if (event instanceof ApplicationPreparedEvent) {
//      logger.info("ServerSettings: {}", this.serverSettingsSupplier.get());
//    }
  }

  /**
   * <p>This is a supplier that can be given to beans for later usage after the application has started. This
   * supplier will not resolve to anything until the `ServerShardSettings` bean has been loaded into the
   * application-context, which could occur via the EnableConfigurationProperties annotation on this class, but that
   * does not currently happen.</p>
   */
  @Bean
  Supplier<ServerShardSettings> serverShardSettingsSupplier() {
    //try {
    final Object overrideBean = applicationContext.getBean(ServerShardSettings.OVERRIDE_BEAN_NAME);
    return () -> (ServerShardSettings) overrideBean;
//    } catch (Exception e) {
//      logger.debug("No ServerShardSettings Override Bean found....");
//    }
//
//    // No override was detected, so return the normal variant that exists because of the EnableConfigurationProperties
//    // directive above.
//    return () -> applicationContext.getBean(ServerShardSettings.class);
  }


}
