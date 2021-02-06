package com.company;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Management implements Serializable {
    private static final UserInterface USER_INTERFACE = new UserInterface();
    private static final String FOOD_ORDERS = "food_bill.txt";
    private static final String ROOM_COST = "_room_cost.txt";
    private static Statement sqlStatement;
    private static long totalFoodOrder;
    private final static String TOTAL_BILL = "_bill.txt";
    private static int numberOfNights;

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

        System.out.println(fullName + " successfully registered with cusmer Id: " + customerID);
        System.out.println();

        List<Object> foodOrders = new ArrayList<>();

        DataHandler.writeToFile(foodOrders, customerID + FOOD_ORDERS);

        List<Object> roomCost = new ArrayList<>();

        DataHandler.writeToFile(roomCost, customerID + ROOM_COST);

        List<Transaction> transactions = new ArrayList<>();

        DataHandler.writeToFile(transactions, customerID + TOTAL_BILL);
    }

    public void searchCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        System.out.println("1. Search by Id");
        System.out.println("2. Search by name");
        int ans = USER_INTERFACE.enterInteger("", 1, 2);
        switch (ans) {
            case 1 -> {
                int customerId = USER_INTERFACE.enterInteger("customer ID", 1, 100);

                PreparedStatement statement = sqlStatement.getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ? ");
                statement.setInt(1, customerId);

                statement.executeQuery();

                ResultClass.setResult(sqlStatement, "customer", customerId);
            }
            case 2 -> {
                String firstName = USER_INTERFACE.enterValue("customer first name");
                String lastName = USER_INTERFACE.enterValue("customer last name");
                String fullName = firstName + " " + lastName;
                int customerId = ResultClass.searchCustomerByName(sqlStatement, fullName);

                PreparedStatement statement = sqlStatement.getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ? ");
                statement.setInt(1, customerId);

                statement.executeQuery();

                ResultClass.setResult(sqlStatement, "customer", customerId);
            }
        }

        listAllFromTable();
        System.out.println();
    }

    public void deleteCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterInteger("customer ID",1, 100);
        customerId = ResultClass.checkIfIdExistst("customer",customerId, "Id");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("DELETE FROM customer WHERE id = ?");
        statement.setInt(1, customerId);

        statement.executeUpdate();

        System.out.println("Customer with Id " + customerId + " successfully deleted.");
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

    public void foodOrder2(int foodChoice) throws IOException, ClassNotFoundException {

//        int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);
//
//        List<Food> foodOrder = DataHandler.readFromFile(customerID + FOOD_ORDERS);
//
//        foodOrder.add(Food.listOfFood().get(foodChoice - 1));
//
//        DataHandler.writeToFile(foodOrder, customerID + FOOD_ORDERS);
//        foodOrder = DataHandler.readFromFile(customerID + FOOD_ORDERS);
//        System.out.println();
//        System.out.println("Order History:");
//        foodOrder.stream().map(s -> s.meal).forEach(System.out::println);
//        System.out.println();
//        System.out.println("Total ammount:");
//        totalFoodOrder = foodOrder.stream()
//                .mapToInt(s -> s.price).summaryStatistics().getSum();
//        System.out.println(totalFoodOrder + " SEK");
//        System.out.println();

        int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);

        List<Transaction> foodOrder = DataHandler.readFromFile(customerID + TOTAL_BILL);

        foodOrder.add(Food.listOfFood().get(foodChoice - 1));

        DataHandler.writeToFile(foodOrder, customerID + TOTAL_BILL);
        foodOrder = DataHandler.readFromFile(customerID + TOTAL_BILL);
        System.out.println();
        System.out.println("Order History:");
        foodOrder.stream().map(s -> s.getNameOfTransaction()).forEach(System.out::println);
        System.out.println();
        System.out.println("Total ammount:");
        totalFoodOrder = foodOrder.stream()
                .mapToInt(s -> s.getPrice()).summaryStatistics().getSum();
        System.out.println(totalFoodOrder + " SEK");
        System.out.println();
    }

    public void checkOutWithBill() throws SQLException, IOException, ClassNotFoundException {
        sqlStatement = Run.getSqlStatement();
        int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("UPDATE roombooking SET checkOutDate = CURRENT_TIMESTAMP, roomavailable = true WHERE customer_id = ? ;");

        statement.setInt(1, customerID);

        statement.executeUpdate();

        System.out.println("Bill for customer with id " + customerID + ":");
        getBill(customerID);
        System.out.println();
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

            numberOfNights = USER_INTERFACE.enterInteger("number of nights to book",1 ,14);

            int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);
            customerID = ResultClass.checkIfIdExistst("customer",customerID, "id");

            PreparedStatement statement = sqlStatement.getConnection().prepareStatement("UPDATE roombooking " +
                    "SET customer_id = ?, checkindate = CURRENT_TIMESTAMP, roomavailable = false, " +
                    "checkOutDate = DATE_ADD(checkindate,INTERVAL " + numberOfNights + " DAY) WHERE roomnumber = " + roomChoice + ";");
            statement.setInt(1, customerID);

            statement.executeUpdate();

            System.out.println("The room is now booked!:");

            ResultClass.setBookedRoomResult(sqlStatement, "bookedroom", customerID);

            listAllFromTable();
            System.out.println();

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(Room.rooms().get(roomChoice - 1));

            DataHandler.writeToFile(transactions,customerID + TOTAL_BILL);
        }
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

    public static void getBill(int customerId) throws IOException, ClassNotFoundException {

        List<Transaction> transactions = DataHandler.readFromFile(customerId + TOTAL_BILL);

        DataHandler.writeToFile(transactions, customerId + TOTAL_BILL);

        transactions = DataHandler.readFromFile(customerId + TOTAL_BILL);

        System.out.println("-Room-");
        transactions.stream()
                .filter(s -> s instanceof Room)
                .map (s -> (Room) s)
                .map(s -> s.getNameOfTransaction() + " " + s.getPrice() + " SEK - " + numberOfNights + " nights")
                .forEach(System.out::println);

        long roomCost = (transactions.stream()
                .filter(s -> s instanceof Room)
                .map (s -> (Room) s)
                .mapToInt(Room::getPrice).summaryStatistics().getSum()) * numberOfNights;
        System.out.println(roomCost);

        System.out.println("-Roomservice-");
        transactions.stream()
                .filter(s -> s instanceof Food)
                .map (s -> (Food) s)
                .map(s -> "- " + s.getNameOfTransaction() + " " + s.getPrice() + " SEK")
                .forEach(System.out::println);

        long foodOrder = transactions.stream()
                .filter(s -> s instanceof Food)
                .map (s -> (Food) s)
                .mapToInt(Food::getPrice).summaryStatistics().getSum();

        System.out.println();
        System.out.println("Total: " + (roomCost + foodOrder) + " SEK");
        System.out.println();
        System.out.println("Thanks for visiting us, welcome back!");
    }
}
