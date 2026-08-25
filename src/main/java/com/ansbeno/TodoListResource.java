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


@Slf4j
@Path("/todolist")
@Produces(MediaType.TEXT_HTML)
@RequiredArgsConstructor
public class TodoListResource {

      private final TodoService todoService;

      @CheckedTemplate(basePath = "todolist")
      public static class Templates {
            // View the main to-do list page
            public static native TemplateInstance index(List<TodoDto> items, ToastNotification toast);

            // View a single item's details
            public static native TemplateInstance viewItemModal(TodoDto item);

            // Form to add a new item
            public static native TemplateInstance createItemFormModal();

            // Form to edit an existing item
            public static native TemplateInstance editItemFormModal(TodoDto item);

            // View the entire list
            public static native TemplateInstance listItems(List<TodoDto> items, ToastNotification toast);
      }

      @TemplateExtension
      public class TemplateExtensions {
            public static String completeCount(List<TodoDto> items) {
                  long completeCount = items.stream().filter(TodoDto::isCompleted).count();
                  long totalCount = items.size();
                  return completeCount + " of " + totalCount + " completed";
            }

      }

      // View the entire list
      @GET
      public TemplateInstance index() {
            List<TodoDto> items = todoService.getTodoDtos();
            return Templates.index(items, null);
      }

      // View a single list item
      @GET
      @Path("item/{id}")
      public TemplateInstance showTodoDto(@PathParam("id") String id) {
            return Templates.viewItemModal(todoService.getTodoDto(id));
      }

      // Form to edit a list item
      @GET
      @Path("item/{id}/edit")
      public TemplateInstance showEditTodoDto(@PathParam("id") String id) {
            return Templates.editItemFormModal(todoService.getTodoDto(id));
      }

      // Update a list item and return the updated list
      @PATCH
      @Path("item/{id}/edit")
      @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
      public TemplateInstance updateTodoDto(TodoDto updatedItem) {
            log.info("Updated item: {}", updatedItem);
            todoService.updateTodoDto(updatedItem);
            List<TodoDto> items = todoService.getTodoDtos();
            var toast = new ToastNotification("Item updated", "Item " + updatedItem.getTitle() + " has been updated",
                        ToastNotification.Type.SUCCESS);
            return Templates.listItems(items, toast);
      }

      // Create a new list item form
      @GET
      @Path("item/new")
      public TemplateInstance newTodoDto() {
            return Templates.createItemFormModal();
      }

      // Add a new list item and return the updated list
      @POST
      @Path("item/new")
      @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
      public TemplateInstance addTodoDto(TodoDto item) {
            item.setId(UUID.randomUUID().toString());
            todoService.addTodoDto(item);
            List<TodoDto> items = todoService.getTodoDtos();
            var toast = new ToastNotification("Item added", "Item " + item.getTitle() + " has been added",
                        ToastNotification.Type.SUCCESS);
            return Templates.listItems(items, toast);
      }

      // Delete a list item and return the updated list
      @DELETE
      @Path("item/{id}")
      public TemplateInstance deleteTodoDto(@PathParam("id") String id) {
            String title = todoService.removeTodoDto(id);
            List<TodoDto> items = todoService.getTodoDtos();
            var toast = new ToastNotification("Item deleted", "Item " + title + " has been deleted",
                        ToastNotification.Type.SUCCESS);
            return Templates.listItems(items, toast);
      }

}
