package com.sappenin.fastpay.temp;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @deprecated Exists only for example purposes. Delete once server processing is implemented and figured out.
 */
@Deprecated
@Component
public class GreetingHandler {

  public Mono<ServerResponse> hello(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
      .body(BodyInserters.fromValue("Hello, Spring!"));
  }

//  @GetMapping
//  private Flux<Employee> getAllEmployees() {
//    return employeeRepository.findAllEmployees();
//  }
}