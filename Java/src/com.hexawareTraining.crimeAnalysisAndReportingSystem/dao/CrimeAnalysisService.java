package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Evidence;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.LawAgency;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Officer;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Suspect;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Victim;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.GetInputImpl;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.PrintDetailsImpl;

public interface CrimeAnalysisService {
	
	// Given Implementations
	
	boolean createIncident(Incident incident) ;
	
	boolean updateIncidentStatus(String status,int incidentId);
	
	List<Incident> getIncidentsInDateRange(Date startDate,Date endDate);
	
	List<Incident> searchIncidents(String criteria);
	
	Report generateIncidentReport(Incident incident);

	Case createCase(String caseTitle, String caseDescription, List<Incident> incidents);

	Case getCaseDetails(int caseId);

	boolean updateCaseDetails(Case caseObj);

	List<Case> getAllCases();
	
	// Additional Implementations

	int addSuspect(Suspect suspect);

	int addVictim(Victim victim);

	int addEvidence(Evidence evidence);

	int createOfficer(Officer officer);

	int createLawAgency(LawAgency agency);

	Incident extractIncidentFromResultSet(ResultSet rs) throws SQLException;

	String extractReportDetailsFromResultSet(ResultSet rs) throws SQLException;

	void addIncident(LinkedHashSet<Incident> incidents, GetInputImpl input, PrintDetailsImpl output);

	List<Incident> filterIncidents(Set<Integer> filterIDs, List<Incident> incidents);


	

}
