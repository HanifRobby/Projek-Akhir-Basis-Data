package com.basdat.repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
    public static void ConnectDB(){
        String connectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR5O;" +
            "databaseName=University;" +
            "encrypt=true;trustServerCertificate=true;" +
            "integratedSecurity=true;";

//        String connectionUrl =
//                "jdbc:sqlserver://LAPTOP-72IKFR5O.database.windows.net:1433;" +
//                "database=University;" +
//                "user=hanif@LAPTOP-72IKFR5O;" +
//                "password=password;" +
//                "encrypt=true;" +
//                "trustServerCertificate=false;" +
//                "loginTimeout=30;";
        ResultSet resultSet = null;

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 5 id, name,dept_name from instructor";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " (" +
                                    resultSet.getString(3) + ")");
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

