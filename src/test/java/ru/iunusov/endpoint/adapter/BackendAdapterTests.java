package ru.iunusov.endpoint.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(BackendAdapter.class)
public class BackendAdapterTests {

  @Autowired
  private MockRestServiceServer server;

  @Autowired
  private BackendAdapter backendAdapter;

  @Value("${backend.url}")
  private String backendUrl;

  @Test
  public void getUsers_okResponse() {
    server
        .expect(requestTo(backendUrl + "/users"))
        .andRespond(
            withSuccess(
                "[\n"
                    + "  {\n"
                    + "    \"id\": \"id\", \n"
                    + "    \"name\": \"name\"\n"
                    + "  }\n"
                    + "]\n",
                MediaType.APPLICATION_JSON));
    final var users = backendAdapter.users();
    assertThat(users.size()).isEqualTo(1);
    assertThat(users.get(0).getId()).isEqualTo("id");
    assertThat(users.get(0).getName()).isEqualTo("name");
  }
}