package com.company;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultClass {

    private static java.sql.ResultSet result;

    public static java.sql.ResultSet getResult() {
        return result;
    }

    public static void setResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + ID + ";");
    }

    public static void setFoodResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE customer_id = " + ID + ";");
    }

    public static void setBookedRoomResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE customer_ID = " + ID + ";");
    }
    public static void setRoomResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE roomNumber = " + ID + ";");
    }

    public static List<Integer> getCustomerFoodOrders(Statement sqlStatement, int customerID) throws SQLException {
        String query = "SELECT food_id FROM foodorder WHERE customer_id = " + customerID + ";";
        result = sqlStatement.executeQuery(query);
        List<Integer> customerFoodOrder = new ArrayList<>();

        int ID = 0;
        while (result.next()) {
            ID = result.getInt("food_id");
            customerFoodOrder.add(ID);
        }
        return customerFoodOrder;
    }

}