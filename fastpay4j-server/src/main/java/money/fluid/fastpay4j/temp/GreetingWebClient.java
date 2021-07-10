package money.fluid.fastpay4j.temp;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @deprecated Exists only for example purposes. Delete once server processing is implemented and figured out.
 */
@Deprecated
public class GreetingWebClient {

  private WebClient client = WebClient.create("http://localhost:8080");

  private Mono<ClientResponse> result = client.get()
    .uri("/hello")
    .accept(MediaType.TEXT_PLAIN)
    .exchange();

  public String getResult() {
    return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
  }
}