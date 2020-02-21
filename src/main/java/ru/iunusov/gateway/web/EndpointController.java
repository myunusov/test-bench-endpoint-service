package ru.iunusov.gateway.web;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.gateway.domain.User;
import ru.iunusov.gateway.service.EndpointService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointController {

  private final EndpointService service;

  @GetMapping("/users")
  public List<User> getUsers() {
    var users = service.users();
    log.info(users.stream().map(Objects::toString).collect(Collectors.joining(", ")));
    return users;
  }

}