package ru.iunusov.gateway.adapter;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iunusov.gateway.domain.User;

@Service
public class BackendAdapter {

  private static final String REQUESTS_ENDPOINT = "/users";

  private final RestTemplate restTemplate;

  @Value("${backend.url}")
  private String backendUrl;

  public BackendAdapter(@NotNull final RestTemplateBuilder builder) {
    restTemplate = builder.build();
  }

  public List<User> users() {
    final ResponseEntity<List<User>> rateResponse =
        restTemplate.exchange(backendUrl + REQUESTS_ENDPOINT,
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
    return rateResponse.getBody();
  }
}