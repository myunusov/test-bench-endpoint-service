package ru.iunusov.endpoint.web;

import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
@Slf4j
public class ErrorAdvice {

  @ExceptionHandler(RestClientException.class)
  public ResponseEntity<String> onServiceUnavailable() {
    final var message = "Internal server error: external service is not available";
    log.error(message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> internalServerError(@NotNull final RuntimeException e) {
    log.error("Internal server error", e);
    return new ResponseEntity<>("Internal server error: " + e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}