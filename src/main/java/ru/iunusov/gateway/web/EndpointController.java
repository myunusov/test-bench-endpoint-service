package ru.iunusov.gateway.web;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.gateway.service.EndpointService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointController {

  private final EndpointService service;

  @GetMapping("/")
  public String getRequestsCount() {
    final String message = service.message();
    log.info(message);
    return message;
  }

}