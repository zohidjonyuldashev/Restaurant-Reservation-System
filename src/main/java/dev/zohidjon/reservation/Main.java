package dev.zohidjon.reservation;

import dev.zohidjon.reservation.additional.Restaurant;
import dev.zohidjon.reservation.additional.Table;
import dev.zohidjon.reservation.menu.Menu;
import dev.zohidjon.reservation.reservation.Reservation;
import dev.zohidjon.reservation.reservation.ReservationManager;
import dev.zohidjon.reservation.reservation.Staff;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = new Menu();
        ReservationManager reservationManager = new ReservationManager();
        Restaurant restaurant = new Restaurant("Golden chicken", "Yangi Sergeli street, 12", "(78) 777-77-47", "https://university.pdp.uz", "9 AM - 11 PM");
        List<Table> tables = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            tables.add(new Table(i, 4));
        }
        Staff staff = new Staff(menu, reservationManager, tables, restaurant);

        boolean next = true;
        while (next) {
            System.out.println("1. Staff\n2. Customer\n3. Exit");
            System.out.print("Choose your role: ");
            int userType = scanner.nextInt();
            scanner.nextLine();

            switch (userType) {
                case 1 -> handleStaffInterface(staff, reservationManager);
                case 2 -> handleCustomerInterface(staff, reservationManager);
                case 3 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void handleCustomerInterface(Staff staff, ReservationManager reservationManager) {
        boolean next = true;
        while (next) {
            System.out.println("\n======== Customer Interface ========");
            System.out.println("1. Make a Reservation\n2. Manage Existing Reservation\n3. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> makeReservation(staff, reservationManager);
                case 2 -> manageReservation(reservationManager);
                case 3 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void makeReservation(Staff staff, ReservationManager reservationManager) {
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        LocalDate date = null;
        LocalTime time = null;
        while (date == null || time == null) {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter time (HH:MM): ");
                time = LocalTime.parse(scanner.nextLine());

                if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
                    System.out.println("Reservation date and time must be in the future.");
                    date = null;
                } else if (!reservationManager.isReservationAvailable(date, time)) {
                    System.out.println("This time slot is already booked. Please choose a different time.");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date or time. Please try again.");
                date = null;
            }
        }

        System.out.print("Enter number of guests: ");
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine();

        if (reservationManager.isReservationAvailable(date, time)) {
            String reservationId = staff.createReservation(customerName, date, time, numberOfGuests);
            System.out.println("Reservation created successfully. Reservation ID: " + reservationId);
            handleMealSelection(staff, reservationId);
        } else {
            System.out.println("This time slot is already booked. Please choose a different time.");
        }
    }

    private static void handleMealSelection(Staff staff, String reservationId) {
        Reservation reservation = staff.getReservationDetails(reservationId);
        boolean done = false;

        while (!done) {
            System.out.println("\n-------- Meal Selection --------");
            System.out.println("1. Add Meal\n2. Update Meal\n3. Remove Meal\n4. View Order\n5. Total Cost\n6. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    staff.viewMenu();
                    System.out.print("Enter meal name: ");
                    String mealName = scanner.nextLine();
                    System.out.print("Enter quantity of meal: ");
                    int quantity = scanner.nextInt();
                    reservation.addMeal(mealName, quantity);
                }
                case 2 -> {
                    System.out.print("Enter old meal name: ");
                    String oldMealName = scanner.nextLine();
                    System.out.print("Enter new meal name: ");
                    String newMealName = scanner.nextLine();
                    reservation.updateMeal(oldMealName, newMealName);
                }
                case 3 -> {
                    System.out.print("Enter meal name to remove: ");
                    String mealName = scanner.nextLine();
                    reservation.removeMeal(mealName);
                }
                case 4 -> System.out.println(reservation);
                case 5 -> System.out.println("Total Cost: $" + reservation.calculateTotalCost());
                case 6 -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageReservation(ReservationManager reservationManager) {
        System.out.print("Enter your reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = reservationManager.getReservation(reservationId);
        if (reservation != null) {
            boolean done = false;
            while (!done) {
                System.out.println("-------- Reservation Management --------");
                System.out.println("""
                        1. cancel reservation
                        2. update reservation
                        3. view details
                        4. back""");

                System.out.print("Enter an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        reservationManager.cancelReservation(reservationId);
                        System.out.println("Reservation is cancelled");
                        done = true;
                    }
                    case 2 -> {
                        LocalDate date = null;
                        LocalTime time = null;
                        while (date == null || time == null) {
                            try {
                                System.out.print("Enter date (YYYY-MM-DD): ");
                                date = LocalDate.parse(scanner.nextLine());
                                System.out.print("Enter time (HH:MM): ");
                                time = LocalTime.parse(scanner.nextLine());

                                if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
                                    System.out.println("Reservation date and time must be in the future.");
                                    date = null;
                                } else if (!reservationManager.isReservationAvailable(date, time)) {
                                    System.out.println("This time slot is already booked. Please choose a different time.");
                                    date = null;
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date or time. Please try again.");
                                date = null;
                            }
                        }

                        System.out.print("Enter number of guests: ");
                        int numberOfGuests = scanner.nextInt();
                        scanner.nextLine();
                        reservationManager.updateReservation(reservationId, date, time, numberOfGuests);
                    }
                    case 3 -> reservationManager.viewAllReservations();
                    case 4 -> done = true;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private static void handleStaffInterface(Staff staff, ReservationManager reservationManager) {
        boolean next = true;
        while (next) {
            System.out.println("\n======== Staff Interface: ========");
            System.out.println("1. Menu Management\n2. Reservation Management\n3. Table Management\n4. Restaurant Information\n5. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> menuManagement(staff);
                case 2 -> reservationManagement(staff, reservationManager);
                case 3 -> tableManagement(staff);
                case 4 -> restaurantManagement(staff);
                case 5 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void menuManagement(Staff staff) {
        boolean next = true;
        while (next) {
            System.out.println("\n------- Menu Management -------");
            System.out.println("""
                    1. add menu
                    2. update menu
                    3. remove menu
                    4. view menu
                    5. back""");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter meal name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter meal price: ");
                    double price = scanner.nextDouble();
                    staff.addMenuItem(name, price);
                }
                case 2 -> {
                    System.out.print("Enter meal name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter meal price: ");
                    double newPrice = scanner.nextDouble();
                    staff.updateMenuItem(newName, newPrice);
                }
                case 3 -> {
                    System.out.print("Enter meal name: ");
                    String removedMeal = scanner.nextLine();
                    staff.removeMenuItem(removedMeal);
                }
                case 4 -> staff.viewMenu();
                case 5 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void reservationManagement(Staff staff, ReservationManager reservationManager) {
        boolean next = true;
        while (next) {
            System.out.println("\n-------- Reservation Management --------");
            System.out.println("""
                    1. create reservation
                    2. update reservation
                    3. cancel reservation
                    4. view all reservation
                    5. get reservation details
                    6. back""");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> makeReservation(staff, reservationManager);
                case 2 -> {
                    System.out.print("Enter reservation id: ");
                    String reservationId = scanner.nextLine();
                    LocalDate date = null;
                    LocalTime time = null;
                    while (date == null || time == null) {
                        try {
                            System.out.print("Enter date (YYYY-MM-DD): ");
                            date = LocalDate.parse(scanner.nextLine());
                            System.out.print("Enter time (HH:MM): ");
                            time = LocalTime.parse(scanner.nextLine());

                            if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
                                System.out.println("Reservation date and time must be in the future.");
                                date = null;
                            } else if (!reservationManager.isReservationAvailable(date, time)) {
                                System.out.println("This time slot is already booked. Please choose a different time.");
                                date = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date or time. Please try again.");
                            date = null;
                        }
                    }

                    System.out.print("Enter number of guests: ");
                    int numberOfGuests = scanner.nextInt();
                    scanner.nextLine();
                    staff.updateReservation(reservationId, date, time, numberOfGuests);
                }
                case 3 -> {
                    System.out.println("Enter reservation id: ");
                    String reservationId = scanner.nextLine();
                    staff.cancelReservation(reservationId);
                    System.out.println("User in id: " + reservationId + ": cancelled");
                }
                case 4 -> staff.viewAllReservations();
                case 5 -> {
                    System.out.print("Enter reservation id: ");
                    String reservationId = scanner.nextLine();
                    Reservation reservationDetails = staff.getReservationDetails(reservationId);
                    System.out.println(reservationDetails);
                }
                case 6 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void tableManagement(Staff staff) {
        boolean next = true;
        while (next) {
            System.out.println("\n-------- Table Management --------");
            System.out.println("""
                    1. add table
                    2. update table
                    3. remove table
                    4. view all tables
                    5. back""");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter table number: ");
                    int tableNum = scanner.nextInt();
                    System.out.print("Enter number of seats: ");
                    int seatsCount = scanner.nextInt();
                    staff.addTable(tableNum, seatsCount);
                }
                case 2 -> {
                    System.out.print("Enter new table number: ");
                    int newTableNum = scanner.nextInt();
                    System.out.print("Enter number of seats: ");
                    int seatsCount = scanner.nextInt();
                    staff.updateTable(newTableNum, seatsCount);
                }
                case 3 -> {
                    System.out.print("Enter table number: ");
                    int tableNum = scanner.nextInt();
                    staff.removeTable(tableNum);
                }
                case 4 -> staff.viewAllTables();
                case 5 -> next = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void restaurantManagement(Staff staff) {
        System.out.println("\nInformation about our Restaurant");
        staff.viewRestaurantDetails();
    }
}
