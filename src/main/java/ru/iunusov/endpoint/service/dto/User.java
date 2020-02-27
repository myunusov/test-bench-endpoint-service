package ru.iunusov.endpoint.service.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class User {

  private final String id;

  private final String name;

  @JsonCreator
  public User(
      @JsonProperty(value = "id", required = true) final String id,
      @JsonProperty(value = "name", required = true) final String name
  ) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
