package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.*;
import java.sql.Date;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalUserInputException;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.CrimeAnalysisServiceImpl;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.PrintDetailsImpl;

public interface GetInput {
	public Incident inputCreateIncident();
	public String[] updateIncidentStatusInput();
	public Date[] getIncidentsInDateRangeInput();
	public String searchIncidentsInput();
	public void inputEvidenceDetails();
	public Incident generateIncidentReportInput();
	public int getCaseByIdInput();
	public boolean updateCaseInput(Case case1);
	public void inputOfficerDetails();
	public void inputLawAgencyDetails();
	public Case createCaseInput(CrimeAnalysisServiceImpl processor,PrintDetailsImpl ouput);
	int inputSuspectDetails() throws Exception;
	boolean genderCheck(String gender);
	int inputVictimDetails() throws Exception;
	String getStatus() throws IllegalUserInputException;


}
