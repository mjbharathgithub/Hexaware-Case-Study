package com.hexawareTraining.crimeAnalysisAndReportingSystem.entity;

public class Officer {
    private int officerId;
    private String firstName;
    private String lastName;
    private String badgeNumber;
    private int officerRank; 
    private String contactInformation;
    private int agencyId;
	public Officer(int officerId, String firstName, String lastName, String badgeNumber, int officerRank,
			String contactInformation, int agencyId) {
		super();
		this.officerId = officerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.badgeNumber = badgeNumber;
		this.officerRank = officerRank;
		this.contactInformation = contactInformation;
		this.agencyId = agencyId;
	}
	public Officer() {
		// TODO Auto-generated constructor stub
	}
	public int getOfficerId() {
		return officerId;
	}
	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBadgeNumber() {
		return badgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		this.badgeNumber = badgeNumber;
	}
	public int getOfficerRank() {
		return officerRank;
	}
	public void setOfficerRank(int officerRank) {
		this.officerRank = officerRank;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
    
    
	
}
