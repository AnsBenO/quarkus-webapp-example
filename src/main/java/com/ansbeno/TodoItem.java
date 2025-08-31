package com.ansbeno;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoItem {
      @FormParam("id")
      private String id;
      @FormParam("title")
      private String title;
      @FormParam("description")
      private String description;
      @FormParam("completed")
      private boolean completed;
}
