package com.sappenin.fastpay;

import com.google.common.collect.Lists;
import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.core.AuthorityName;
import com.sappenin.fastpay.core.Committee;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.authority.AuthorityState;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.NetworkProtocol;
import com.sappenin.fastpay.server.settings.NetworkSettings;
import com.sappenin.fastpay.server.settings.ServerSettings;
import com.sappenin.fastpay.server.settings.ServerShardSettings;
import com.sappenin.fastpay.server.spring.FastPayServerShard;
import com.sappenin.fastpay.server.spring.config.ConverterConfig;
import com.sappenin.fastpay.server.spring.config.ServerSettingsFromPropertyFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * The main application for starting a FastPay4j server. This application reads properties from the environment and uses
 * them to create 1 or more FastPay4j server shards. Note that all configuration for the bootstrapper application is
 * contained in this class.
 */
@EnableConfigurationProperties( {ServerSettingsFromPropertyFile.class})
@Import( {
  ConverterConfig.class // <-- So the main app can import properties.
})
public class FastPay4jApplication implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(FastPay4jApplication.class);

  // All shards operated by this server.
  private final List<FastPayServerShard> fastPayServerShards = Lists.newArrayList();

  // Shutting down all shards happens in a single thread via a listener, so we only need to wait for 1 count in the
  // latch.
  private static CountDownLatch closeLatch = new CountDownLatch(1);

  /**
   * The main application initializer.
   *
   * @param args Command-line arguments (these are unused in-favor of environment properties).
   */
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext ctx = SpringApplication.run(FastPay4jApplication.class, args);

    // Command line runners don't consume reactive types and simply return after execution, so must block here.
    final CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      LOGGER.info("Terminating Fastpay4j...");
      closeLatch.countDown();
    }));
    closeLatch.await();
  }

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  Supplier<ServerSettings> serverSettingsSupplier;

  @Bean
  Supplier<ServerSettings> serverSettingsSupplier() {
    return () -> applicationContext.getBean(ServerSettings.class);
  }

  @Override
  public void run(String... args) throws Exception {
    final ServerSettings serverSettings = serverSettingsSupplier.get();
    String host = serverSettings.authority().host();
    int basePort = serverSettings.authority().basePort();
    FastPayAddress fastPayAddress = serverSettings.authority().fastPayAddress();
    NetworkProtocol networkProtocol = serverSettings.authority().networkProtocol();
    Ed25519PrivateKey serverKey = serverSettings.serverKey();
    TreeMap<AuthorityName, UnsignedLong> votingRights = serverSettings.votingRights();

    final int numShards = serverSettings.authority().numShards();

    for (int shardNum = 0; shardNum < numShards; shardNum++) {
      // Make a server shard.
      final ServerShardSettings serverShardSettings = ServerShardSettings.builder()
        .networkSettings(NetworkSettings.builder()
          .host(host)
          .port(basePort + shardNum)
          .fastPayAddress(fastPayAddress)
          .networkProtocol(networkProtocol)
          .build())
        .authorityState(
          AuthorityState.builder()
            .authorityName(AuthorityName.builder()
              .edPublicKey(fastPayAddress.edPublicKey())
              .build())
            .secretKey(serverKey)
            .committee(Committee.builder()
              .votingRights(votingRights)
              .build())
            .numShards(numShards)
            .shardId(shardNum)
            // TODO: Add AccountBalances, but only the address is not this actual shard.
            //.accounts()
            .build())
        .build();

      // TODO: Rename to just FastPayServer?
      FastPayServerShard shard = new FastPayServerShard(serverShardSettings);
      fastPayServerShards.add(shard);
      shard.start();
    }

    LOGGER.info("FastPay4j Authority Startup Complete (address={} protocol={} host={} base_port={} num_shards={}",
      serverSettings.authority().fastPayAddress(),
      networkProtocol,
      serverSettings.authority().host(),
      basePort,
      serverSettings.authority().numShards()
    );

    // Emit Meta-data.

    // Command line runners don't consume reactive types and simply return after execution, so must block here.
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      LOGGER.info("Terminating all Fastpay4j shards ({} in total)...", fastPayServerShards.size());
      fastPayServerShards.stream().forEach(shard -> {
        shard.stop();
      });
      closeLatch.countDown();
      LOGGER.info("All {} Fastpay4j shards terminated.", fastPayServerShards.size());
    }));
    closeLatch.await();
  }

//  @Deprecated
//  private final ClientManagementService clientManagementService;
//
//  public FastPay4jApplication(ClientManagementService clientManagementService) {
//    this.clientManagementService = clientManagementService;
//  }

//  @Autowired
//  private ApplicationContext applicationContext;
//
//  @Autowired
//  Supplier<ServerSettings> serverSettingsSupplier;
//
//  @Bean
//  Supplier<ServerSettings> serverSettingsSupplier() {
//    return () -> applicationContext.getBean(ServerSettings.class);
//  }

//  @Override
//  public void run(String... args) {
//
//    // TODO: Load the ServerSettings; (2) Create a ShardConfig; (3) Start a shard-server for each one (each shard is a spring application).
//
//    // TODO: Move to command-line? Or just load from env properties.
//    int numShards = 4;
//    final ServerSettings serverSettings = this.serverSettingsSupplier.get();
//    for (int shardNum = 0; shardNum < numShards; shardNum++) {
//      // Make a server shard.
//      final ServerShardSettings serverShardSettings = ServerShardSettings.builder()
//        .networkSettings(NetworkSettings.builder()
//          .host(serverSettings.authority().host())
//          .port(serverSettings.authority().basePort() + shardNum)
//          .fastPayAddress(serverSettings.authority().fastPayAddress())
//          .networkProtocol(serverSettings.authority().networkProtocol())
//          .build())
//        .authorityState(AuthorityState.builder()
//          .authorityName(AuthorityName.of(serverSettings.authority().fastPayAddress().edPublicKey()))
//          .secretKey(serverSettings.serverKey())
//          .committee(Committee.builder()
//            .votingRights(serverSettings.votingRights())
//            .build())
//          .numShards(serverSettings.authority().numShards())
//          .shardId(shardNum)
//          // TODO: Add AccountBalances, but only the address is not this actual shard.
//          //.accounts()
//          .build())
//        .build();
//
//      // TODO: Rename to just FastPayServer?
//      FastPayServerShard shard = new FastPayServerShard(serverShardSettings);
//      this.servers.add(shard);
//      shard.start();
//    }

//    LOGGER.info("EXECUTING : command line runner");
//    for (int i = 0; i < args.length; ++i) {
//      LOGGER.info("args[{}]: {}", i, args[i]);
//
//    }

  // final ServerSettings serverSettings = serverSettingsSupplier.get();
//    clientManagementService.retrieveClient(serverSettings.authority().host(), serverSettings.authority().basePort())
//      .switchIfEmpty(
//        clientManagementService.addClient(serverSettings.authority().host(), serverSettings.authority().basePort())
//      )
//      .flatMap(TcpClient::connect)
//      .retryWhen(RetrySpec.backoff(3L, Duration.ofSeconds(1)))
//      .flatMap(this::handleHandshake)
//      .subscribe();

  //    GreetingWebClient gwc = new GreetingWebClient();
//    System.out.println(gwc.getResult());
}

// Reactor has a UDP and TCP Stream implementation built-in that will use Netty.
// See https://objectpartners.com/2014/11/18/udp-server-with-spring-boot-and-reactor/ (old)
// See https://www.baeldung.com/spring-boot-reactor-netty
// See https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-ann-requestmapping-registration

// See here for a Netty-based TCP and UDP server: https://projectreactor.io/docs/netty/snapshot/reference/index.html#udp-server
// and here: https://medium.com/@nikola.babic1/how-to-implement-a-custom-handshaking-protocol-via-tcp-using-reactor-netty-d8bcd2fe6ee7
// https://violetagg.github.io/reactor-netty-workshop/

// Not reactive: https://github.com/ArchangelDesign/WhispersOfFreedom/blob/master/__server/src/main/java/com/whispersoffreedom/WOFApplication.java
// Maybe: https://www.programmersought.com/article/22466378480/

// For serialization, consider CBOR (https://github.com/FasterXML/jackson-dataformats-binary/tree/master/cbor)
// or perhaps another format? Adjusting fasterpay with CBOR might be as trivial as replacing bincode:: with cbor:: from Serde.
// Alt: MessagePack, which allows zero-copy operations (there's Java and Rust libraries available).
// DevMode should be JSON.


