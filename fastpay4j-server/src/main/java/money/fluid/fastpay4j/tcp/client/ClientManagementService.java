package money.fluid.fastpay4j.tcp.client;

import io.netty.channel.ChannelOption;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManagementService {

  private static Map<String, TcpClient> ipToTcpClientMap = new ConcurrentHashMap<>();

  public Mono<TcpClient> addClient(String host, int port) {
    TcpClient client = TcpClient.create()
      .host(host)
      .port(port)
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
      .wiretap(true);

    ipToTcpClientMap.put(host + ":" + port, client);
    return Mono.just(client);
  }

  public Mono<TcpClient> retrieveClient(String host, int port) {
    TcpClient client = ipToTcpClientMap.get(host + ":" + port);

    if (client == null) {
      return Mono.empty();
    }

    return Mono.just(client);
  }

}
