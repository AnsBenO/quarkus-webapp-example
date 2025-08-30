package com.ansbeno;

import java.util.List;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Path("/todolist")
@Produces(MediaType.TEXT_HTML)
public class TodoListResource {

      private final TodoService todoService;

      @CheckedTemplate
      public static class Templates {
            public static native TemplateInstance index(List<TodoItem> items);

            public static native TemplateInstance todoItem(TodoItem item);

            public static native TemplateInstance todoForm();

            public static native TemplateInstance todoItemForm(TodoItem item);

            public static native TemplateInstance confirmDelete(TodoItem item);

      }

      @TemplateExtension
      public class TemplateExtensions {
            public static String completeCount(List<TodoItem> items) {
                  long completeCount = items.stream().filter(TodoItem::isCompleted).count();
                  long totalCount = items.size();
                  return completeCount + " of " + totalCount + " completed";
            }

      }

      // View the entire list
      @GET
      @Path("/")
      public TemplateInstance index() {
            return Templates.index(todoService.getTodoItems());
      }

      // View a single list item
      @GET
      @Path("item/{id}")
      public TemplateInstance getTodoItem(@PathParam("id") String id) {
            return Templates.todoItem(todoService.getTodoItem(id));
      }

      // Form to add a new list item
      @GET
      @Path("item/{id}/edit")
      public TemplateInstance showEditTodoItem(@PathParam("id") String id) {
            return Templates.todoItemForm(todoService.getTodoItem(id));
      }

      // Update a list item and return the updated list
      @PATCH
      @Path("item/{id}/edit")
      public TemplateInstance updateTodoItem(TodoItem updatedItem) {
            todoService.updateTodoItem(updatedItem);
            return index();
      }

      // Crete a new list item form
      @GET
      @Path("item/new")
      public TemplateInstance newTodoItem() {
            return Templates.todoForm();
      }

      // Add a new list item and return the updated list
      @POST
      @Path("item/new")
      public TemplateInstance addTodoItem(TodoItem item) {
            todoService.addTodoItem(item);
            return index();
      }

      // Delete a list item and return the updated list
      @DELETE
      @Path("item/{id}")
      public TemplateInstance deleteTodoItem(@PathParam("id") String id) {
            todoService.removeTodoItem(id);
            return index();
      }

}
