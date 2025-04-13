package com.hexawareTraining.crimeAnalysisAndReportingSystem.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

public class DBPropertyUtil{
    public static String[] getConnectionString() throws IOException {
    	Properties props = new Properties();
    	String[] connectionStrings = new String[3];
        try (InputStream inputStream = new FileInputStream("resources/db.properties"))
        {			
            props.load(inputStream);
            connectionStrings[0] = props.getProperty("db.url");
            connectionStrings[1] = props.getProperty("db.username");
            connectionStrings[2] = props.getProperty("db.password");
            
//            System.out.println(url+" usename ; "+username+" password : "+password);

            return connectionStrings;
        }
    }
}
