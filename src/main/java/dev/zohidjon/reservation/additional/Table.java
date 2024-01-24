package dev.zohidjon.reservation.additional;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class Table {
    private int tableNumber;
    private int numberOfSeats;
    private boolean isAvailable;

    public Table(int tableNumber, int numberOfSeats) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.isAvailable = true; // Initially, all tables are available.
    }
}
