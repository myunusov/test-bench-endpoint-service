package ru.iunusov.gateway.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Entity {

  @JsonCreator
  public User(
      @JsonProperty(value = "id", required = true) final String id,
      @JsonProperty(value = "name", required = true) final String name
  ) {
    super(id, name);
  }

}
