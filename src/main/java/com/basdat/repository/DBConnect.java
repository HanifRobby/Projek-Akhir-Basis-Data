package com.basdat.repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnect {

    public static String connectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR5O;" +
            "databaseName=test;" +
            "encrypt=true;" +
            "trustServerCertificate=true;" +
            "integratedSecurity=true;";

    public static String AltConnectionUrl = "jdbc:sqlserver://LAPTOP-72IKFR5O;" +
            "databaseName = test4;" +
            "encrypt = true;" +
            "trustServerCertificate = true;" +
            "loginTimeout = 30;";

    public static void ConnectDB(){

    }


}

