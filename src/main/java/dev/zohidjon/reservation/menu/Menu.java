package dev.zohidjon.reservation.menu;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, MenuItem> items;

    public Menu() {
        this.items = new HashMap<>();
    }

    public void addMenuItem(String name, double price) {
        String mealKey = name.toLowerCase();
        if (!items.containsKey(mealKey)) {
            items.put(mealKey, new MenuItem(mealKey, price));
        } else {
            System.out.println("Item already exists in the menu.");
        }
    }

    public void updateMenuItem(String name, double newPrice) {
        String mealKey = name.toLowerCase();
        if (items.containsKey(mealKey)) {
            items.get(mealKey).setPrice(newPrice);
        } else {
            System.out.println("Item does not exist in the menu.");
        }
    }

    public void removeMenuItem(String name) {
        String mealKey = name.toLowerCase();
        if (items.containsKey(mealKey)) {
            items.remove(mealKey);
        } else {
            System.out.println("Item does not exist in the menu.");
        }
    }

    public MenuItem getMenuItem(String name) {
        String mealKey = name.toLowerCase();
        return items.get(mealKey);
    }

    public void printMenu() {
        if (items.isEmpty()) {
            System.out.println("The menu is currently empty.");
        } else {
            System.out.println("\n-------- Menu --------");
            for (MenuItem item : items.values()) {
                System.out.println(item);
            }
        }
    }
}