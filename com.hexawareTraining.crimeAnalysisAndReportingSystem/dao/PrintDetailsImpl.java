package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public class PrintDetailsImpl implements PrintDetails {
	
	public void printIncidentReport(Report report) {
		System.out.println("Incident Report");
		System.out.println("Report ID : "+report.getReportId());
		System.out.println("Incident ID : "+report.getIncidentId());
		System.out.println("Reporting Officer ID : "+report.getReportingOfficerId());
		System.out.println("Report Date : "+report.getReportDate());
		System.out.println("Report Details : "+report.getReportDetails());
		System.out.println("Report Status : "+report.getStatus());
		
		
		
	}
	
	public void printIncdients(List<Incident> incidents) {
		
		System.out.println("Incidents Found");
		
		for(Incident incident: incidents) {
		System.out.println("Incident ID : "+incident.getIncidentId());
		System.out.println("Incident Type : "+incident.getIncidentType());
		System.out.println("Incident Date : "+incident.getIncidentDate());
		System.out.println("Incident Location : "+incident.getLocation());
		System.out.println("Incident Description : "+incident.getDescription());
		System.out.println("Incident Status : "+incident.getStatus());
		System.out.println();
		}
		
	}


}
