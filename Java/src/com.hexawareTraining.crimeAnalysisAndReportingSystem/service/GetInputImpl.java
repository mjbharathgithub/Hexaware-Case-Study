package com.hexawareTraining.crimeAnalysisAndReportingSystem.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.sql.Date;
import java.util.List;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.format.DateTimeParseException;
import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.GetInput;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalUserInputException;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.InvalidContactException;

public class GetInputImpl implements GetInput {
	
	Scanner scanner = new Scanner(System.in);
	CrimeAnalysisServiceImpl processor=new CrimeAnalysisServiceImpl();
    PrintDetailsImpl output=new PrintDetailsImpl();
    
	@Override
	public Incident inputCreateIncident() {

		try {
			
		System.out.println("Enter Incident Details:");
        System.out.print("Incident Type: ");
        String incidentType =scanner.nextLine();

        System.out.print("Incident Date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date date = Date.valueOf(dateString);
        

        System.out.print("Location: ");
        String location =scanner.nextLine();

        System.out.print("Description: ");
        String description =scanner.nextLine();

        String status =getStatus();
        
        int suspectId,victimId,choice;
        
        System.out.print("Add Victim\n1. Create new Victim\n2. Add existing Victim\n3. Exit\n>> ");
        choice=Integer.parseInt(scanner.nextLine());
       
       switch (choice)
       {
       case 1 :
       	victimId= inputVictimDetails();;
       	break;
       	
       case 2:
       	System.out.print("\nVictim ID: ");
           victimId = Integer.parseInt(scanner.nextLine());
           break;
           
       case 3:
       	return null;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
       

        System.out.print("Add suspect\n1. Create new Suspect\n2. Add existing Suspect\n3. Exit\n>> ");
        choice=Integer.parseInt(scanner.nextLine());
        
        switch (choice)
        {
        case 1 :
        	suspectId= inputSuspectDetails();
        	break;
        	
        case 2:
        	System.out.print("Suspect ID: ");
            suspectId = Integer.parseInt(scanner.nextLine());
            break;
            
        case 3:
        	return null;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
        
        
        return new Incident(0,incidentType,date,location,description,status,victimId,suspectId);
        
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage());
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Input Try Again");
		}
		return null;
                  
	}
	
	@Override
	public int inputSuspectDetails() throws Exception {
        
            System.out.println("Enter Suspect First Name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter Suspect Last Name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter Date of Birth (YYYY-MM-DD):");
            String dateString = scanner.nextLine();
            Date date = Date.valueOf(dateString);

            System.out.println("Enter Gender (Male/Female/other):");
            String gender = scanner.nextLine();
            genderCheck(gender);

            String contact = checkAndGetContact();

            Suspect suspect = new Suspect(0,firstName, lastName, date, gender, contact);
            int id = processor.addSuspect(suspect);
            
            if (id != -1) System.out.println("\u001B[32m"+"Suspect added with ID: " + id+"\u001B[0m");
            else System.err.println("Failed to add suspect.");
            
            return id;
        
    }
	
	@Override
	public boolean genderCheck(String gender) {
		if(gender.equalsIgnoreCase("male")|gender.equalsIgnoreCase("female")|gender.equalsIgnoreCase("others")) {
			return true;
		}
		throw new IllegalUserInputException("Gender mismatch please enter gender from the given option ");
	}
	
	@Override
	public int inputVictimDetails() throws Exception {
        
            System.out.println("Enter Victim First Name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter Victim Last Name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter Date of Birth (YYYY-MM-DD):");
            String dateString = scanner.nextLine();
            Date date = Date.valueOf(dateString);

            System.out.println("Enter Gender (Male/female/other):");
            String gender = scanner.nextLine();
            genderCheck(gender);

            String contact = checkAndGetContact();

            Victim victim = new Victim(0,firstName, lastName, date, gender, contact);
            int id = processor.addVictim(victim);

            if (id != -1) System.out.println("\u001B[32m"+"Victim added with ID: " + id+"\u001B[0m");
            else System.err.println("Failed to add victim.");
            return id;
            
    }
	
	
	@Override
	 public void inputEvidenceDetails() {
	        try {
	            System.out.println("Enter Evidence Description:");
	            String description = scanner.nextLine();

	            System.out.println("Enter Location Found:");
	            String location = scanner.nextLine();

	            System.out.println("Enter Incident ID:");
	            int incidentId = Integer.parseInt(scanner.nextLine());

	            Evidence evidence = new Evidence(0,description, location, incidentId);
	            int id = processor.addEvidence(evidence);

	            if (id != -1) System.out.println("\u001B[32m"+"Evidence added with ID: " + id+"\u001B[0m");
	            else System.err.println("Failed to add evidence.");
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid input. Incident ID must be an integer.");
	        } catch (Exception e) {
	            System.err.println("Failed to add evidence.");
	        }
	 }
	
	@Override
	public void inputOfficerDetails() {
	    try {
	        System.out.println("Enter Officer First Name:");
	        String firstName = scanner.nextLine();

	        System.out.println("Enter Officer Last Name:");
	        String lastName = scanner.nextLine();

	        System.out.println("Enter Badge Number:");
	        String badge = scanner.nextLine();

	        System.out.println("Enter Officer Rank (Integer):");
	        int rank = Integer.parseInt(scanner.nextLine());

	        String contact = checkAndGetContact();
	        
	        System.out.println("Enter Agency ID:");
	        int agencyId = Integer.parseInt(scanner.nextLine());

	        Officer officer = new Officer(0,firstName, lastName, badge, rank, contact, agencyId);
	        int id = processor.createOfficer(officer);

	        if (id != -1) System.out.println("\u001B[32m"+"Officer added with ID: " + id+"\u001B[0m");
	        else System.err.println("Failed to add officer.");
	        
	    } catch (InvalidContactException e) {
	        System.err.println(e.getMessage());
	    } catch (Exception e) {
	        System.err.println("Improper Input\nTry again ");
	    }
	}
	
	private String checkAndGetContact() throws InvalidContactException {
		System.out.println("Enter Contact Number (10-digit):");
        String contact = scanner.nextLine();
		if (!contact.matches("\\d{10}")) {
            throw new InvalidContactException("Officer contact must be a 10-digit number.");
        }
		return contact;
		
	}

	@Override
	public void inputLawAgencyDetails() {
	    try {
	        System.out.println("Enter Agency Name:");
	        String name = scanner.nextLine();

	        System.out.println("Enter Jurisdiction:");
	        String jurisdiction = scanner.nextLine();

	        String contact = checkAndGetContact();

	        System.out.println("Enter Officer ID (in charge):");
	        int officerId = Integer.parseInt(scanner.nextLine());

	        LawAgency agency = new LawAgency(0,name, jurisdiction, contact, officerId);
	        int id = processor.createLawAgency(agency);

	        if (id != -1) System.out.println("\u001B[32m"+"Law Agency added with ID: " + id+"\u001B[0m");
	        else System.err.println("Failed to create agency.");
	    } catch (InvalidContactException e) {
	        System.err.println("Validation failed: " + e.getMessage());
	    } catch (Exception e) {
	        System.err.println("Something went wrong: " + e.getMessage());
	    }
	}

	@Override
    public String getStatus() throws IllegalUserInputException {
   	System.out.println("Enter Status (Open, Closed or Under Investigation) : ");
		String status=scanner.nextLine();
		if((status.equalsIgnoreCase("Open")||status.equalsIgnoreCase("Closed")||status.equalsIgnoreCase("Under Investigation"))) {
        	return status;
        }
		else {throw new IllegalUserInputException(status+" is not accepted as Status for Incidents");
		}

		
	}
	
	@Override
	public String[] updateIncidentStatusInput() {
		try {
		System.out.println("Incident Status Update");

		System.out.println("Enter Incient ID : ");
		int incidentId=Integer.parseInt(scanner.nextLine());
		String status=getStatus();
		
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
	
	@Override
	public Date[] getIncidentsInDateRangeInput() {

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
	
	@Override
	public String searchIncidentsInput() {
		try {
		System.out.println("Enter the Criteria to Search the Incident : ");
		System.out.print("Searching Options \n1. Incident ID \n2. Incident Type \n3. Location \n4. Status\n5. exit\n>>");
		String criteria;
		
		int option=Integer.parseInt(scanner.nextLine());

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

			criteria=getStatus();
			if(!criteria.equals(""))
				
				return criteria;
			
			throw new IllegalUserInputException(criteria+" is not accepted as Status for Incidents");
			
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
	
	
	@Override
	public Incident generateIncidentReportInput() {

		System.out.println("Enter Incident ID : ");
		int incidentId=Integer.parseInt(scanner.nextLine());
		
		System.out.println();
		Incident incident = new Incident();
		incident.setIncidentId(incidentId);
		return incident;
		
	}
	
	
	@Override
	public Case createCaseInput(CrimeAnalysisServiceImpl processor,PrintDetailsImpl ouput) {
        List<Incident> incidents= new LinkedList<>();
        
        System.out.println("Enter Case Title : ");
        String caseTitle= scanner.nextLine();
        System.out.println("Enter Case Description : ");
        String caseDescription = scanner.nextLine();

        processor.addIncident(incidents, this, output);


        return processor.createCase(caseTitle, caseDescription, new LinkedList<>(incidents));
    }
	
	@Override
	public int getCaseByIdInput() {
		System.out.println("Case Detail by ID\nEnter Case ID : ");
		try {
		int searchId= Integer.parseInt(scanner.nextLine());

		return searchId;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Improper Input\nTry Again");
		}
		
		return 0;
		
	}
	
	@Override
	public boolean updateCaseInput(Case caseObject) {


		try {
		if(caseObject==null)
			{
			System.err.println("No Case Found with ID : "+caseObject.getCaseId());
			return false;
			}

		System.out.println("Enter new Case Title : ");
		String newCaseTitle=scanner.nextLine();
		
		System.out.println("Enter new Case Description : ");
		String newCaseDescription=scanner.nextLine();


		String newCaseStatus=getStatus();
		
		caseObject.setCaseTitle(newCaseTitle); 
		caseObject.setCaseDescription(newCaseDescription);
		caseObject.setStatus(newCaseStatus);
		
		System.out.println("Do You want to Add Incident to the Case (yes/no) : ");
		String addIncidentOption =scanner.nextLine();
		
		if(addIncidentOption.equalsIgnoreCase("yes")) {
			processor.addIncident(caseObject.getIncidents(), this, output);
		}
		
		return processor.updateCaseDetails(caseObject);
		
		}catch (IllegalUserInputException e) {
			// TODO: handle exception
			System.err.println(e.getLocalizedMessage()+"\nTry Again");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Failed to Update Case");
		}
		return false;
	}

	

}
