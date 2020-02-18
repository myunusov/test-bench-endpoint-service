package ru.iunusov.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.iunusov.gateway.adapter.BackendAdapter;

@Component
@RequiredArgsConstructor
public class EndpointService {

  private final BackendAdapter backendAdapter;

  @Value("${instance.id}")
  private int instanceId;

  @Value("${secret}")
  private String secret;

  public String message() {
    if (Math.random() < .1) {
      throw new IllegalStateException("System Error");
    }
    return String.format("Number of requests %s (gateway %d, secret %s)",
        backendAdapter.getRequests(), instanceId, secret);
  }
}
