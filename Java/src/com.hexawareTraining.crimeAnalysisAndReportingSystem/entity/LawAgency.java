package com.hexawareTraining.crimeAnalysisAndReportingSystem.entity;

public class LawAgency {
    private int agencyId;
    private String agencyName;
    private String jurisdiction;
    private String contactInformation;
    private int officerId;
	public LawAgency(int agencyId, String agencyName, String jurisdiction, String contactInformation, int officerId) {
		super();
		this.agencyId = agencyId;
		this.agencyName = agencyName;
		this.jurisdiction = jurisdiction;
		this.contactInformation = contactInformation;
		this.officerId = officerId;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public int getOfficerId() {
		return officerId;
	}
	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}
    
    
}
