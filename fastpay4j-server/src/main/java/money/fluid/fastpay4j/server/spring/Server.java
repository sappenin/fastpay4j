package money.fluid.fastpay4j.server.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * A Server-shard that runs on a particular port, with various properties. This class is used to be able to operate
 * multiple instances on different ports in the same JVM, which is especially useful for sharding.
 */
public abstract class Server implements ApplicationListener {

  private final Properties properties;
  protected SpringApplication application;
  private ConfigurableApplicationContext context;

  /**
   * Required-args constructor.
   *
   * @param configurations A configuration class.
   */
  public Server(final Class<?>... configurations) {
    Objects.requireNonNull(configurations);

    this.application = new SpringApplication(configurations);
    this.properties = new Properties();

    final ArrayList<ApplicationContextInitializer<?>> initializers = new ArrayList<>();

    initializers.add(context -> context.getEnvironment().getPropertySources()
      .addFirst(new PropertiesPropertySource("node", properties)));

    application.setInitializers(initializers);
    application.addListeners(this);
  }

  public void start() {
    context = application.run();
  }

  public void stop() {
    if (context != null) {
      context.close();
    }
  }

  public Server setProperty(final String key, final String value) {
    Objects.requireNonNull(key);
    this.properties.setProperty(key, value);
    return this;
  }

  public ConfigurableApplicationContext getContext() {
    return context;
  }

  public int getPort() {
    return Integer.valueOf(this.properties.getProperty("server.port"));
  }

  public void setPort(final int port) {
    this.setProperty("server.port", port + "");
  }

  public String getHost() {
    return this.properties.getProperty("server.host");
  }

  public void setHost(final String host) {
    Objects.requireNonNull(host);
    this.setProperty("server.host", host);
  }

//  /**
//   * Allows sub-classes to access the {@link ServerSettings}. Note that the {@code context} object will not be available
//   * until after {@link #start()} completes, so callers should be careful when calling this method.
//   */
//  protected Supplier<ServerSettings> getServerSettings() {
//    return (Supplier<ServerSettings>) context.getBean("serverSettingsSupplier");
//  }

//  public void setServerShardSettings(ServerShardSettings serverShardSettings) {
//    final Supplier<ServerShardSettings> serverSettingsSupplier = () -> serverShardSettings;
//    ((GenericApplicationContext) context).registerBean(
//      "serverShardSettingsSupplier", Supplier.class, serverSettingsSupplier
//    );
//  }
}
