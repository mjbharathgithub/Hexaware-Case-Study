package com.hexawareTraining.crimeAnalysisAndReportingSystem.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.CrimeAnalysisServiceImpl;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.GetInput;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.GetInputImpl;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.PrintDetails;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.PrintDetailsImpl;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;


public class MainModule {
	Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) throws SQLException, IOException {
    	MainModule main = new MainModule();
    	CrimeAnalysisServiceImpl processor= new CrimeAnalysisServiceImpl();
    	GetInput input= new GetInputImpl();
    	PrintDetails output=new PrintDetailsImpl();
    	
    	System.out.println("========== Crime Analysis & Reporting System ==========");
    	mainLoop:
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. View Incidents in Date Range");
            System.out.println("4. Search Incidents");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(main.scanner.nextLine());

            switch (choice) {
                case 1:
                    processor.createIncident(input.inputCreateIncident());
                    break;
                    
                case 2:
                	String inputDetails[]=input.updateIncidentStatusInput();
                    processor.updateIncidentStatus(inputDetails[0],Integer.parseInt(inputDetails[1]));
                    break;
                case 3:
                	Date []dateRange = input.getIncidentsInDateRangeInput();
                    List<Incident> incidentsInGivenDateRange= processor.getIncidentsInDateRange(dateRange[0],dateRange[1]);
                    output.printIncdients(incidentsInGivenDateRange);
                    break;
                case 4:
                    List<Incident> incidentsWithSpecifiedCriterial= processor.searchIncidents(input.searchIncidentsInput());
                    output.printIncdients(incidentsWithSpecifiedCriterial);
                    break;
                case 5:
                	Report report= processor.generateIncidentReport(input.inputCreateIncident());
                	output.printIncidentReport(report);
                    break;
                case 6:
                    System.out.println("Exiting system. Thank you!");
                    break mainLoop;
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
