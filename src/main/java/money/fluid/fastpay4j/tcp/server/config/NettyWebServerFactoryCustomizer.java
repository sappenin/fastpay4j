package money.fluid.fastpay4j.tcp.server.config;

//@Configuration
public class NettyWebServerFactoryCustomizer {//implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

//  @Override
//  public void customize(NettyReactiveWebServerFactory serverFactory) {
//    //serverFactory.setPort(8088);
//    serverFactory.addServerCustomizers(new PortCustomizer(8080));
//  }
//
//  /**
//   * Since Spring Boot auto-configures the Netty server, we need to skip auto-configuration by explicitly defining a
//   * NettyReactiveWebServerFactory bean.
//   *
//   * @return A {@link NettyReactiveWebServerFactory}.
//   */
//  @Bean
//  public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
//    NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
//    webServerFactory.addServerCustomizers(new EventLoopNettyCustomizer());
//    return webServerFactory;
//  }
//
//  private static class PortCustomizer implements NettyServerCustomizer {
//
//    private final int port;
//
//    private PortCustomizer(int port) {
//      this.port = port;
//    }
//
//    @Override
//    public HttpServer apply(HttpServer httpServer) {
//      return httpServer.port(port);
//    }
//  }
//
//  private static class EventLoopNettyCustomizer implements NettyServerCustomizer {
//
//    @Override
//    public HttpServer apply(HttpServer httpServer) {
//      EventLoopGroup parentGroup = new NioEventLoopGroup();
//      EventLoopGroup childGroup = new NioEventLoopGroup();
//      return httpServer;
////        .runOn(parentGroup)
////        .runOn(childGroup)
////        .ch
////        .tcpConfiguration(tcpServer -> tcpServer
////        .bootstrap(serverBootstrap -> serverBootstrap
////          .group(parentGroup, childGroup)
////          .channel(NioServerSocketChannel.class)));
//    }
//  }


}
