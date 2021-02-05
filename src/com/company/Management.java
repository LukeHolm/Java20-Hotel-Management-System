package com.company;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Management implements Serializable {
    private static final UserInterface USER_INTERFACE = new UserInterface();
    private static final String FOOD_ORDERS = "_bill.txt";
    private static final String ROOM_COST = "_room_cost.txt";
    private static Statement sqlStatement;
    private static long totalFoodOrder;
    private static long totalPriceForRoom;

    public void newCustomer() throws SQLException, IOException {
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

        List<Object> foodOrders = new ArrayList<>();

        DataHandler.writeToFile(foodOrders, customerID + FOOD_ORDERS);

        List<Object> roomCost = new ArrayList<>();

        DataHandler.writeToFile(roomCost, customerID + ROOM_COST);
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

    public void foodOrder() throws IOException {

//        System.out.println("Please choose something from the menu");
//        System.out.println();
//        int foodChoice = USER_INTERFACE.foodChoice();
//
//        if (!(foodChoice == 0)) {
//            int customerID = USER_INTERFACE.enterInteger("customer id");
//
//            try {
//                FileInputStream existingBill = new FileInputStream(customerID + FILENAME);
//                ObjectInputStream readExistingBill = new ObjectInputStream(existingBill);
//                List<Food> foodOrder = (List<Food>) readExistingBill.readObject();
//
//                switch (foodChoice) {
//                    case 1 -> foodOrder.add(Food.listOfFood().get(0));
//                    case 2 -> foodOrder.add(Food.listOfFood().get(1));
//                    case 3 -> foodOrder.add(Food.listOfFood().get(2));
//                    case 4 -> foodOrder.add(Food.listOfFood().get(3));
//                }
//
//                FileOutputStream fos = new FileOutputStream(customerID + FILENAME);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//                oos.writeObject(foodOrder);
//                oos.close();
//                readExistingBill.close();
//
//                FileInputStream fis = new FileInputStream(customerID + FILENAME);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                List<Food> foodOrders = (List<Food>) ois.readObject();
//                System.out.println("Order History:");
//                for (Food n : foodOrders) {
//                    System.out.println(n);
//                }
//                System.out.println();
//                ois.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    public void foodOrder2() throws IOException, ClassNotFoundException {

        System.out.println("Please choose something from the menu");
        System.out.println();
        int foodChoice = USER_INTERFACE.foodChoice();

        if (!(foodChoice == 0)) {
            int customerID = USER_INTERFACE.enterInteger("customer id");

            List<Food> foodOrder = DataHandler.readFromFile(customerID + FOOD_ORDERS);

            foodOrder.add(Food.listOfFood().get(foodChoice - 1));

            DataHandler.writeToFile(foodOrder, customerID + FOOD_ORDERS);
            foodOrder = DataHandler.readFromFile(customerID + FOOD_ORDERS);
            System.out.println();
            System.out.println("Order History:");
            foodOrder.stream().map(s -> s.meal).forEach(System.out::println);
            System.out.println();
            System.out.println("Total ammount:");
            totalFoodOrder = foodOrder.stream()
                    .mapToInt(s -> s.price).summaryStatistics().getSum();
            System.out.println(totalFoodOrder + " SEK");
            System.out.println();
        }
    }

    public void checkOutWithBill() {
        System.out.println("Thanks for visiting us, welcome back!");

        System.out.println("Total price to pay:");
        System.out.println(totalFoodOrder + totalPriceForRoom);
    }

    public int roomDetails() throws IOException, ClassNotFoundException {
        sqlStatement = Run.getSqlStatement();

        int roomChoice = USER_INTERFACE.roomChoice();

        List<Room> rooms = DataHandler.readFromFile("Rooms.txt");
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

            List<Room> roomCost = DataHandler.readFromFile(customerID + ROOM_COST);
            roomCost.add(Room.rooms().get(roomChoice - 1));

            DataHandler.writeToFile(roomCost, customerID + ROOM_COST);
            roomCost = DataHandler.readFromFile(customerID + ROOM_COST);
            totalPriceForRoom = (roomCost.stream()
                    .mapToInt(s -> s.price).summaryStatistics().getSum() * numberOfNights);
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
