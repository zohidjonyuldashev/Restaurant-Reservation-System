package dev.zohidjon.reservation.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MenuItem {
    private String name;
    @Setter
    private double price;

    @Override
    public String toString() {
        return name + ": $" + price;
    }
}