package dev.zohidjon.reservation.reservation;

import dev.zohidjon.reservation.additional.Restaurant;
import dev.zohidjon.reservation.additional.Table;
import dev.zohidjon.reservation.menu.Menu;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
public class Staff {
    private Menu menu;
    private ReservationManager reservationManager;
    private List<Table> tables;
    private Restaurant restaurant;

    public void addMenuItem(String name, double price) {
        menu.addMenuItem(name, price);
    }

    public void updateMenuItem(String name, double newPrice) {
        menu.updateMenuItem(name, newPrice);
    }

    public void removeMenuItem(String name) {
        menu.removeMenuItem(name);
    }

    public void viewMenu() {
        menu.printMenu();
    }

    public String createReservation(String customerName, LocalDate date, LocalTime time, int numberOfGuests) {
        return reservationManager.createReservation(customerName, date, time, numberOfGuests, menu);
    }

    public void updateReservation(String reservationId, LocalDate newDate, LocalTime newTime, int newNumberOfGuests) {
        reservationManager.updateReservation(reservationId, newDate, newTime, newNumberOfGuests);
    }

    public void cancelReservation(String reservationId) {
        reservationManager.cancelReservation(reservationId);
    }

    public void viewAllReservations() {
        reservationManager.viewAllReservations();
    }

    public Reservation getReservationDetails(String reservationId) {
        return reservationManager.getReservation(reservationId);
    }

    // Table Management
    public void addTable(int tableNumber, int numberOfSeats) {
        tables.add(new Table(tableNumber, numberOfSeats));
    }

    public void updateTable(int tableNumber, int newNumberOfSeats) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber) {
                table.setNumberOfSeats(newNumberOfSeats);
                break;
            }
        }
    }

    public void removeTable(int tableNumber) {
        tables.removeIf(table -> table.getTableNumber() == tableNumber);
    }

    public void viewAllTables() {
        for (Table table : tables) {
            System.out.println(table);
        }
    }

    // Restaurant Information
    public void viewRestaurantDetails() {
        System.out.println(restaurant);
    }
}
