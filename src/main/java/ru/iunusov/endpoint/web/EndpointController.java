package ru.iunusov.endpoint.web;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iunusov.endpoint.service.EndpointService;
import ru.iunusov.endpoint.service.dto.User;

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

  @GetMapping("/projects")
  public List<Object> getProjects() {
    throw new IllegalStateException("It's not implemented it");
  }

}