package com.ansbeno;

import java.util.List;
import java.util.UUID;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Path("/todolist")
@Produces(MediaType.TEXT_HTML)
public class TodoListResource {

      private final TodoService todoService;

      @CheckedTemplate(basePath = "todolist")
      public static class Templates {
            // View the main to-do list page
            public static native TemplateInstance index(List<TodoItem> items);

            // View a single item's details
            public static native TemplateInstance viewItemModal(TodoItem item);

            // Form to add a new item
            public static native TemplateInstance createItemFormModal();

            // Form to edit an existing item
            public static native TemplateInstance editItemFormModal(TodoItem item);

            // View the entire list
            public static native TemplateInstance listItems(List<TodoItem> items);
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
      public TemplateInstance index() {
            List<TodoItem> items = todoService.getTodoItems();
            return Templates.index(items);
      }

      // View a single list item
      @GET
      @Path("item/{id}")
      public TemplateInstance getTodoItem(@PathParam("id") String id) {
            return Templates.viewItemModal(todoService.getTodoItem(id));
      }

      // Form to edit a list item
      @GET
      @Path("item/{id}/edit")
      public TemplateInstance showEditTodoItem(@PathParam("id") String id) {
            return Templates.editItemFormModal(todoService.getTodoItem(id));
      }

      // Update a list item and return the updated list
      @PATCH
      @Path("item/{id}/edit")
      @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
      public TemplateInstance updateTodoItem(TodoItem updatedItem) {
            log.info("Updated item: {}", updatedItem);
            todoService.updateTodoItem(updatedItem);
            List<TodoItem> items = todoService.getTodoItems();
            return Templates.listItems(items);
      }

      // Create a new list item form
      @GET
      @Path("item/new")
      public TemplateInstance newTodoItem() {
            return Templates.createItemFormModal();
      }

      // Add a new list item and return the updated list
      @POST
      @Path("item/new")
      @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
      public TemplateInstance addTodoItem(TodoItem item) {
            item.setId(UUID.randomUUID().toString());
            todoService.addTodoItem(item);
            List<TodoItem> items = todoService.getTodoItems();
            return Templates.listItems(items);
      }

      // Delete a list item and return the updated list
      @DELETE
      @Path("item/{id}")
      public TemplateInstance deleteTodoItem(@PathParam("id") String id) {
            todoService.removeTodoItem(id);
            List<TodoItem> items = todoService.getTodoItems();
            return Templates.listItems(items);
      }

}
