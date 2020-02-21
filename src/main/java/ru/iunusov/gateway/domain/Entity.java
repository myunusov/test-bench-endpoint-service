package ru.iunusov.gateway.domain;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
@ToString
public abstract class Entity {
  private final String id;
  private final String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity entity = (Entity) o;
    return id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


}
