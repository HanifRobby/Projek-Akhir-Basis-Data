package com.basdat.repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnect {

    public static String connectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR50;" +
            "databaseName=test3;" +
            "encrypt=true;trustServerCertificate=true;" +
            "integratedSecurity=true;";

    public static String AltConnectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR50;" +
            "databaseName = University;" +
            "user = AdminPA;" +
            "password = admin123" +
            "encrypt = true;" +
            "trustServerCertificate = true;" +
            "loginTimeout = 30;";

    public static void ConnectDB(){

    }


}

