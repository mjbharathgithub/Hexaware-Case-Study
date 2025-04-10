package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;

public class GetInputImpl implements GetInput {
	
	Scanner scanner = new Scanner(System.in);
	@Override
	public Incident inputCreateIncident() {
		Date incidentDate;
		System.out.println("Enter Incident Details:");

        System.out.print("Incident ID: ");
        int incidentId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Incident Type: ");
        String incidentType = scanner.nextLine();

        System.out.print("Incident Date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
        	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        	 incidentDate =  dateFormat.parse(dateString);
        	 
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use yyyy-MM-dd.");
            return null; 
        }

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Status: ");
        String status = scanner.nextLine();

        System.out.print("Victim ID: ");
        int victimId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Suspect ID: ");
        int suspectId = scanner.nextInt();
        scanner.nextLine(); 

        return new Incident(incidentId,incidentType,incidentDate,location,description,
        		status,victimId,suspectId);          
	}
	
	public String[] updateIncidentStatusInput() {
		System.out.println("Incident Status Update");
		System.out.println("Enter Status : ");
		String status=scanner.nextLine();
		System.out.println("Enter Incient ID : ");
		int incidentId=scanner.nextInt();
		scanner.nextLine();
		
		return new String[] {status,incidentId+""};
	}
	
	
	public Date[] getIncidentsInDateRangeInput() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 

        System.out.print("Enter the date in YYYY-MM-DD format: ");
        String str1 = scanner.nextLine();
        String str2 = scanner.nextLine();

        try {
            Date startDate = dateFormat.parse(str1);
            Date endDate = dateFormat.parse(str2);
            
            System.out.println("You entered the date: " + startDate+" End dAte"+ endDate);
            
            return new Date[] {startDate,endDate};
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        } 
	}
	
	public String searchIncidentsInput() {
		
		System.out.println("Enter the Criteria to Search the Incident : ");
		String criteria=scanner.nextLine();
		
		return criteria;
	}
	
//	public Incident generateIncidentReportInput() {
//		
//	}

}
