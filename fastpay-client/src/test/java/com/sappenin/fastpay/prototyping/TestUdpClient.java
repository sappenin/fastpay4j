package com.sappenin.fastpay.prototyping;

import io.netty.channel.ChannelOption;
import com.sappenin.fastpay.core.FastPayAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpClient;

import java.util.Objects;

public class TestUdpClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUdpClient.class);

  private final String host = "0.0.0.0";
  private final int basePort = 9101;
  private final int shardNum = 0;

  public TestUdpClient() {

  }

  public String getBalance(final FastPayAddress fastPayAddress) {
    Objects.requireNonNull(fastPayAddress);
    final UdpClient udpClient = UdpClient.create()
      .host(host)
      .port(basePort + shardNum)
      .option(
        ChannelOption.CONNECT_TIMEOUT_MILLIS,
        2000 // 2s
      )
      .option(
        ChannelOption.SO_TIMEOUT,
        2000
      )
      .wiretap(true);
    //.doOnConnected(conn -> conn.addHandler(new LineBasedFrameDecoder(8192)))

    // Write data...
    Mono<? extends Connection> connectionMono = udpClient
      .handle((udpInbound, udpOutbound) -> {
        return udpOutbound.sendString(Mono.just("hello"));
      }).connect();

    Connection result = connectionMono.block();

    return "";
//      .handle((udpInbound, udpOutbound) -> {
//
//        udpInbound.receive()
//          .subscribe((Consumer<? super ByteBuf>) Mono.create((response) -> {
//            LOGGER.info(response.toString());
//          }));
//
//        //new MonoSubscriber();
//        NettyOutbound response = udpOutbound
//          .sendString(Mono.just("hello"));

    //ByteBuf bytes = new By NettyOutbound response = udpOutbound.send(bytes)
    ////          .then($ -> udpInbound.receive());
    ////        udpInbound.receive();
//
//      });

    // Listen for responses from the server.
//    connection.subscribe(response -> {
//      LOGGER.info(response.toString());
//    });

//    connection.handle((udpInbound, udpOutbound) -> {

    // udpOutbound.
//      udpInbound.receive()
//          .subscribe((Consumer<? super ByteBuf>) Mono.create((response) -> {
//            LOGGER.info(response.toString());
//          }));

//    });
    // Block so that this test doesn't end.
    //connection.block(Duration.ofSeconds(30));

  }
}
