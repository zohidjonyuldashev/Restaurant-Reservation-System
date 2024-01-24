package dev.zohidjon.reservation.reservation;

import dev.zohidjon.reservation.menu.Menu;
import dev.zohidjon.reservation.menu.MenuItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Reservation {
    private String customerName;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private int numberOfGuests;
    private Menu menu;
    private Map<String, Integer> mealSelections;

    public Reservation(String customerName, LocalDate reservationDate, LocalTime reservationTime, int numberOfGuests, Menu menu) {
        this.customerName = customerName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.menu = menu;
        this.mealSelections = new HashMap<>();
    }

    public void addMeal(String mealName, int quantity) {
        String mealKey = mealName.toLowerCase();
        MenuItem item = menu.getMenuItem(mealKey);
        if (item != null && quantity > 0) {
            mealSelections.put(mealName, mealSelections.getOrDefault(mealName, 0) + quantity);
        } else {
            System.out.println("Meal not found in menu or invalid quantity.");
        }
    }

    public void updateMeal(String oldMealName, String newMealName) {
        removeMeal(oldMealName);
        addMeal(newMealName, 1);
    }

    public void removeMeal(String mealName) {
        String mealKey = mealName.toLowerCase();
        if (mealSelections.containsKey(mealKey)) {
            mealSelections.remove(mealName);
        } else {
            System.out.println("Meal not found in reservation.");
        }
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<String, Integer> entry : mealSelections.entrySet()) {
            MenuItem item = menu.getMenuItem(entry.getKey());
            if (item != null) {
                totalCost += item.getPrice() * entry.getValue();
            }
        }
        return totalCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation Details:\n");
        sb.append("Customer Name: ").append(customerName).append("\n");
        sb.append("Date: ").append(reservationDate).append("\n");
        sb.append("Time: ").append(reservationTime).append("\n");
        sb.append("Number of Guests: ").append(numberOfGuests).append("\n");
        sb.append("Meal Selections:\n");
        for (Map.Entry<String, Integer> entry : mealSelections.entrySet()) {
            sb.append(entry.getKey()).append(" x ").append(entry.getValue()).append("\n");
        }
        sb.append("Total Cost: $").append(String.format("%.2f", calculateTotalCost()));
        return sb.toString();
    }
}