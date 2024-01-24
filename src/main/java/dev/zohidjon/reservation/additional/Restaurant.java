package dev.zohidjon.reservation.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Restaurant {
    private String name;
    private String address;
    private String phoneNumber;
    private String website;
    private String openingHours;
}
