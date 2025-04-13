package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
//import java.util.Date;
import java.util.stream.Collectors;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Case;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Officer;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalUserInputException;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IncidentNotFoundException;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.main.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.util.DBConnUtil;

public class CrimeAnalysisServiceImpl implements CrimeAnalysisService {
	 Scanner scanner = new Scanner(System.in);
	
	    public static final String ANSI_RESET = "\u001B[0m";
	    public static final String ANSI_RED = "\u001B[31m";
	    public static final String ANSI_GREEN = "\u001B[32m";
	    public static final String ANSI_BLUE = "\u001B[34m";
    @Override
    public boolean createIncident(Incident incident) {
        if (incident == null)
            return false;
        String sqlQuery = "insert into incidents (incident_type, incident_date, location, description, status, victim_id, suspect_id) values  (?,?,?,?,?,?,?)";

        try (Connection conn = DBConnUtil.getConn();
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            
            //Set values to the prepared statement.
            pstmt.setString(1, incident.getIncidentType());
            pstmt.setDate(2, incident.getIncidentDate());
            pstmt.setString(3, incident.getLocation());
            pstmt.setString(4, incident.getDescription());
            pstmt.setString(5, incident.getStatus());
            pstmt.setInt(6, incident.getVictimId());
            pstmt.setInt(7, incident.getSuspectId());

            pstmt.executeUpdate(); // Execute the insert

            ResultSet generatedKeys = pstmt.getGeneratedKeys(); // Get the generated keys
            if (generatedKeys.next()) {
                incident.setIncidentId(generatedKeys.getInt(1)); // Retrieve the auto-generated ID and set it to incident object.
            }

            System.out.println("\n"+ANSI_BLUE+"You can view Incident using this System Generated Incident ID : "+incident.getIncidentId()+""+ANSI_RESET);
            return true; // Return true to indicate success

        }
        catch (SQLException e) {
			// TODO: handle exception
        	System.err.println("There is a mismatch in the ID You have Entered");
		}
        catch (Exception e) {
            System.out.println("Joseph its found");
            e.printStackTrace();
            
        }
        return false;
    }

    
//    PreparedStatement caseStmt = conn.prepareStatement(insertCaseSQL, Statement.RETURN_GENERATED_KEYS);
//    PreparedStatement linkStmt = conn.prepareStatement(linkSQL)) {
//
//   caseStmt.setString(1, caseTitle);
//   caseStmt.setString(2, caseDescription);
//   caseStmt.setDate(3, currentDate);
//   caseStmt.setString(4, "Open");
//
//   int affectedRows = caseStmt.executeUpdate();
//
//   if (affectedRows == 0) {
//       throw new SQLException("Creating case failed, no rows affected.");
//   }
//
//   ResultSet generatedKeys = caseStmt.getGeneratedKeys();
//   if (generatedKeys.next()) {
//       int caseId = generatedKeys.getInt(1);


    @Override
    public boolean updateIncidentStatus(String status, int incidentId)  {
        String sqlQuery = "update incidents set status = ? where incident_id = ?";
        
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, incidentId);
            System.out.println("Update successfully");
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
            e.printStackTrace();
            
        }
    	
    	return false;
    }

    @Override
    public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        String sqlQuery = "select * from incidents where incident_date between ? and ?";
        List<Incident> incidents = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConn();
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            pstmt.setDate(1,  startDate);
            pstmt.setDate(2, endDate);

            ResultSet rs = pstmt.executeQuery();
//            System.out.println("Entered method and cursore"+rs.isBeforeFirst());
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Incident> searchIncidents(String criteria) {
    	
    	
        String sqlQuery = "SELECT * FROM incidents WHERE incident_type LIKE ? OR location LIKE ? OR status LIKE ? or incident_id=?";
        List<Incident> incidents = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

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
            e.printStackTrace();
        }
        return null;
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
                + "join evidences e on e.incident_id=i.incident_id\r\n"
                + "join officers o on o.officer_id= 1\r\n"
                + "join law_agencies l on l.agency_id=o.agency_id where i.incident_id=? ;";

        String sqlQuery2 = "insert into reports (report_id,incident_id,reporting_officer_id, report_date, report_details, status) values (?,?,?,?,?,?)";

        String sqlQuery3="select * from reports where report_id=?";
        Report report = new Report();
        
        System.out.println("Incident ID : " + incident.getIncidentId());
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlQuery1);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlQuery2);
        	 PreparedStatement pstmt3 = conn.prepareStatement(sqlQuery3)) {
        	
        	System.out.println("Enter Report ID : ");
            int reportId = scanner.nextInt();
            scanner.nextLine();
            pstmt3.setInt(1, reportId);
            ResultSet existingResultSet= pstmt3.executeQuery();
            if(existingResultSet.isBeforeFirst()) {
//            	String reportDetails = extractReportDetailsFromResultSet(existingResultSet);

                 // Create Report object only after successfully fetching details
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
            int reportingOfficerId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the status (Draft or Finalized) : ");
            String status = scanner.nextLine();
            
            if(!(status.equalsIgnoreCase("Draft")||status.equalsIgnoreCase("Draft"))) {
            	throw new IllegalUserInputException(status+" is not accepted as Status for Report\nTry Again");
            }
            pstmt1.setInt(1, incident.getIncidentId());
            ResultSet rs = pstmt1.executeQuery();
            
            if(!rs.isBeforeFirst()) {
            	throw new IncidentNotFoundException("Incident with "+incident.getIncidentId()+" not Found \nTry Again");
            }
            String reportDetails = extractReportDetailsFromResultSet(rs);

            report = new Report(); // Create Report object only after successfully fetching details
            report.setReportId(reportId);
            report.setIncidentId(incident.getIncidentId());
            report.setReportingOfficerId(reportingOfficerId);
            report.setReportDate(new Date(System.currentTimeMillis()));
            report.setReportDetails(reportDetails);
            report.setStatus(status);



            pstmt2.setInt(1, report.getReportId());
            pstmt2.setInt(2, report.getIncidentId());
            pstmt2.setInt(3, report.getReportingOfficerId());
            
            pstmt2.setDate(4, report.getReportDate()); // Convert LocalDate to String for SQL
            pstmt2.setString(5, report.getReportDetails());
            pstmt2.setString(6, report.getStatus());

            pstmt2.executeUpdate();

            return report;

        }
        catch (IllegalUserInputException e) {
           System.err.println(e.getLocalizedMessage());
             // Return null if any SQL exception occurs
        } 
        catch (SQLException e) {
            System.err.println("Officer ID Does not Exist\nTry Again");
             // Return null if any SQL exception occurs
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
//			return null;
		} 
        catch (IncidentNotFoundException e) {
			// TODO: handle exception
        	System.err.println(e.getLocalizedMessage());
		}
        catch (Exception e) {
			// TODO: handle exception
        	System.err.println("Improper \nTry again");
		}
        return null;
    }
    private Incident extractIncidentFromResultSet(ResultSet rs) throws SQLException {
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
    private String extractReportDetailsFromResultSet(ResultSet rs) throws SQLException {
    	
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
    		reportDetailsBuilder.append("  Description: ").append(rs.getString("description")).append("\n");
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
        String insertCaseSQL = "INSERT INTO cases (case_title, case_description,created_at, status) VALUES (?, ?, ?, ?)";
        String linkSQL = "INSERT INTO case_incidents (case_id, incident_id) VALUES (?, ?)";
        Date currentDate=new Date(System.currentTimeMillis());
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement caseStmt = conn.prepareStatement(insertCaseSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement linkStmt = conn.prepareStatement(linkSQL)) {

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
        	System.out.println("Exception at Create cases Joseph");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Case getCaseDetails(int caseId) {
        String caseSQL = "SELECT * FROM cases WHERE case_id = ?";
        String incidentsSQL = "SELECT i.* FROM incidents i JOIN case_incidents ci ON i.incident_id = ci.incident_id WHERE ci.case_id = ?";

        try (Connection conn =  DBConnUtil.getConn();
             PreparedStatement caseStmt = conn.prepareStatement(caseSQL);
             PreparedStatement incStmt = conn.prepareStatement(incidentsSQL)) {

            caseStmt.setInt(1, caseId);
            ResultSet caseRs = caseStmt.executeQuery();

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateCaseDetails(Case caseObj) {
        String updateSQL = "UPDATE cases SET case_title = ?, case_description = ?, status = ? WHERE case_id = ?";
        
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, caseObj.getCaseTitle());
            stmt.setString(2, caseObj.getCaseDescription());
            stmt.setString(3, caseObj.getStatus());
            stmt.setInt(4, caseObj.getCaseId());

            int rows = stmt.executeUpdate();
            
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    
    @Override
    public List<Case> getAllCases() {
        List<Case> cases = new ArrayList<>();
        String sql = "SELECT * FROM cases";
        String incidentSQL = "SELECT i.* FROM incidents i " +
                             "JOIN case_incidents ci ON i.incident_id = ci.incident_id " +
                             "WHERE ci.case_id = ?";

        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement incStmt = conn.prepareStatement(incidentSQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Case caseObj = new Case();
                caseObj.setCaseId(rs.getInt("case_id"));
                caseObj.setCaseTitle(rs.getString("case_title"));
                caseObj.setCaseDescription(rs.getString("case_description"));
                caseObj.setStatus(rs.getString("status"));
                caseObj.setCreatedAt(rs.getDate("created_at")); // changed from getTimestamp

                // Fetch related incidents
                incStmt.setInt(1, caseObj.getCaseId());
                ResultSet incRs = incStmt.executeQuery();
                List<Incident> incidents = new ArrayList<>();

                while (incRs.next()) {
                    Incident incident = new Incident();
                    incident.setIncidentId(incRs.getInt("incident_id"));
                    incident.setIncidentType(incRs.getString("incident_type"));
                    incident.setIncidentDate(incRs.getDate("incident_date")); // Use getDate if date type
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
    public void addIncident(List<Incident> incidents, GetInput input, PrintDetails output) {
        secondaryLoop:
        while (true) {
            try {
                System.out.println("\n_____ Adding Incidents _____");
                System.out.println("1. Add new Incident to the Case\n2. Add existing Incident to the Case\n>>");

                int choice = 0;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        incidents.add(input.inputCreateIncident());
                        break;

                    case 2:
                        System.out.println("Entered Search Inputs");
                        List<Integer> incidentId = new ArrayList<>();
                        List<Incident> searchedList = searchIncidents(input.searchIncidentsInput());

                        if (searchedList == null || searchedList.isEmpty()) {
//                        	System.out.println("No incidents found for the given search input. this 626");
//                            throw new IncidentNotFoundException();
                        	break secondaryLoop;
                        }

                        
                        output.printIncdients(searchedList);
                        System.out.println("Enter the Incident ID from the above List of Incidents to add to the case:");

                        while (true) {
                            try {
                                System.out.print("Enter Incident ID: ");
                                int id = Integer.parseInt(scanner.nextLine());
                                incidentId.add(id);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid Incident ID.");
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
                        System.out.println("All the searched incidents have been added successfully.");
                        break;

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
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); // Optional: helpful for debugging
            }
        }
    }

    public List<Incident> filterIncidents(List<Integer> filterIDs, List<Incident> incidents) {
      
        if (filterIDs == null || filterIDs.isEmpty() || incidents == null || incidents.isEmpty()) {
            return new ArrayList<>(); 
        }
        
        List<Incident> filteredList = incidents.stream()
                .filter(incident -> filterIDs.contains(incident.getIncidentId()))
                .collect(Collectors.toList());
		 System.out.println("Entered Filter Mehod Jospeh \nsize of filter : "+filteredList.size());
        return filteredList;
    }
}

	

