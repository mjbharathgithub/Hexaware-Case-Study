package com.hexawareTraining.crimeAnalysisAndReportingSystem.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.util.DBConnUtil;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.*;

public class MainModule {
	Scanner scanner = new Scanner(System.in);
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public static void main(String[] args)  {
    	MainModule main = new MainModule();
    	Scanner scanner = new Scanner(System.in);
    	CrimeAnalysisServiceImpl processor= new CrimeAnalysisServiceImpl();
    	GetInputImpl input= new GetInputImpl();
    	PrintDetailsImpl output=new PrintDetailsImpl();

    	System.out.println("========== Crime Analysis & Reporting System ==========");
    	mainLoop:
        while (true) {
        	try {
        
            System.out.println("\nSelect an option:");
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. View Incidents in Date Range");
            System.out.println("4. Search Incidents");
            System.out.println("5. Add Evidence to Incident");
            System.out.println("6. Add Officer");
            System.out.println("7. Add Law Agency");
            System.out.println("8. Generate Report");
            System.out.println("9. Add Cases");
            System.out.println("10. Search Case Detail By ID");
            System.out.println("11. Update Case Details");
            System.out.println("12. Get All Cases Details");
            System.out.println("13. Exit");
            
            
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            boolean result;
            switch (choice) {
                case 1:
                	System.out.println("************** Creating Incident **************");
                    result=processor.createIncident(input.inputCreateIncident());
                    
                    System.out.println((result?ANSI_GREEN+"Incident Created sucessfully"+ANSI_RESET:ANSI_RED+"Incident Not Created"+ANSI_RESET));   
                    break;
                    
                case 2:
                	System.out.println("************** Update Incident Status **************");
                	String inputDetails[]=input.updateIncidentStatusInput();
                     result= processor.updateIncidentStatus(inputDetails[0],Integer.parseInt(inputDetails[1]));
                     System.out.println((result?ANSI_GREEN+"Incident Updated sucessfully"+ANSI_RESET:ANSI_RED+"Incident Not Updated"+ANSI_RESET));
                     break;
                     
                case 3:
                	System.out.println("************** Search Incidents In Given Range **************");
                	Date []dateRange = input.getIncidentsInDateRangeInput();
                	if(dateRange==null) throw new Exception();
                    List<Incident> incidentsInGivenDateRange= processor.getIncidentsInDateRange(dateRange[0],dateRange[1]);
                    output.printIncdients(incidentsInGivenDateRange);
                    
                    System.out.println((incidentsInGivenDateRange!=null)?(ANSI_GREEN+"Incidents in the Given Date Found Successfully "+ANSI_RESET):"");
                    break;
                    
                case 4:
                	System.out.println("************** Search Incidents Based On Criteria **************");
                	String criteria=input.searchIncidentsInput();
                	if(criteria==null) break;
                    List<Incident> incidentsWithSpecifiedCriterial= processor.searchIncidents(criteria);
                    output.printIncdients(incidentsWithSpecifiedCriterial);
                    System.out.println((incidentsWithSpecifiedCriterial!=null)?(ANSI_GREEN+"Incidents with the given criteria Found Successfully "+ANSI_RESET):"");
                    break;
                    
                case 5:
                	System.out.println("************** Add Evidence to Incident **************");
                	input.inputEvidenceDetails();
                	break;
              
                case 6:
                	System.out.println("************** Add Officer to the Agency **************");
                	input.inputOfficerDetails();
                	break;
                    
                case 7:
                	System.out.println("************** Add Law Agency **************");
                	input.inputLawAgencyDetails();
                	break;    
                	
                	
                case 8:
                	System.out.println("************** Genrerate Report **************");
                	Report report= processor.generateIncidentReport(input.generateIncidentReportInput());
                	if(report==null) break;
                	output.printIncidentReport(report);
                	System.out.println(((ANSI_GREEN+"Report is generated Successfully "+ANSI_RESET)));
                    break;
                    
                case 9:
                	System.out.println("************** Creating Cases **************");
                    Case case1= input.createCaseInput(processor,output);
                    output.printCase(case1);
                    break;
                    
                case 10:
                	System.out.println("************** Search Case By ID **************");
                	int caseId=input.getCaseByIdInput();
                	if(caseId==0) break;
                	Case case2= processor.getCaseDetails(caseId);
                	if(case2==null) break;
                	output.printCase(case2);
                	break;
                	
                case 11:
                	System.out.println("************** Update Case Details By ID **************");
                	int caseID= input.getCaseByIdInput();
                	if(caseID==0) break;
                	Case case3=processor.getCaseDetails(caseID);
                	if(case3==null) {
                		break;
                	}
                	
                	boolean updateStatus= input.updateCaseInput(case3);
                	if(!updateStatus) break;
                	output.printCase(case3);
                	System.out.println(ANSI_GREEN+"Case is Updated Successfully"+ANSI_RESET);
                	
                	break;
                    
                case 12:
                	System.out.println("************** View All Cases **************");
                
                	List<Case> cases= processor.getAllCases();
                	
                	if(cases==null) {
                		System.out.println(ANSI_RED+"No Cases to View"+ANSI_RESET);
                		break;
                	}
                	cases.forEach(case5->{
                		System.out.println();
                		output.printCase(case5);
                	});
                	
                	System.out.println(ANSI_GREEN+"Cases Have Retrieved Successfully"+ANSI_RESET);
                	break;
                	
                case 13:
                	System.out.println(ANSI_GREEN+"Thank You for using Crime Analysis and Recording System"+ANSI_RESET);
                	break mainLoop;
                		
                
                
                default:
                    System.err.println("Invalid option. Try again.");
                   
            }
        }
        	catch (Exception e) {
				// TODO: handle exception
        		System.err.println("Improper Input Try Again");
			}

    }
}
}
