package com.hexawareTraining.crimeAnalysisAndReportingSystem.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
//import java.util.Date;
import java.util.stream.Collectors;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.dao.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.*;
//import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.util.*;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

public class CrimeAnalysisServiceImpl implements CrimeAnalysisService {
	 
	
	    
	    
    @Override
    public boolean createIncident(Incident incident) {
        if (incident == null)
            return false;
        String sqlQuery = "insert into incidents (incident_type, incident_date, location, description, status, victim_id, suspect_id) values  (?,?,?,?,?,?,?)";
		Connection conn = DBConnUtil.getConn();
		
        try (
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setString(1, incident.getIncidentType());
            pstmt.setDate(2, incident.getIncidentDate());
            pstmt.setString(3, incident.getLocation());
            pstmt.setString(4, incident.getDescription());
            pstmt.setString(5, incident.getStatus());
            pstmt.setInt(6, incident.getVictimId());
            pstmt.setInt(7, incident.getSuspectId());

            pstmt.executeUpdate(); 

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                incident.setIncidentId(generatedKeys.getInt(1)); 
            }

            System.out.println("\n"+"\u001B[32m"+"You can view Incident using this System Generated Incident ID : "+incident.getIncidentId()+"\u001B[0m");
            return true; 

        }
        catch (SQLException e) {
			// TODO: handle exception
        	System.err.println("ID Entered is not Found\nTryAgain");
		}
        catch (Exception e) {
            System.out.println("Joseph its found");
            e.printStackTrace();
            
        }
        return false;
    }
    
    @Override
    public int addSuspect(Suspect suspect) {
        String sql = "insert into  suspects (first_name, last_name, date_of_birth, gender, contact_information) values (?, ?, ?, ?, ?)";
        Connection conn = DBConnUtil.getConn();
        
        try (
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, suspect.getFirstName());
            stmt.setString(2, suspect.getLastName());
            stmt.setDate(3, suspect.getDateOfBirth());
            stmt.setString(4, suspect.getGender());
            stmt.setString(5, suspect.getContactInformation());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error adding suspect\nTry agin");
        }
        return -1;
    }
    
    
    @Override
    public int addVictim(Victim victim) {
        String sql = "insert into victims (first_name, last_name, date_of_birth, gender, contact_information) values (?, ?, ?, ?, ?)";
        Connection conn = DBConnUtil.getConn();
        try (
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, victim.getFirstName());
            stmt.setString(2, victim.getLastName());
            stmt.setDate(3, victim.getDateOfBirth());
            stmt.setString(4, victim.getGender());
            stmt.setString(5, victim.getContactInformation());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error adding victim\nTry again");
        }
        return -1;
    }
    
    @Override
    public int addEvidence(Evidence evidence) {
        String sql = "insert into evidences (description, location_found, incident_id) values (?, ?, ?)";
        Connection conn =DBConnUtil.getConn();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evidence.getDescription());
            stmt.setString(2, evidence.getLocationFound());
            stmt.setInt(3, evidence.getIncidentId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Incident ID Not found\nTry again");
        }
        return -1;
    }



    @Override
    public boolean updateIncidentStatus(String status, int incidentId)  {
        String sqlQuery = "update incidents set status = ? where incident_id = ?";
        Connection conn = DBConnUtil.getConn();
        try (
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, incidentId);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected==0) {
            	throw new IncidentNotFoundException("Incident with ID : "+incidentId+" Not Found to Update");
            }
            return  true; 

        }
        catch (IncidentNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            
        }
        
        catch (Exception e) {
            System.err.println("Error Updating Incident\nTry agin");
            
        }
    	
    	return false;
    }

    @Override
    public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        String sqlQuery = "select * from incidents where incident_date between ? and ?";
        List<Incident> incidents = new ArrayList<>();
		Connection conn = DBConnUtil.getConn();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            pstmt.setDate(1,  startDate);
            pstmt.setDate(2, endDate);

            ResultSet rs = pstmt.executeQuery();
            
            if(!rs.isBeforeFirst()) {
            	throw new IncidentNotFoundException("There are no Incidents found in between "+startDate+" to" +endDate);
            }
            while (rs.next()) {
                Incident incident = extractIncidentFromResultSet(rs);
                incidents.add(incident);
            }
            return incidents;
        }catch (IncidentNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.err.println("Error retrieving incident in the given range");
        }
        return null;
    }

    @Override
    public List<Incident> searchIncidents(String criteria) {
    	
    	
        String sqlQuery = "select * from incidents WHERE incident_type like ? or location like ? or status like ? or incident_id=?";
        
        List<Incident> incidents = new ArrayList<>();
		Connection conn = DBConnUtil.getConn();
		             
        try (PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            String keyword = "%" +criteria+ "%";
            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);
            pstmt.setString(4, criteria);

            ResultSet rs = pstmt.executeQuery();
            if(!rs.isBeforeFirst()) {
            	throw new IncidentNotFoundException("There are no Incidents found for the given Input  "+(criteria==null?"":": "+criteria));
            }
            while (rs.next()) {
                Incident incident = extractIncidentFromResultSet(rs);
                incidents.add(incident);
            }
            return incidents; 
        }
        catch (IncidentNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.err.println("Error Searching Incidents\n");
        }
        return null;
        }
    
    @Override
    public int createOfficer(Officer officer) {
        String sql = "insert into  officers (first_name, last_name, badge_number, officer_rank, contact_information, agency_id) values (?, ?, ?, ?, ?, ?)";
        
        Connection conn =DBConnUtil.getConn();
        try (
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            

            stmt.setString(1, officer.getFirstName());
            stmt.setString(2, officer.getLastName());
            stmt.setString(3, officer.getBadgeNumber());
            stmt.setInt(4, officer.getOfficerRank());
            stmt.setString(5, officer.getContactInformation());
            stmt.setInt(6, officer.getAgencyId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        }  catch (Exception e) {
            System.err.println("Agency ID not found\nTry with other ID ");
        }
        return -1;
    }

    @Override
    public int createLawAgency(LawAgency agency) {
        String sql = "insert into law_agencies (agency_name, jurisdiction, contact_information, officer_id) values (?, ?, ?, ?)";
        Connection conn =DBConnUtil.getConn();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            
            stmt.setString(1, agency.getAgencyName());
            stmt.setString(2, agency.getJurisdiction());
            stmt.setString(3, agency.getContactInformation());
            stmt.setInt(4, agency.getOfficerId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        }  catch (Exception e) {
        	System.err.println("Officer ID not found");
        }
        return -1;
    }


    @Override
    public Report generateIncidentReport(Incident incident) {
    	
    	
        String sqlQuery1 = "select i.incident_id,i.incident_type,i.incident_date,i.location,i.description,i.status,\r\n"
                + "		v.victim_id, concat(v.first_name,' ',v.last_name) victim_name, v.date_of_birth, v.gender,v.contact_information,\r\n"
                + "		s.suspect_id,concat(s.first_name,' ',s.last_name) suspect_name,s.date_of_birth, s.gender,s.contact_information,\r\n"
                + "        e.evidence_id,e.description,e.location_found,\r\n"
                + "        o.officer_id,concat(o.first_name,' ',o.last_name) officer_name,o.badge_number,o.officer_rank,o.contact_information,\r\n"
                + "		l.agency_id, l.Agency_name,l.jurisdiction,l.contact_information\r\n"
                + "from victims v\r\n"
                + "join incidents i on v.victim_id=i.victim_id\r\n"
                + "join suspects s on s.suspect_id=i.suspect_id\r\n"
                + "left join evidences e on e.incident_id=i.incident_id\r\n"
                + "join officers o on o.officer_id=? \r\n"
                + "join law_agencies l on l.agency_id=o.agency_id where i.incident_id=? ;";

        String sqlQuery2 = "insert into reports (report_id,incident_id,reporting_officer_id, report_date, report_details, status) values (?,?,?,?,?,?)";

        String sqlQuery3="select * from reports where report_id=?";
        
        Scanner scanner = new Scanner(System.in);
        Report report = new Report();

        Connection conn = DBConnUtil.getConn();
             
        try (PreparedStatement pstmt1 = conn.prepareStatement(sqlQuery1);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlQuery2);
        	 PreparedStatement pstmt3 = conn.prepareStatement(sqlQuery3)) {
        	
        	System.out.println("Enter Report ID : ");
            int reportId = Integer.parseInt(scanner.nextLine());
//            scanner.nextLine();
            pstmt3.setInt(1, reportId);
            ResultSet existingResultSet= pstmt3.executeQuery();
            if(existingResultSet.isBeforeFirst()) {

            	System.out.println("The Report Already exist\nWould You like to view Report (yes/no)\n>>");
            	String viewChoice=scanner.nextLine();
            	if(viewChoice.equalsIgnoreCase("no")) {
            		System.out.println("Exiting Create Report Window");
            		return null;
            	}
            	if(existingResultSet.next()) {
                report.setReportId(reportId);
                report.setIncidentId(incident.getIncidentId());
                report.setReportingOfficerId(existingResultSet.getInt("reporting_officer_id"));
                report.setReportDate(existingResultSet.getDate("report_date"));
                report.setReportDetails(existingResultSet.getString("report_details"));
                report.setStatus(existingResultSet.getString("status"));
            	}
            	
            	return report;
            	
            } 
            System.out.println("Enter Reporting officer ID : ");
            int reportingOfficerId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the status (Draft or Finalized) : ");
            String status = scanner.nextLine();
            
            if(!(status.equalsIgnoreCase("Draft")||status.equalsIgnoreCase("Draft"))) {
            	throw new IllegalUserInputException(status+" is not accepted as Status for Report\nTry Again");
            }
            pstmt1.setInt(1, reportingOfficerId);
            pstmt1.setInt(2, incident.getIncidentId());
            ResultSet rs = pstmt1.executeQuery();
            
            if(!rs.isBeforeFirst()) {
            	throw new IncidentNotFoundException("Incident with "+incident.getIncidentId()+" not Found \nTry Again");
            }
            String reportDetails = extractReportDetailsFromResultSet(rs);

            report = new Report();
            report.setReportId(reportId);
            report.setIncidentId(incident.getIncidentId());
            report.setReportingOfficerId(reportingOfficerId);
            report.setReportDate(new Date(System.currentTimeMillis()));
            report.setReportDetails(reportDetails);
            report.setStatus(status);



            pstmt2.setInt(1, report.getReportId());
            pstmt2.setInt(2, report.getIncidentId());
            pstmt2.setInt(3, report.getReportingOfficerId());
            
            pstmt2.setDate(4, report.getReportDate()); 
            pstmt2.setString(5, report.getReportDetails());
            pstmt2.setString(6, report.getStatus());

            pstmt2.executeUpdate();

            return report;

        }
        catch (IllegalUserInputException e) {
           System.err.println(e.getLocalizedMessage());
            
        } 
        catch (SQLException e) {
            System.err.println("Officer ID Does not Exist\nTry Again");
           
        } 
        catch (IncidentNotFoundException e) {
			// TODO: handle exception
        	System.err.println(e.getLocalizedMessage());
		}
        catch (Exception e) {
			// TODO: handle exception
        	System.err.println("Improper input \nTry again");
		}
        return null;
    }
    
    @Override
    public Incident extractIncidentFromResultSet(ResultSet rs) throws SQLException {
        Incident incident = new Incident();
        incident.setIncidentId(rs.getInt("incident_id"));
        incident.setIncidentType(rs.getString("incident_type"));
        incident.setIncidentDate(rs.getDate("incident_date"));
        incident.setLocation(rs.getString("location"));
        incident.setDescription(rs.getString("description"));
        incident.setStatus(rs.getString("status"));
        incident.setVictimId(rs.getInt("victim_id"));
        incident.setSuspectId(rs.getInt("suspect_id"));
        return incident;
    }
    
    @Override
    public String extractReportDetailsFromResultSet(ResultSet rs) throws SQLException {
    	
    		StringBuilder reportDetailsBuilder = new StringBuilder();
    		
    		if(rs.next()) {

    		reportDetailsBuilder.append("Incidents Details :\n");
    		reportDetailsBuilder.append("  Incident ID: ").append(rs.getInt("incident_id")).append("\n");
    		reportDetailsBuilder.append("  Incident Type: ").append(rs.getString("incident_type")).append("\n");
    		reportDetailsBuilder.append("  Incident Date: ").append(rs.getDate("incident_date")).append("\n");
    		reportDetailsBuilder.append("  Location: ").append(rs.getString("location")).append("\n");
    		reportDetailsBuilder.append("  Description: ").append(rs.getString("description")).append("\n");
    		reportDetailsBuilder.append("  Status: ").append(rs.getString("status")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		reportDetailsBuilder.append("\nVictims Details :\n");
    		reportDetailsBuilder.append("  Victim ID: ").append(rs.getInt("victim_id")).append("\n");
    		reportDetailsBuilder.append("  Victim Name: ").append(rs.getString("victim_name")).append("\n");
    		reportDetailsBuilder.append("  Date of Birth: ").append(rs.getDate("date_of_birth")).append("\n");
    		reportDetailsBuilder.append("  Gender: ").append(rs.getString("gender")).append("\n");
    		reportDetailsBuilder.append("  Contact Information: ").append(rs.getString("contact_information")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		reportDetailsBuilder.append("\nSuspects Details :\n");
    		reportDetailsBuilder.append("  Suspect ID: ").append(rs.getInt("suspect_id")).append("\n");
    		reportDetailsBuilder.append("  Suspect Name: ").append(rs.getString("suspect_name")).append("\n");
    		reportDetailsBuilder.append("  Date of Birth: ").append(rs.getDate("date_of_birth")).append("\n");
    		reportDetailsBuilder.append("  Gender: ").append(rs.getString("gender")).append("\n");
    		reportDetailsBuilder.append("  Contact Information: ").append(rs.getString("contact_information")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		reportDetailsBuilder.append("\nEvidences Details :\n");
    		reportDetailsBuilder.append("  Evidence ID: ").append(rs.getInt("evidence_id")).append("\n");
    		reportDetailsBuilder.append("  Description: ").append(rs.getString("e.description")).append("\n");
    		reportDetailsBuilder.append("  Location Found: ").append(rs.getString("location_found")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		reportDetailsBuilder.append("\nOfficers Details :\n");
    		reportDetailsBuilder.append("  Officer ID: ").append(rs.getInt("officer_id")).append("\n");
    		reportDetailsBuilder.append("  Officer Name: ").append(rs.getString("officer_name")).append("\n");
    		reportDetailsBuilder.append("  Badge Number: ").append(rs.getString("badge_number")).append("\n");
    		reportDetailsBuilder.append("  Officer Rank: ").append(rs.getString("officer_rank")).append("\n");
    		reportDetailsBuilder.append("  Contact Information: ").append(rs.getString("contact_information")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		reportDetailsBuilder.append("\nLaw Agency Details :\n");
    		reportDetailsBuilder.append("  Agency ID: ").append(rs.getInt("agency_id")).append("\n");
    		reportDetailsBuilder.append("  Agency Name: ").append(rs.getString("Agency_name")).append("\n");
    		reportDetailsBuilder.append("  Jurisdiction: ").append(rs.getString("jurisdiction")).append("\n");
    		reportDetailsBuilder.append("  Contact Information: ").append(rs.getString("contact_information")).append("\n");
    		reportDetailsBuilder.append("-------------------------------------------------------------------");
    		}
    		return reportDetailsBuilder.toString();

    		
    	
    	}
    
    @Override
    public Case createCase(String caseTitle, String caseDescription, List<Incident> incidents) {
        String sqlQuery1 = "insert into cases (case_title, case_description,created_at, status) values (?, ?, ?, ?)";
        String sqlQuery2 = "insert into case_incidents (case_id, incident_id) values (?, ?)";
        Date currentDate=new Date(System.currentTimeMillis());
        Connection conn = DBConnUtil.getConn();
             try (PreparedStatement caseStmt = conn.prepareStatement(sqlQuery1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement linkStmt = conn.prepareStatement(sqlQuery2)) {

            caseStmt.setString(1, caseTitle);
            caseStmt.setString(2, caseDescription);
            caseStmt.setDate(3, currentDate);
            caseStmt.setString(4, "Open");

            int affectedRows = caseStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating case failed, no rows affected.");
            }

            ResultSet generatedKeys = caseStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int caseId = generatedKeys.getInt(1);

                for (Incident incident : incidents) {
                    linkStmt.setInt(1, caseId);
                    linkStmt.setInt(2, incident.getIncidentId());
                    linkStmt.addBatch();
                }
                linkStmt.executeBatch();

                Case createdCase = new Case();
                createdCase.setCaseId(caseId);
                createdCase.setCaseTitle(caseTitle);
                createdCase.setCaseDescription(caseDescription);
                createdCase.setCreatedAt(currentDate);
                createdCase.setStatus("Open");
                createdCase.setIncidents(incidents);

                return createdCase;
            }

        } catch (Exception e) {
        	System.err.println("Failed to create Case\nTry Again");
          
        }

        return null;
    }

    @Override
    public Case getCaseDetails(int caseId) {
        String caseSQL = "select * from cases where case_id = ?";
        String incidentsSQL = "select i.* from incidents i join case_incidents ci on i.incident_id = ci.incident_id where ci.case_id = ?";

        Connection conn =  DBConnUtil.getConn();
             try (PreparedStatement caseStmt = conn.prepareStatement(caseSQL);
             PreparedStatement incStmt = conn.prepareStatement(incidentsSQL)) {

            caseStmt.setInt(1, caseId);
            ResultSet caseRs = caseStmt.executeQuery();
            if(!caseRs.isBeforeFirst()) {
            	throw new CaseNotFoundException("Case with "+caseId+" not Found \nTry Again");
            }
            if (caseRs.next()) {
                Case caseObj = new Case();
                caseObj.setCaseId(caseRs.getInt("case_id"));
                caseObj.setCaseTitle(caseRs.getString("case_title"));
                caseObj.setCaseDescription(caseRs.getString("case_description"));
                caseObj.setStatus(caseRs.getString("status"));
                caseObj.setCreatedAt(caseRs.getDate("created_at"));

                incStmt.setInt(1, caseId);
                ResultSet incRs = incStmt.executeQuery();

                List<Incident> incidents = new ArrayList<>();
                while (incRs.next()) {
                    Incident incident = extractIncidentFromResultSet(incRs);
                    incidents.add(incident);
                }

                caseObj.setIncidents(incidents);
                return caseObj;
            }

        }
        catch (CaseNotFoundException e) {
			// TODO: handle exception
        	System.err.println(e.getLocalizedMessage());
		}
        catch (Exception e) {
           System.err.println("Error retrieving Case by ID\nTry again");
        }

        return null;
    }

    @Override
    public boolean updateCaseDetails(Case caseObj) {
        String updateSQL = "update cases set case_title = ?, case_description = ?, status = ? where case_id = ?";
        String updateIncidents = "insert ignore into case_incidents (case_id, incident_id, added_at) values (?, ?, ?)";

        Connection conn = DBConnUtil.getConn();
             try (PreparedStatement stmt = conn.prepareStatement(updateSQL);
            	  PreparedStatement insertStmt = conn.prepareStatement(updateIncidents)) {

            stmt.setString(1, caseObj.getCaseTitle());
            stmt.setString(2, caseObj.getCaseDescription());
            stmt.setString(3, caseObj.getStatus());
            stmt.setInt(4, caseObj.getCaseId());

            int row = stmt.executeUpdate();
            
            for (Incident incident : caseObj.getIncidents()) {
                insertStmt.setInt(1, caseObj.getCaseId());
                insertStmt.setInt(2, incident.getIncidentId());
                insertStmt.setDate(3,new Date(System.currentTimeMillis()));
                insertStmt.addBatch();
            }
            int rows[]= insertStmt.executeBatch();
            
            return row > 0&& rows.length>0;

        } catch (Exception e) {
            System.err.println("Case ID not found\nTry Again");
            e.printStackTrace();
        }

        return false;
    }

    
    @Override
    public List<Case> getAllCases() {
        List<Case> cases = new LinkedList<>();
        String sql = "select * from cases";
        String incidentSQL = "select i.* from incidents i " +
                             "join case_incidents ci on i.incident_id = ci.incident_id " +
                             "where ci.case_id = ?";

        Connection conn = DBConnUtil.getConn();
             try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement incStmt = conn.prepareStatement(incidentSQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Case caseObj = new Case();
                caseObj.setCaseId(rs.getInt("case_id"));
                caseObj.setCaseTitle(rs.getString("case_title"));
                caseObj.setCaseDescription(rs.getString("case_description"));
                caseObj.setStatus(rs.getString("status"));
                caseObj.setCreatedAt(rs.getDate("created_at")); 

                incStmt.setInt(1, caseObj.getCaseId());
                ResultSet incRs = incStmt.executeQuery();
                List<Incident> incidents = new ArrayList<>();

                while (incRs.next()) {
                    Incident incident = new Incident();
                    incident.setIncidentId(incRs.getInt("incident_id"));
                    incident.setIncidentType(incRs.getString("incident_type"));
                    incident.setIncidentDate(incRs.getDate("incident_date")); 
                    incident.setLocation(incRs.getString("location"));
                    incident.setDescription(incRs.getString("description"));
                    incident.setStatus(incRs.getString("status"));
                    incident.setVictimId(incRs.getInt("victim_id"));
                    incident.setSuspectId(incRs.getInt("suspect_id"));
                    incidents.add(incident);
                }

                caseObj.setIncidents(incidents);
                cases.add(caseObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cases;
    }

    @Override
    public void addIncident(List<Incident> incidents, GetInputImpl input, PrintDetailsImpl output) {
    	Scanner scanner = new Scanner(System.in);
        secondaryLoop:
        while (true) {
            try {
                System.out.println("\n_____ Adding Incidents _____");
                System.out.println("1. Add new Incident to the Case\n2. Add existing Incident to the Case\n3. Exit>>");

                int choice = 0;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                    	Incident incident=input.inputCreateIncident();
                    	createIncident(incident);
                    	
                        incidents.add(incident);
                        break;

                    case 2:
                        System.out.println("Entered Search Inputs");
                        Set<Integer> incidentId = new HashSet<>();
                        String criteria= input.searchIncidentsInput();
                        if(criteria==null) continue;
                        List<Incident> searchedList = searchIncidents(criteria);

                        if (searchedList == null || searchedList.isEmpty()) {
                        	System.err.println("No incidents found for the given search input.");

                        	break secondaryLoop;
                        }

                        System.out.println("_____________________Incidents Retrieved from the Search_____________________");
                        output.printIncdients(searchedList);
                        System.out.println("\nEnter the Incident ID from the above List of Incidents to add to the case:");

                        while (true) {
                            try {
                                System.out.print("Enter Incident ID: ");
                                int id = Integer.parseInt(scanner.nextLine());
                               
                                 if(!incidentId.add(id)) {
                                	 System.err.println("ID already Selected");
                                 }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid input. Please enter a valid Incident ID.");
                                continue;
                            }

                            System.out.print("Add Another ID (yes/no): ");
                            String idOption = scanner.nextLine();
                            if (idOption.equalsIgnoreCase("no")) break;
                        }

                        searchedList = filterIncidents(incidentId, searchedList);
                        if (searchedList == null || searchedList.isEmpty()) {
                            throw new IncidentNotFoundException("No incidents matched the given IDs.");
                        }

                        
                        output.printIncdients(searchedList);
                        System.out.println("Are you sure you want to add the above incidents to the case? (yes/no)");
                        String addOption = scanner.nextLine();

                        if (addOption.equalsIgnoreCase("no")) {
                            System.out.println("The searched incidents have not been added.");
                            break;
                        }

                        incidents.addAll(searchedList);
                        System.out.println("\u001B[32m"+"All the searched incidents have been added successfully."+"\u001B[0m");
                        break;
                    
                    case 3:
                    	System.out.println("Exiting Add Incidents....");
                    	return;

                    default:
                        System.out.println("Exiting Create Case window....");
                        break secondaryLoop;
                }

                System.out.print("Do you wish to continue (yes/no): ");
                String option = scanner.nextLine();
                if (!option.equalsIgnoreCase("yes")) break secondaryLoop;

            } catch (IncidentNotFoundException e) {
                System.err.println( e.getLocalizedMessage());
            } catch (Exception e) {
                System.out.println("Improper Input\nTry Again");
            }
        }
    }

    
    @Override
    public List<Incident> filterIncidents(Set<Integer> filterIDs, List<Incident> incidents) {
      
        if (filterIDs == null || filterIDs.isEmpty() || incidents == null || incidents.isEmpty()) {
            return new ArrayList<>(); 
        }
  
        List<Incident> filteredList = incidents.stream()
                .filter(incident -> filterIDs.contains(incident.getIncidentId()))
                .collect(Collectors.toList());
        return filteredList;
    }
}
