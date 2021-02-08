package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultClass {

    private static java.sql.ResultSet result;
    private static UserInputHandler userInput = new UserInputHandler();

    public static void setResult(Statement sqlStatement, String tableName, int inputId) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE customer_id = " + inputId + ";");
    }

    public static void setAvailableRoomsResult(Statement sqlStatement, String tableName) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + ";");
    }

    public static int getCustomerId(Statement sqlStatement, String fullName) throws SQLException {
        String query = "SELECT * FROM customer WHERE customer_name LIKE '" + fullName + "';";
        result = sqlStatement.executeQuery(query);

        int ID = 0;
        while (result.next()) {
            ID = result.getInt("id");
        }
        return ID;
    }

    public static int checkIfIdExistst(String tableName, int inputID, String columnName) throws SQLException {
        ResultSet result;
        Statement sqlStatement = Run.getSqlStatement();

        while (true) {
            String query = "SELECT * FROM " + tableName + " WHERE ID = " + inputID;
            result = sqlStatement.executeQuery(query);
            int ID = 0;
            while (result.next()) {
                ID = result.getInt(columnName);
            }
            if (!(ID == inputID)) {
                System.out.println("No customer with id " + inputID + " was found, please try again");
                inputID = userInput.getIntFromUser(1, 100);
            } else {
                return ID;
            }
        }
    }

    public static int ifInputMatches(Statement sqlStatement, String query, String columnName) throws SQLException {
        result = sqlStatement.executeQuery(query);

        int ID = 0;
        while (result.next()) {
            ID = result.getInt(columnName);
        }
        return ID;
    }

    public static java.sql.ResultSet getResult() {
        return result;
    }

    public static void setupResult(Statement sqlStatement, String query) throws SQLException {
        int result2 = sqlStatement.executeUpdate(query);
    }
}