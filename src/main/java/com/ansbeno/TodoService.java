package com.ansbeno;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TodoService {

      private ArrayList<TodoItem> todoItems;

      @PostConstruct
      public void init() {
            todoItems = new ArrayList<>(List.of(
                        new TodoItem("1", "Task 1", "Description for Task 1", false),
                        new TodoItem("2", "Task 2", "Description for Task 2", true),
                        new TodoItem("3", "Task 3", "Description for Task 3", false)));
      }

      public List<TodoItem> getTodoItems() {
            return todoItems;
      }

      public void setTodoItems(List<TodoItem> todoItems) {
            this.todoItems = todoItems.stream()
                        .collect(Collectors.toCollection(ArrayList::new));
      }

      public TodoItem getTodoItem(String id) {
            return todoItems.stream()
                        .filter(item -> item.getId().equals(id))
                        .findFirst()
                        .orElse(null);
      }

      public void addTodoItem(TodoItem item) {
            todoItems.add(item);
      }

      public void removeTodoItem(String id) {
            todoItems.removeIf(item -> item.getId().equals(id));
      }

      public void updateTodoItem(TodoItem updatedItem) {
            todoItems = todoItems.stream()
                        .map(item -> item.getId().equals(updatedItem.getId()) ? updatedItem : item)
                        .collect(Collectors.toCollection(ArrayList::new));
      }

}
