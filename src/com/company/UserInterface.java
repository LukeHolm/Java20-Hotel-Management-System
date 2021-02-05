package com.company;

import java.io.IOException;

public class UserInterface {
    private UserInputHandler userInput = new UserInputHandler();

    public int adminOrCustomerChoice() {
        System.out.println("Welcome to the Hotel Booking System");
        System.out.println("Please enter if you are a admin or a customer");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("0. Exit Program");

        return userInput.getIntFromUser(0, 2);
    }

    public int adminChoice() {
        System.out.println("1. Register a new customer");
        System.out.println("2. Search for a customer");
        System.out.println("3. Delete customer");               //TODO: vill vi ha en upgrade också? till vad?
        System.out.println("4. Booking or upgrading room");
        System.out.println("5. Ordering food for specific room");
        System.out.println("6. Customer check out and showing bill");
        System.out.println("0. Exit admin view");

        return userInput.getIntFromUser(0,6);
    }

    public int customerChoice() {
        System.out.println("1. Display room details");
        System.out.println("2. Display room availability");
        System.out.println("3. Book");
        System.out.println("4. Order food");
        System.out.println("5. Checkout");
        System.out.println("0. Exit customer view");

        return userInput.getIntFromUser(0,5);
    }

    public int foodChoice() throws IOException {
        Food.listOfFood().forEach(System.out::println);
        System.out.println("0. Go back");

        return userInput.getIntFromUser(0,4);
    }

    public int roomChoice() {
        System.out.println("1. Twin bed room");
        System.out.println("2. Single bed room");
        System.out.println("3. Deluxe twin bed room");
        System.out.println("4. Deluxe single bed room");
        System.out.println("0. Exit");

        return userInput.getIntFromUser(0,4);
    }

    public boolean confirm() {
        System.out.println("Would you like to book this room? (Y/N)");
        String ans = userInput.getStringFromUser();

        return ans.equalsIgnoreCase("y");
    }


    public String enterValue(String value) {
        System.out.println("Please enter " + value);

        return userInput.getStringFromUser();
    }

    public int enterInteger(String value) {
        System.out.println("Please enter " + value);

        return userInput.getIntFromUser(1,100);
    }

    public static String PadRight(String string) {
        int totalStringLength = 40;
        int charsToPadd = totalStringLength - string.length();

        if (string.length() >= totalStringLength)
            return string;

        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < charsToPadd; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }


}
