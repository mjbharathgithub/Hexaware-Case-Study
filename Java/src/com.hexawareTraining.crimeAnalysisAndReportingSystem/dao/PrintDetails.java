package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.util.List;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;

public interface PrintDetails {
	void printIncidentReport(Report report);
	void printIncdients(List<Incident> incidents);
	void printCase(Case case1);
}
