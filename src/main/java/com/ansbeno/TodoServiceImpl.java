package com.ansbeno;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Transactional
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

      private final TodoMapper mapper;

      public List<TodoDto> getTodoDtos() {
            List<TodoEntity> entities = TodoEntity.listAll();
            return entities.stream()
                        .map(mapper::toDto)
                        .toList();
      }

      public TodoDto getTodoDto(String id) {
            TodoEntity entity = TodoEntity.findById(id);
            return entity != null ? mapper.toDto(entity) : null;
      }

      public void addTodoDto(TodoDto item) {
            TodoEntity entity = mapper.toEntity(item);
            entity.persist();
      }

      public String removeTodoDto(String id) {
            TodoEntity entity = TodoEntity.findById(id);
            if (entity != null) {
                  String title = entity.getTitle();
                  entity.delete();
                  return title;
            }
            return "";
      }

      public void updateTodoDto(TodoDto updatedItem) {
            TodoEntity entity = TodoEntity.findById(updatedItem.getId());
            if (entity != null) {
                  entity.setTitle(updatedItem.getTitle());
                  entity.setDescription(updatedItem.getDescription());
                  entity.setCompleted(updatedItem.isCompleted());
            }
      }

}
