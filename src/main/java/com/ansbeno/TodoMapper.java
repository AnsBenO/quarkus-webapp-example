package com.ansbeno;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TodoMapper {

      public TodoDto toDto(TodoEntity entity) {
            return new TodoDto(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getDescription(),
                        entity.isCompleted()
            );
      }

      public TodoEntity toEntity(TodoDto dto) {
            return new TodoEntity(
                        dto.getId(),
                        dto.getTitle(),
                        dto.getDescription(),
                        dto.isCompleted()
            );
      }
}
