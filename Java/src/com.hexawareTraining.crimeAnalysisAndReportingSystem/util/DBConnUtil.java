package com.hexawareTraining.crimeAnalysisAndReportingSystem.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DBConnUtil {
    
    private static Connection conn;

    public static Connection getConn() {
        if (conn == null) {
            try {
                String connectionString = DBPropertyUtil.getPropertyString("resources/db.properties");
                String []connectionCredentials= connectionString.split("\\|");
                String url= connectionCredentials[0];
                String user= connectionCredentials[1];
                String password= connectionCredentials[2];
                
                conn = DriverManager.getConnection(url,user,password);
                System.out.println("\u001B[32m"+"Connection to DataBase is established Successfully"+"\u001B[0m");
            } catch (SQLException e) {
               System.err.println("Failed To establish connection to Database");
            }
        }
        return conn;
    }
}
