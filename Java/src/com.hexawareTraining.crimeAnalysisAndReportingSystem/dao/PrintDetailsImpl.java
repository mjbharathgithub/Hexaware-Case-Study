package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public class PrintDetailsImpl implements PrintDetails {
	@Override
	public void printIncidentReport(Report report) {
		if(report==null)  {
			System.out.println("No Report Found");
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
			return;
		}
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



	@Override
	public void printCase(Case case1) {
		// TODO Auto-generated method stub
		if(case1==null)  {
			System.err.println("Improper Input\nTry Again");
			return;
		}
		System.out.println("\n________________Case Details________________");
		System.out.println("Case ID : "+case1.getCaseId());
		System.out.println("Case Title : "+case1.getCaseTitle());
		System.out.println("Case Description : "+case1.getCaseDescription());
		System.out.println("Case createad Date : "+case1.getCreatedAt());
//		System.out.println("size "+ case1.getIncidents().size());
		printIncdients(case1.getIncidents());
		System.out.println("Case Status : "+case1.getStatus()); 
		
		
	}


}
