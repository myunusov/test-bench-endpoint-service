package ru.iunusov.gateway.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.iunusov.gateway.adapter.BackendAdapter;

@Component
public class EndpointService {

  private final BackendAdapter backendAdapter;

  @Value("${instance.id}")
  private int instanceId;

  @Value("${secret}")
  private String secret;

  private final Counter counter;

  private final Timer timer;

  public EndpointService(final BackendAdapter adapter, final MeterRegistry registry) {
    this.backendAdapter = adapter;
    counter = Counter.builder("user.errors.count")
        .tag("request", "users")
        .description("The number of request of user service")
        .register(registry);
    timer = Timer.builder("user.request.latency")
        .description("The latency of request of user service")
        .tag("request", "users")
        .register(registry);
  }

  @SneakyThrows
  public String message() {
    if (Math.random() < .1) {
      counter.increment(1.0);
      throw new IllegalStateException("System Error");
    }
    final var result = timer.recordCallable(backendAdapter::getRequests);
    return String.format("Number of requests %s (gateway %d, secret %s)",
        result, instanceId, secret);
  }
}
