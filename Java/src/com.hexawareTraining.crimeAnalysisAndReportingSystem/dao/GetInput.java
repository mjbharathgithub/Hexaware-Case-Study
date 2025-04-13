package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.sql.Date;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;

public interface GetInput {
	Incident inputCreateIncident();
	String[] updateIncidentStatusInput();
	Date[] getIncidentsInDateRangeInput();
	String searchIncidentsInput();
	Incident generateIncidentReportInput();
	Case createCaseInput(CrimeAnalysisServiceImpl processor,PrintDetails ouput);
//	void getCaseDetailsInput();
	int getCaseByIdInput();
	void updateCaseInput(Case case1);
//	Case updateCaseInput(Case case1);


}
