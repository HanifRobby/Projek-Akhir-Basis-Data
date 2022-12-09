package com.basdat.repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnect {
    private static Connection con;

    private static String url = "jdbc:sqlserver://LAPTOP-DKJ85E0O;" +
            "databaseName=test3;" +
            "encrypt=true;" +
            "trustServerCertificate=true;" +
            "integratedSecurity=true;" +
            "loginTimeout = 30;";

    public static String connectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR5O;" +
            "databaseName=test3;" +
            "encrypt=true;" +
            "trustServerCertificate=true;" +
            "integratedSecurity=true;" +
            "loginTimeout = 30;";

    public static String AltConnectionUrl = "";


    public static Connection getConnection() {
        if (con != null) {
            return con;
        }

        return getConnection(url);
    }

    private static Connection getConnection(String url) {
        try {
            con = DriverManager.getConnection(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }


}

