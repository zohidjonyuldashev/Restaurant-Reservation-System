package dev.zohidjon.reservation.reservation;

import dev.zohidjon.reservation.menu.Menu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReservationManager {
    private Map<String, Reservation> reservations;
    private UUID uuid;

    public ReservationManager() {
        this.reservations = new HashMap<>();
        this.uuid = UUID.randomUUID();
    }

    public String createReservation(String customerName, LocalDate date, LocalTime time, int numberOfGuests, Menu menu) {
        String reservationId = UUID.randomUUID().toString();
        Reservation newReservation = new Reservation(customerName, date, time, numberOfGuests, menu);
        reservations.put(reservationId, newReservation);
        return reservationId;
    }

    public void updateReservation(String reservationId, LocalDate newDate, LocalTime newTime, int newNumberOfGuests) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            reservation.setReservationDate(newDate);
            reservation.setReservationTime(newTime);
            reservation.setNumberOfGuests(newNumberOfGuests);
        } else {
            System.out.println("Reservation not found: " + reservationId);
        }
    }

    public void cancelReservation(String reservationId) {
        if (reservations.containsKey(reservationId)) {
            reservations.remove(reservationId);
        } else {
            System.out.println("Reservation not found: " + reservationId);
        }
    }

    public boolean isReservationAvailable(LocalDate date, LocalTime time) {
        return reservations.values().stream()
                .noneMatch(reservation -> reservation.getReservationDate().isEqual(date) && reservation.getReservationTime().equals(time));
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
        } else {
            for (Map.Entry<String, Reservation> entry : reservations.entrySet()) {
                System.out.println("Reservation ID: " + entry.getKey());
                System.out.println(entry.getValue());
                System.out.println();
            }
        }
    }

}
