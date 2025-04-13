package com.hexawareTraining.crimeAnalysisAndReportingSystem.entity;

import java.sql.Date;

public class Victim {
	
	private int victimId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String contactInformation;
    
	public Victim(int victimId, String firstName, String lastName, Date dateOfBirth, String gender,
			String contactInformation) {
		super();
		this.victimId = victimId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contactInformation = contactInformation;
	}
	public int getVictimId() {
		return victimId;
	}
	public void setVictimId(int victimId) {
		this.victimId = victimId;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
    
    

}
