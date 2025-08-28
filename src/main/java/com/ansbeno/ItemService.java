package com.ansbeno;

import java.math.BigDecimal;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemService {

      private Item[] items;

      @PostConstruct
      public void init() {
            Item item1 = new Item("1", "Item 1", new BigDecimal("10.00"));
            Item item2 = new Item("2", "Item 2", new BigDecimal("20.00"));
            Item item3 = new Item("3", "Item 3", new BigDecimal("30.00"));

            items = new Item[] { item1, item2, item3 };
      }

      public Item[] getItems() {
            return items;
      }

      public void setItems(Item[] items) {
            this.items = items;
      }

      public Item getItem(String id) {
            for (Item item : items) {
                  if (item.getId().equals(id)) {
                        return item;
                  }
            }
            return null;
      }
}
