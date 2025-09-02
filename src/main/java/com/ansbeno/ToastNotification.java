package com.ansbeno;

import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Notification class for showing toasts
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToastNotification {

      // Notification types
      public enum Type {
            SUCCESS("success"),
            ERROR("error"),
            WARNING("warning"),
            INFO("info"),
            QUESTION("question");

            private final String value;

            Type(String value) {
                  this.value = value;
            }

            public String getValue() {
                  return value;
            }
      }

      private String title;
      private String message;
      private Type type;
}
