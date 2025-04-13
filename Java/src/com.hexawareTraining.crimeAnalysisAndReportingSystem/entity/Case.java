package com.hexawareTraining.crimeAnalysisAndReportingSystem.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
//: caseId, caseTitle, caseDescription, createdAt, status, and a list of Incident objects.
public class Case {
    private int caseId;
    private String caseTitle;
    private String caseDescription;
    private Date createdAt;
    private String status;
    private List<Incident> incidents;  
   
    

    // Constructors

    public Case() {
    }



	public Case(int caseId, String caseTitle, String caseDescription, Date createdAt, String status,
			List<Incident> incidents) {
		super();
		this.caseId = caseId;
		this.caseTitle = caseTitle;
		this.caseDescription = caseDescription;
		this.createdAt = createdAt;
		this.status = status;
		this.incidents = incidents;
	}



	public int getCaseId() {
		return caseId;
	}



	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}



	public String getCaseTitle() {
		return caseTitle;
	}



	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}



	public String getCaseDescription() {
		return caseDescription;
	}



	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public List<Incident> getIncidents() {
		return incidents;
	}



	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}
    
    
}
