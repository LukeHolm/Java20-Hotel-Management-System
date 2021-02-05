package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Management {
    private static final UserInterface USER_INTERFACE = new UserInterface();
    private static Statement sqlStatement;

    public void newCustomer() throws SQLException, IOException, ClassNotFoundException {
        sqlStatement = Run.getSqlStatement();
        String firstName = USER_INTERFACE.enterValue("customer first name");
        String lastName = USER_INTERFACE.enterValue("customer last name");
        String fullName = firstName + " " + lastName;
        String contactNumber = USER_INTERFACE.enterValue("customer contact number");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("INSERT INTO customer(Customer_name, contact_number)\n" +
                "VALUES( ? , ? );");
        statement.setString(1, fullName);
        statement.setString(2, contactNumber);

        statement.executeUpdate();

        int customerID = ResultClass.getCustomerId(sqlStatement, fullName);

        List<Integer> customerBill = new ArrayList<>();

        HotelData.writeToFile(customerBill, customerID + "_bill.txt");
    }

    public void searchCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterInteger("customer ID");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ? ");
        statement.setInt(1, customerId);

        statement.executeQuery();

        ResultClass.setResult(sqlStatement, "customer", customerId);

        listAllFromTable();
    }

    public void manageCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterInteger("customer ID");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("DELETE FROM customer WHERE id = ?");
        statement.setInt(1, customerId);

        statement.executeUpdate();

        System.out.println("Customer with Id :" + customerId + " deleted.");
        System.out.println();
    }

    public void foodOrder() throws SQLException {
//        sqlStatement = Run.getSqlStatement();
//        System.out.println("Please choose something from the menu");
//        System.out.println();
//        int foodChoice = USER_INTERFACE.foodChoice();
//
//        if (!(foodChoice == 0)) {
//            int customerID = USER_INTERFACE.enterInteger("customer id");
//
//            PreparedStatement statement = sqlStatement.getConnection().prepareStatement("INSERT INTO foodOrder (food_Id, customer_id)\n" +
//                    "VALUES ( ?, ? )");
//            statement.setInt(1, foodChoice);
//            statement.setInt(2, customerID);
//
//            statement.executeUpdate();
//
//            ResultClass.setFoodResult(sqlStatement, "foodorder", customerID);
//            System.out.println("Total food orders for customer:");
//            List<Integer> customerFoodOrder = ResultClass.getCustomerId(sqlStatement, customerID);
//            customerFoodOrder.forEach(System.out::println);
//            System.out.println();
//        }
    }

    public void checkOutWithBill() {

        //har beställt 1, 2, 2
        // bill
        //if 1 bill += 60
        //if 2 bill += 110

    }

    public int roomDetails() throws IOException, ClassNotFoundException {
        sqlStatement = Run.getSqlStatement();

        int roomChoice = USER_INTERFACE.roomChoice();

        List<Room> rooms = HotelData.readFromFile("Rooms.txt");
        System.out.println();
        rooms.stream().filter(s -> s.typeOfRoom == roomChoice)
                .map(s -> s.roomName).forEach(System.out::println);
        System.out.println("-------------------------------");
        rooms.stream().filter(s -> s.typeOfRoom == roomChoice)
                .map(s -> s.roomDescription).forEach(System.out::println);
        System.out.println("-------------------------------");
        System.out.print("Price: ");
        rooms.stream().filter(s -> s.typeOfRoom == roomChoice)
                .map(s -> s.price).forEach(System.out::print);
        System.out.println("KR per night");
        System.out.println();

        return roomChoice;
    }

    public void roomAvailability() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        ResultClass.setAvailableRoomsResult(sqlStatement, "availablerooms");
        listAllFromTable();
        System.out.println();
    }

    public void bookRoom() throws SQLException, IOException, ClassNotFoundException {
        sqlStatement = Run.getSqlStatement();

        int roomChoice = roomDetails();

        if (USER_INTERFACE.confirm()) {

            int customerID = USER_INTERFACE.enterInteger("customer id");

            int numberOfNights = USER_INTERFACE.enterInteger("number of nights to book");

            PreparedStatement statement = sqlStatement.getConnection().prepareStatement("UPDATE roombooking " +
                    "SET customer_id = ?, checkindate = CURRENT_TIMESTAMP, roomavailable = false, " +
                    "checkOutDate = DATE_ADD(checkindate,INTERVAL " + numberOfNights + " DAY) WHERE roomnumber = " + roomChoice + ";");
            statement.setInt(1, customerID);

            statement.executeUpdate();


            System.out.println("The room is now booked!:");

            ResultClass.setBookedRoomResult(sqlStatement, "bookedroom", customerID);

            listAllFromTable();
            System.out.println();
        }
    }

    public void checkOut() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerID = USER_INTERFACE.enterInteger("customer id");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("UPDATE roombooking SET checkOutDate = CURRENT_TIMESTAMP, roomavailable = true WHERE customer_id = ? ;");

        statement.setInt(1, customerID);

        statement.executeUpdate();
    }

    public void listAllFromTable() throws SQLException {
        ResultSet result = ResultClass.getResult();

        int columnCount = result.getMetaData().getColumnCount();

        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = result.getMetaData().getColumnName(i + 1);
        }

        for (String columnName : columnNames) {
            System.out.print(UserInterface.PadRight(columnName));
        }

        while (result.next()) {
            System.out.println();
            for (String columnName : columnNames) {
                String value = result.getString(columnName);

                if (value == null)
                    value = "null";

                System.out.print(UserInterface.PadRight(value));
            }
        }
        System.out.println();
    }
}
