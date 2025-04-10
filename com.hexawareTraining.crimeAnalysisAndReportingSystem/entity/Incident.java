package com.hexawareTraining.crimeAnalysisAndReportingSystem.entity;

import java.util.Date;

public class Incident {
	
	private int incidentId;
	private String incidentType;
	private Date incidentDate;
	private String location;
	private String description;
	private String status;
	private int victimId;
	private int suspectId;
	
	
	public Incident() {
		
	}
  
	public Incident(int incidentId, String incidentType, Date incidentDate,String location,String description,String status,int victimId, int suspectId) {
		super();
		this.incidentId = incidentId;
		this.incidentType = incidentType;
		this.incidentDate = incidentDate;
		this.location = location;
		this.description = description;
		this.status=status;
		this.victimId = victimId;
		this.suspectId = suspectId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	public Date getIncidentDate() {
		return incidentDate;
	}
	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVictimId() {
		return victimId;
	}
	public void setVictimId(int victimId) {
		this.victimId = victimId;
	}
	public int getSuspectId() {
		return suspectId;
	}
	public void setSuspectId(int suspectId) {
		this.suspectId = suspectId;
	}
	
	
	
	

}
