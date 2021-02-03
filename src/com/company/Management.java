package com.company;

import java.sql.*;

public class Management {
    private static final UserInterface USER_INTERFACE = new UserInterface();
    private static Statement sqlStatement;

    public void newCustomer() throws SQLException {
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

        //Customer customer = new Customer(fullName, customerId, contactNumber); kanske behöver objektet sen?
    }

    public void searchCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterInteger("customer ID");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ? ");
        statement.setInt(1, customerId);

        statement.executeQuery();

        ResultClass.setResult(sqlStatement,"customer",customerId);

        listAllFromTable();
    }

    public void manageCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterInteger("customer ID");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("DELETE FROM customer WHERE id = ?");
        statement.setInt(1, customerId);

        statement.executeUpdate();

        System.out.println("Customer with Id :" + customerId + " deleted." );
        System.out.println();
    }

    public void foodOrder() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        System.out.println("Please choose something from the menu");
        System.out.println();
        int foodChoice = USER_INTERFACE.foodChoice();

        int customerID = USER_INTERFACE.enterInteger("customer id");


        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("INSERT INTO foodOrder (food_Id, customer_id)\n" +
                "VALUES ( ?, ? )");
        statement.setInt(1, foodChoice);
        statement.setInt(2,customerID);

        statement.executeUpdate();

        ResultClass.setFoodResult(sqlStatement,"customerFoodOrder", customerID);
        System.out.println("Total food orders for custmer:");
        listAllFromTable();
        System.out.println();
    }

    public void checkOutWithBill() {

    }

    public void roomInfo() {

    }

    public void roomAvailability() {

    }

    public void bookRoom() {

    }

    public void orderFood() {

    }

    public void checkOut() {

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
