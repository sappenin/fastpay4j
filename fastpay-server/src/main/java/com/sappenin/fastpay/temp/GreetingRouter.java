package com.sappenin.fastpay.temp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * @deprecated Exists only for example purposes. Delete once server processing is implemented and figured out.
 */
@Deprecated
@Configuration
//@Import( {NettyWebServerFactoryCustomizer.class})
public class GreetingRouter {

  @Bean
  public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
    return RouterFunctions
      .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
        greetingHandler::hello);
  }

//  @Bean
//  public DatagramServer<byte[], byte[]> datagramServer(Environment env) throws InterruptedException {
//
//    final DatagramServer<byte[], byte[]> server = new DatagramServerSpec<byte[], byte[]>(NettyDatagramServer.class)
//      .env(env)
//      .listen(SocketUtils.findAvailableTcpPort())
//      .codec(StandardCodecs.BYTE_ARRAY_CODEC)
//      .consumeInput(bytes -> log.info("received: " + new String(bytes)))
//      .get();
//
//    server.start().await();
//    return server;
//  }

}