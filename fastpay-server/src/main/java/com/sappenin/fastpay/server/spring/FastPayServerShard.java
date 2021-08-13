package com.sappenin.fastpay.server.spring;

import com.sappenin.fastpay.server.settings.ServerShardSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;
import java.util.Optional;

/**
 * An extension of {@link Server} that implements a FastPay server. Each server runs on a unique port and has its own
 * Spring ApplicationContext.
 */
public class FastPayServerShard extends Server {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  // Allows a Server to use an overridden ServerSettings (so we don't have to load it from a properties file).
  // TODO: Make non-optional?
  private final Optional<ServerShardSettings> serverShardSettingsOverride;

//  public FastPayServerShard() {
//    super(FastPayServerShardConfig.class);
//    this.serverShardSettingsOverride = Optional.empty();
//  }

  public FastPayServerShard(final ServerShardSettings serverShardSettings) {
    super(FastPayServerShardConfig.class);
    this.serverShardSettingsOverride = Optional.of(serverShardSettings);
    this.setProperty(SpringApplication.BANNER_LOCATION_PROPERTY, "shard-banner.txt");
    // Set the server port for the underlying server.
//    setHost(serverShardSettings.networkSettings().host());
//    setPort(serverShardSettings.networkSettings().port());
  }

  @Override
  public void start() {
    super.start();

//    // ...only now is everything wired-up.
//    if (SpringProfileUtils.isProfileActive(getContext().getEnvironment(), "MIGRATE-ONLY")) {
//      System.out.println("###################################################################");
//      System.out.println("!!! Container started with migrate-only profile. Shutting down. !!!");
//      System.out.println("###################################################################");
//      this.stop();
//      return;
//    }
  }

  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(final ApplicationEvent event) {
    Objects.requireNonNull(event);
    if (event instanceof ApplicationPreparedEvent) {
      // If there is a ServerSettingsOverride, then add it to the ApplicationContext. The ServerShardConfig is smart
      // enough to detect it and use it instead.
      this.serverShardSettingsOverride
        .ifPresent(cso -> ((ApplicationPreparedEvent) event).getApplicationContext().getBeanFactory()
          .registerSingleton(ServerShardSettings.OVERRIDE_BEAN_NAME, cso));
    } else if (event instanceof ApplicationStartedEvent) {
      logger.info("Started FastPay4j Shard {} ({} total) on port={}",
        serverShardSettingsOverride.get().authorityState().shardId(),
        serverShardSettingsOverride.get().authorityState().numShards(),
        serverShardSettingsOverride.get().networkSettings().port(),
        serverShardSettingsOverride.get().authorityState().authorityName()
      );
    }
  }
}
