package com.hexawareTraining.crimeAnalysisAndReportingSystem.service;

import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.PrintDetails;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public class PrintDetailsImpl implements PrintDetails {
	@Override
	public void printIncidentReport(Report report) {
		if(report==null)  {
			System.err.println("No Report Found");
			return;
		}
		System.out.println("___________Report Details___________");
		System.out.println("Report ID : "+report.getReportId());
		System.out.println("Incident ID : "+report.getIncidentId());
		System.out.println("Reporting Officer ID : "+report.getReportingOfficerId());
		System.out.println("Report Date : "+report.getReportDate());
		System.out.println("\n___________Related Details___________\n\n"+report.getReportDetails());
		System.out.println("\nReport Status : "+report.getStatus()+"\n-------------------------------------------------------------------");
		
		
		
	} 
	
	
	@Override
	public void printIncdients(List<Incident> incidents) {
		
		
		if(incidents==null)  {
			System.err.println("Improper Input\nTry Again");
			
		}
		else if(incidents.isEmpty()) {
			System.err.println("\nNo Incidents For this Case\n");
			
		}
		else {
		System.out.println("_______Incidents Found_____");
		for(Incident incident: incidents) {
		System.out.println("Incident ID : "+incident.getIncidentId());
		System.out.println("Incident Type : "+incident.getIncidentType());
		System.out.println("Incident Date : "+incident.getIncidentDate());
		System.out.println("Incident Location : "+incident.getLocation());
		System.out.println("Incident Description : "+incident.getDescription());
		System.out.println("Incident Status : "+incident.getStatus());
		System.out.println("--------------------------------------------");
		}
		}
		
	}



	@Override
	public void printCase(Case caseObject) {
		// TODO Auto-generated method stub
		if(caseObject.getCaseId()==0) {
			System.err.println("Case not Found \nTry Again");
			return;
		}
		if(caseObject==null)  {
			System.err.println("Improper Input\nTry Again");
			return;
		}
		System.out.println("\n________________Case Details________________");
		System.out.println("Case ID : "+caseObject.getCaseId());
		System.out.println("Case Title : "+caseObject.getCaseTitle());
		System.out.println("Case Description : "+caseObject.getCaseDescription());
		System.out.println("Case createad Date : "+caseObject.getCreatedAt());
		printIncdients(caseObject.getIncidents());
		System.out.println("Case Status : "+caseObject.getStatus()); 
		
		
	}


}
