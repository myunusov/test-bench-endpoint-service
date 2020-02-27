package ru.iunusov.endpoint.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import ru.iunusov.endpoint.service.EndpointService;
import ru.iunusov.endpoint.service.dto.User;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EndpointController.class)
public class EndpointControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private EndpointService service;

  @Test
  public void getRequest_okResponse() throws Exception {
    when(service.users()).thenReturn(singletonList(new User("id", "name")));

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is("id")))
        .andExpect(jsonPath("$[0].name", is("name")));
  }

  @Test
  public void getRequest_failedResponse() throws Exception {
    when(service.users()).thenThrow(RestClientException.class);
    mockMvc.perform(get("/users")).andExpect(status().is5xxServerError());
  }
}