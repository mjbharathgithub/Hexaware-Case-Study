package com.hexawareTraining.crimeAnalysisAndReportingSystem.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnUtil {
    public static Connection getConn() throws SQLException, IOException {
        
    	String connectionStrings[]=DBPropertyUtil.getConnectionString();
    	String URL=connectionStrings[0];
    	String USER_NAME=connectionStrings[1];
    	String PASSWORD=connectionStrings[2];
    	return DriverManager.getConnection(URL,USER_NAME,PASSWORD);
    }
}
