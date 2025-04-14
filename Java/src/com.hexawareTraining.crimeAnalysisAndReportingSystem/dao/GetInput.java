package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public interface CrimeAnalysisService {
	
	boolean createIncident(Incident incident) ;
	
	boolean updateIncidentStatus(String status,int incidentId);
	
	List<Incident> getIncidentsInDateRange(Date startDate,Date endDate);
	
	List<Incident> searchIncidents(String criteria);
	
	Report generateIncidentReport(Incident incident);

	Case createCase(String caseTitle, String caseDescription, List<Incident> incidents);

	Case getCaseDetails(int caseId);

	boolean updateCaseDetails(Case caseObj);

	List<Case> getAllCases();

//	void addIncident(List<Incident> incidents, GetInput input, CrimeAnalysisServiceImpl processor,
//			PrintDetails output);

	void addIncident(List<Incident> incidents, GetInput input, PrintDetails output);
	
	

}
