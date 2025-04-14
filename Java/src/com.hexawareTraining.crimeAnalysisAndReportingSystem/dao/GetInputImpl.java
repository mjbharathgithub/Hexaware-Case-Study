package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
//import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalDateInput;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalUserInputException;

public class GetInputImpl implements GetInput {
	
	Scanner scanner = new Scanner(System.in);
	CrimeAnalysisServiceImpl processor=new CrimeAnalysisServiceImpl();
    PrintDetails output=new PrintDetailsImpl();
	@Override
	public Incident inputCreateIncident() {
//		String incidentDate;
		

//        System.out.print("Incident ID: ");
//        int incidentId = scanner.nextInt();
//        scanner.nextLine(); 
		try {
			
		System.out.println("Enter Incident Details:");
        System.out.print("Incident Type: ");
        String incidentType = scanner.nextLine();

        System.out.print("Incident Date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
//		String regex = "^(?!0000)(?:[0-9]{4})-(?!00)(?:0[1-9]|1[0-2])-(?!00)(?:0[1-9]|[12][0-9]|3[01])$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(dateString);
        Date date = Date.valueOf(dateString);
        

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

//        System.out.print("Status (Open,closed or Under Investigation) : ");
        String status = getStatus();

        System.out.print("Victim ID: ");
        int victimId = Integer.parseInt(scanner.nextLine());
//        scanner.nextLine(); 

        System.out.print("Suspect ID: ");
        int suspectId = Integer.parseInt(scanner.nextLine());
//        scanner.nextLine(); 
        
//        if(!pattern.matcher(dateString).matches() || ) {
//        	return null;
//        }
        
        return new Incident(0,incidentType,date,location,description,status,victimId,suspectId);
        
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
//			return null;
		}
		
		catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.err.println("improper date input\ntry again\n");
//			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Input Try Again tihs");
		}
		return null;
                  
	}
	
     private String getStatus() throws IllegalUserInputException {
    	System.out.println("Enter Status (Open, Closed or Under Investigation) : ");
 		String status=scanner.nextLine();
 		if((status.equalsIgnoreCase("Open")||status.equalsIgnoreCase("Closed")||status.equalsIgnoreCase("Under Investigation"))) {
         	return status;
         }
 		else {throw new IllegalUserInputException(status+" is not accepted as Status for Incidents");
 		}
 		
//    	 return "";
		
	}
	
	public String[] updateIncidentStatusInput() {
		try {
		System.out.println("Incident Status Update");
//		System.out.println("Enter Status (Open, Closed or Under Investigation : ");
		
		
		System.out.println("Enter Incient ID : ");
		int incidentId=Integer.parseInt(scanner.nextLine());
		String status=getStatus();
		
//		scanner.nextLine();
		
		
//		System.out.println("INput read successfully");
		return new String[] {status,incidentId+""};
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper ID Input");
		}
		return null;
	}
	
	
	public Date[] getIncidentsInDateRangeInput() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
        System.out.print("Enter the date in YYYY-MM-DD format\n");
        System.out.println("Start Date : ");
        String startDateString = scanner.nextLine();
        Date startDate =Date.valueOf(startDateString);
        System.out.println("End Date : ");
        String endDateString = scanner.nextLine();
        Date endDate =Date.valueOf(endDateString);
        
        int comparedResult=startDate.compareTo(endDate);
        if(comparedResult>0) {
        	throw new IllegalUserInputException("Start Date is Older Than End Date");
        }

 
//            System.out.println("You entered the date: " + startDate+" End dAte"+ endDate+" "+comparedResult);
            
            return new Date[] {startDate,endDate};

		}
		catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Date Entry");
		}
		return null;
	}
	
	public String searchIncidentsInput() {
		try {
		System.out.println("Enter the Criteria to Search the Incident : ");
		System.out.println("Searching Options \n1. Incident ID \n2. Incident Type \n3. Location \n4. Status\n5. exit\n>>");
		String criteria;
		
		int option=Integer.parseInt(scanner.nextLine());
//		scanner.nextLine();
		switch (option) {
		case 1:
			System.out.println("Enter Incident ID : ");
			break;
		case 2:
			System.out.println("Enter Incident Type : ");
			break;
		case 3:
			System.out.println("Enter Incident Location : ");
			break;
		case 4:
//			System.out.println("Enter Incident Status : ");
			criteria=getStatus();
			if(!criteria.equals(""))
				
				return criteria;
			
			throw new IllegalUserInputException(criteria+" is not accepted as Status for Incidents");
			
//			break;
		
		case 5:
			System.out.println("Exiting Search window ");
			return null;	
			
			
		default:
			throw new IllegalArgumentException("Improper Input\nExiting Search window");
		}
		
		criteria=scanner.nextLine();
		return criteria;
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Input\nExiting Search window\nTry Again");
		}
		
		 return null;
		
		
	}
	
	public Incident generateIncidentReportInput() {
//		System.out.println("Report Generation");
		
		System.out.println("Enter Incident ID : ");
		int incidentId=Integer.parseInt(scanner.nextLine());
		
		System.out.println();
		Incident incident = new Incident();
		incident.setIncidentId(incidentId);
		return incident;
		
	}
	@Override
	public Case createCaseInput(CrimeAnalysisServiceImpl processor,PrintDetails ouput) {
        List<Incident> incidents=new ArrayList<>();
        
        System.out.println("Enter Case Title : ");
        String caseTitle= scanner.nextLine();
        System.out.println("Enter Case Description : ");
        String caseDescription = scanner.nextLine();
//        GetInputImpl input=new GetInputImpl();
        
        processor.addIncident(incidents, this, output);
        
//        String caseStatus=scanner.nextLine();

        return processor.createCase(caseTitle, caseDescription, incidents);
    }
	@Override
	public int getCaseByIdInput() {
		System.out.println("Case Detail by ID\nEnter Case ID : ");
		try {
		int searchId= Integer.parseInt(scanner.nextLine());
//		scanner.nextLine();
		return searchId;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Input\nTry Again");
		}
		
		return 0;
		
	}
	
	@Override
	public boolean updateCaseInput(Case case1) {
		
//		int searchId= getCaseByIdInput();
//		scanner.nextLine();
//		Case case1= processor.getCaseDetails(searchId);
		try {
		if(case1==null)
			{
			System.out.println("No Case Found with ID "+case1.getCaseId());
			return false;
			}
//		case_title = ?, case_description = ?, status = ?
		System.out.println("Enter new Case Title : ");
		String newCaseTitle=scanner.nextLine();
		
		System.out.println("Enter new Case Description : ");
		String newCaseDescription=scanner.nextLine();
		
//		System.out.println("Enter new Case Status : (Open, Closed or Under Investigation) : ");
		String newCaseStatus=getStatus();
		
		
		
		
		case1.setCaseTitle(newCaseTitle); 
		case1.setCaseDescription(newCaseDescription);
		case1.setStatus(newCaseStatus);
		System.out.println("Do You want to Add Incident to the Case (yes/no) : ");
		String addIncidentOption =scanner.nextLine();
		if(addIncidentOption.equalsIgnoreCase("yes")) {
			processor.addIncident(case1.getIncidents(), this, output);
		}
		
		
		return processor.updateCaseDetails(case1);
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage()+"\nTry Again");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
