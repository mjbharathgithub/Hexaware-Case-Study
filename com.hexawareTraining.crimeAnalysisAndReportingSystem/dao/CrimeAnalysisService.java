package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.Date;
import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public interface CrimeAnalysisService {
	
	boolean createIncident(Incident incident) ;
	
	boolean updateIncidentStatus(String status,int incidentId);
	
	List<Incident> getIncidentsInDateRange(Date startDate,Date endDate);
	
	List<Incident> searchIncidents(String criteria);
	
	Report generateIncidentReport(Incident incident);
	
	

}
