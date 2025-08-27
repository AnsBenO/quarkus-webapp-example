package com.ansbeno;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Path("/hello")
public class HelloResource {

      @CheckedTemplate
      public static class Templates {
            public static native TemplateInstance hello(String name);
      }

      @GET
      @Produces(MediaType.TEXT_HTML)
      public TemplateInstance get(@QueryParam("name") String name) {
            return Templates.hello(name);
      }

}
