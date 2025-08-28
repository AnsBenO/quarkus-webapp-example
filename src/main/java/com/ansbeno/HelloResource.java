package com.ansbeno;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Path("/hello")
public class HelloResource {

      private final ItemService itemService;

      @CheckedTemplate
      public static class Templates {
            public static native TemplateInstance hello(String name);

            public static native TemplateInstance item(Item item);
      }

      @GET
      @Produces(MediaType.TEXT_HTML)
      public TemplateInstance getHello(@QueryParam("name") String name) {
            return Templates.hello(name);
      }

      @GET
      @Path("item/{id}")
      @Produces(MediaType.TEXT_HTML)
      public TemplateInstance getItem(@PathParam("id") String id) {
            return Templates.item(this.itemService.getItem(id));
      }

}
