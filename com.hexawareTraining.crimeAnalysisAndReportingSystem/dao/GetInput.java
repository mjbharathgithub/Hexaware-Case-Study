package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.Date;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;

public interface GetInput {
	Incident inputCreateIncident();
	String[] updateIncidentStatusInput();
	Date[] getIncidentsInDateRangeInput();
	String searchIncidentsInput();


}
