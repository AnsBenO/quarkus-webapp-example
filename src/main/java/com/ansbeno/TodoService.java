package com.ansbeno;

import java.util.List;

public interface TodoService {

      public List<TodoDto> getTodoDtos();

      public TodoDto getTodoDto(String id);

      public void addTodoDto(TodoDto item);

      public String removeTodoDto(String id);

      public void updateTodoDto(TodoDto updatedItem);

}
