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
        int contactNumber = USER_INTERFACE.enterContactNumber("customer contact number");

        //Customer customer = new Customer(fullName, customerId, contactNumber); kanske behöver objektet sen?

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("INSERT INTO customer(Customer_name, contact_number)\n" +
                "VALUES( ? , ? );");
        statement.setString(1, fullName);
        statement.setInt(2, contactNumber);

        statement.executeUpdate();
    }

    public void searchCustomer() throws SQLException {
        sqlStatement = Run.getSqlStatement();
        int customerId = USER_INTERFACE.enterContactNumber("customer ID");

        PreparedStatement statement = sqlStatement.getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ? ");
        statement.setInt(1, customerId);

        statement.executeQuery();

        ResultClass.setResult(sqlStatement,"customer",customerId);

        listAllFromTable();
    }

    public void manageCustomer() {

    }

    public void foodOrder() {

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
