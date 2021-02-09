package com.company;

import java.io.IOException;
import java.sql.*;

public class Run {
    private static final UserInterface USER_INTERFACE = new UserInterface();
    private final static Management MANAGEMENT = new Management();
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String dbAddress = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "hotel_booking_system";
    private static final String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String userName = "root";
    private static Statement sqlStatement;
    private static Connection connection;
    private static boolean exitProgram;
    private static boolean exitLoop;
    //#region password
    private static final String password = "Jole0257!";
    //endregion


    Run() {
        exitLoop = false;
        exitProgram = false;
    }

    public void Program() throws SQLException {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress +dbName+timezone, userName, password);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            createDatabase();
        }
        try {
            createSqlStatement();
            while (!exitProgram) {
                int selection = USER_INTERFACE.adminOrCustomerChoice();
                adminOrCustomer(selection);
            }
            System.out.println("Thanks and bye!");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void adminOrCustomer(int selection) throws SQLException, IOException, ClassNotFoundException {
        switch (selection) {
            case 1 -> {
                exitLoop = false;
                while (!exitLoop) {
                    selection = USER_INTERFACE.adminChoice();
                    adminView(selection);
                }
            }
            case 2 -> {
                exitLoop = false;
                while (!exitLoop) {
                    selection = USER_INTERFACE.customerChoice();
                    customerView(selection);
                }
            }
            case 0 -> exitProgram = true;
        }
    }

    public void adminView(int selection) throws SQLException, IOException, ClassNotFoundException {
        switch (selection) {
            case 1 -> MANAGEMENT.newCustomer();
            case 2 -> MANAGEMENT.searchCustomer();
            case 3 -> MANAGEMENT.deleteCustomer();
            case 4 -> MANAGEMENT.bookRoom();
            case 5 -> {
                int foodChoice = USER_INTERFACE.foodChoice();
                if (!(foodChoice == 0)) {
                    MANAGEMENT.foodOrder(foodChoice);
                }
            }
            case 6 -> {
                int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);
                MANAGEMENT.checkOutWithBill(customerID);
            }
            case 7 -> MANAGEMENT.setup();

            case 0 -> exitLoop = true;
        }
    }

    public void customerView(int selection) throws SQLException, IOException, ClassNotFoundException {
        switch (selection) {
            case 1 -> {
                int roomChoice = USER_INTERFACE.roomChoice();
                MANAGEMENT.roomDetails(roomChoice);
            }
            case 2 -> MANAGEMENT.roomAvailability();
            case 3 -> MANAGEMENT.bookRoom();

            case 4 -> {
                int foodChoice = USER_INTERFACE.foodChoice();
                if (!(foodChoice == 0)) {
                    MANAGEMENT.foodOrder(foodChoice);
                }
            }
            case 5 -> {
                int customerID = USER_INTERFACE.enterInteger("customer id", 1, 100);
                MANAGEMENT.checkOutWithBill(customerID);
            }
            case 0 -> exitLoop = true;
        }
    }

    public static Statement getSqlStatement() {
        return sqlStatement;
    }

    public static void createSqlStatement() throws SQLException {
        System.out.println("Anslutningen lyckades!\n");
        sqlStatement = connection.createStatement();
    }

    private void createDatabase() {
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(dbAddress + timezone, userName, password);
            Statement s = connection.createStatement();
            int createDatabase = s.executeUpdate("CREATE DATABASE " + dbName);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
