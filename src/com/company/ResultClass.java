package com.company;

import java.sql.SQLException;
import java.sql.Statement;

public class ResultClass {

    private static java.sql.ResultSet result;

    public static java.sql.ResultSet getResult() {
        return result;
    }

    public static void setResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + ID + ";");
    }

    public static void setFoodResult(Statement sqlStatement, String tableName, int ID) throws SQLException {
        result = sqlStatement.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + ID + ";");
    }

}
