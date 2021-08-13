package com.sappenin.fastpay.prototyping;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.resources.LoopResources;
import reactor.netty.udp.UdpClient;
import reactor.netty.udp.UdpServer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @see "https://projectreactor.io/docs/netty/release/reference/index.html"
 * @see "https://github.com/reactor/reactor-netty/blob/ff2b71d07f87cda45a4df23bca35418c8bf5e45e/reactor-netty-core/src/test/java/reactor/netty/udp/UdpClientTest.java"
 */
public class TestUdpClientTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUdpClientTest.class);
  private static final String HOST = "0.0.0.0";
  private static final int PORT = 9101;

  private TestUdpClient testUdpClient;

  @BeforeEach
  public void setUp() {
    //testUdpClient = new TestUdpClient();

    //Connection serverConnection = this.startServer();
    //   Connection clientConnection = this.startClient();

//    clientConnection.outbound().sendString(Mono.just("Hello2")).then().block();
//    String result = clientConnection.inbound().receive().asString().blockFirst();
//
//    serverConnection.inbound().receive().then().block();

    // Keep the server running.
    //serverConnection.onDispose().block();

//    serverConnection.dispose();
//    clientConnection.dispose();

    //clientConnection.
//    serverConnection.onDispose().block();
//    clientConnection.onDispose().block();

    //serverConnection.onDispose().block();
  }

//  private Connection startClient() {
//    return UdpClient.create()
//      .host(HOST)
//      .port(PORT)
//      .wiretap(true)
//      .handle((udpInbound, udpOutbound) -> {
//
////        // Handle outgoing
////        udpOutbound.sendString(Mono.just("hello back"));
//
//        // Handle incoming
//        return udpInbound.receive()
//          // TODO: Do stuff whenever you receive something...
//          .then();
//      })
//      .connectNow(Duration.ofSeconds(30));
//  }

  private Connection startServer() {
    return UdpServer.create()
      .host(HOST)
      .port(PORT)
      .wiretap(true)
      // Consume Data
      .handle((in, out) ->
        out.sendObject(
          in.receiveObject()
            .map(o -> {
              if (o instanceof DatagramPacket) {
                DatagramPacket p = (DatagramPacket) o;

                // See https://stackoverflow.com/questions/40289204/how-to-convert-a-netty-bytebuf-to-a-string-and-vice-versa
                if (p.content().toString(StandardCharsets.UTF_8).equals("Hello2")) {
//                  return Mono.just("Hello back");
                  final ByteBuf bytes = Unpooled.wrappedBuffer("Hello back" .getBytes(StandardCharsets.UTF_8));
                  return new DatagramPacket(bytes, p.sender());
                } else {
//                  return new DatagramPacket(p.content().retain(), p.sender());
                  return new DatagramPacket(Unpooled.wrappedBuffer("echo" .getBytes(StandardCharsets.UTF_8)),
                    p.sender());
                }

              } else {
                return Mono.error(new Exception("Unexpected type of the message: " + o));
              }
            })))
      .bindNow(Duration.ofSeconds(30));

  }

  @Test
  public void testGetBalance() throws InterruptedException {
    startServer();
    assertThat(getResponsePoor().block()).isEqualTo("echo");
  }

  @Test
  public void smokeTest() throws Exception {
//    assertThat(this.getResponse().block()).isEqualTo("echo");
    getResponsePoor();
  }


  private Mono<String> getResponsePoor() throws InterruptedException {
    LoopResources resources = LoopResources.create("test");
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<String> result = new AtomicReference<>();

    Connection client1 =
      UdpClient.create()
        .host(HOST)
        .port(PORT)
        .runOn(resources)
        .handle((in, out) -> {

          in.receiveObject()
            .subscribe(response -> {
              if (response instanceof DatagramPacket) {
                DatagramPacket p = (DatagramPacket) response;
                // Capture the result as a String.
                result.set(p.content().toString(StandardCharsets.UTF_8));
                latch.countDown();
              } else {
                throw new RuntimeException("");
              }
            });

          return out.sendString(Mono.just("ping1"))
            .neverComplete();
        })
        .wiretap(true)
        .connect()
        .block(Duration.ofSeconds(30));

    assertThat(client1).isNotNull();

    assertTrue(latch.await(30, TimeUnit.SECONDS));
    client1.disposeNow();

    return Mono.just(result.get());
  }

  private Mono<String> getResponseMono() throws InterruptedException {
    LoopResources resources = LoopResources.create("test");
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<String> result = new AtomicReference<>();

    Connection client1 =
      UdpClient.create()
        .host(HOST)
        .port(PORT)
        .runOn(resources)
        .handle((in, out) -> {

          in.receiveObject()
            .subscribe(response -> {
              if (response instanceof DatagramPacket) {
                DatagramPacket p = (DatagramPacket) response;
                // Capture the result as a String.
                result.set(p.content().toString(StandardCharsets.UTF_8));
                latch.countDown();
              } else {
                throw new RuntimeException("");
              }
            });

          return out.sendString(Mono.just("ping1"))
            .neverComplete();
        })
        .wiretap(true)
        .connect()
        .block(Duration.ofSeconds(30));

    assertThat(client1).isNotNull();

    assertTrue(latch.await(30, TimeUnit.SECONDS));
    client1.disposeNow();

    return Mono.just(result.get());
  }

//  @Test
//  public void smokeTest2() throws Exception {
//    LoopResources resources = LoopResources.create("test");
//    CountDownLatch latch = new CountDownLatch(4);
//    Connection server =
//      UdpServer.create()
//        .port(0)
//        .runOn(resources)
//        .handle((in, out) -> in.receiveObject()
//          .map(o -> {
//            if (o instanceof DatagramPacket) {
//              DatagramPacket received = (DatagramPacket) o;
//              ByteBuf buffer = received.content();
//              System.out.println(
//                "Server received " + buffer.readCharSequence(buffer.readableBytes(), CharsetUtil.UTF_8));
//              ByteBuf buf1 = Unpooled.copiedBuffer("echo ", CharsetUtil.UTF_8);
//              ByteBuf buf2 = Unpooled.copiedBuffer(buf1, buffer);
//              buf1.release();
//              return new DatagramPacket(buf2, received.sender());
//            } else {
//              return Mono.error(new Exception());
//            }
//          })
//          .flatMap(out::sendObject))
//        .wiretap(true)
//        .bind()
//        .block(Duration.ofSeconds(30));
//    assertThat(server).isNotNull();
//
//    InetSocketAddress address = (InetSocketAddress) server.address();
//    Connection client1 =
//      UdpClient.create()
//        .port(address.getPort())
//        .runOn(resources)
//        .handle((in, out) -> {
//          in.receive()
//            .subscribe(b -> latch.countDown());
//          return out.sendString(Mono.just("ping1"))
//            .then(out.sendString(Mono.just("ping2")))
//            .neverComplete();
//        })
//        .wiretap(true)
//        .connect()
//        .block(Duration.ofSeconds(30));
//    assertThat(client1).isNotNull();
//
//    Connection client2 =
//      UdpClient.create()
//        .port(address.getPort())
//        .runOn(resources)
//        .handle((in, out) -> {
//          in.receive()
//            .subscribe(b -> latch.countDown());
//          return out.sendString(Mono.just("ping3"))
//            .then(out.sendString(Mono.just("ping4")))
//            .neverComplete();
//        })
//        .wiretap(true)
//        .connect()
//        .block(Duration.ofSeconds(30));
//    assertThat(client2).isNotNull();
//
//    assertTrue(latch.await(30, TimeUnit.SECONDS));
//    server.disposeNow();
//    client1.disposeNow();
//    client2.disposeNow();
//  }
}
