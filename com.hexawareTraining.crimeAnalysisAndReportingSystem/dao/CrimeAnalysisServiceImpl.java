package com.hexawareTraining.crimeAnalysisAndReportingSystem.dao;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Report;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.main.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.util.DBConnUtil;

public class CrimeAnalysisServiceImpl implements CrimeAnalysisService {
	
	

    @Override
    public boolean createIncident(Incident incident) {
        String sql = "insert into incidents (incident_type, incident_date, location, description, status, victim_id, suspect_id, incident_id) values  (?,?,?,?,?,?,?,?)";
        
       
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, incident.getIncidentType());
            pstmt.setDate(2, (java.sql.Date) incident.getIncidentDate());
            pstmt.setString(3, incident.getLocation());
            pstmt.setString(4, incident.getDescription());
            pstmt.setString(5, incident.getStatus());
            pstmt.setInt(6, incident.getVictimId());
            pstmt.setInt(7, incident.getSuspectId());
            pstmt.setInt(5, incident.getIncidentId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIncidentStatus(String status, int incidentId)  {
        String sql = "update incidents set status = ? where incident_id = ?";
        
        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, incidentId);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    	
    	
    }

    @Override
    public List<Incident> getIncidentsInDateRange(Date startDate, Date endDate) {
        String sql = "select * from incidents where date(incident_date) between ? and ?";
        List<Incident> incidents = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConn();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, (java.sql.Date) startDate);
            pstmt.setDate(2, (java.sql.Date) endDate);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Incident incident = extractIncidentFromResultSet(rs);
                incidents.add(incident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incidents;
    }

    @Override
    public List<Incident> searchIncidents(String criteria) {
        String sql = "select * from incidents where incident_type like ? or location like ? or status like ?";
        List<Incident> incidents = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String keyword = "%" + criteria + "%";
            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Incident incident = extractIncidentFromResultSet(rs);
                incidents.add(incident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incidents;    }

    @Override
    public Report generateIncidentReport(Incident incident) {
        Report report = new Report();
        report.setIncidentId(incident.getIncidentId());
        report.setReportId(incident.getIncidentId());
        report.setReportingOfficerId(incident.getIncidentId());
        report.setReportDate(new java.sql.Date(System.currentTimeMillis()));
        report.setReportDetails("Report generated for incident type: " + incident.getIncidentType() +
                " occurred at " + incident.getLocation() + " with status: " + incident.getStatus());
        report.setStatus("Draft");

        String sql = "insert into reports (incident_id,report_id,reporting_officer_id, report_date, report_details, status) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnUtil.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, incident.getIncidentId());
            pstmt.setInt(2, incident.getIncidentId());
            pstmt.setInt(3, incident.getIncidentId());
            pstmt.setDate(4, report.getReportDate());
            pstmt.setString(5, report.getReportDetails());
            pstmt.setString(6, report.getStatus());

            pstmt.executeUpdate();
            
            return report;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        
    }

    
    private Incident extractIncidentFromResultSet(ResultSet rs) throws SQLException {
        Incident incident = new Incident();
        incident.setIncidentId(rs.getInt("incident_id"));
        incident.setIncidentType(rs.getString("incident_type"));
        incident.setIncidentDate(rs.getTimestamp("incident_date"));
        incident.setLocation(rs.getString("location"));
        incident.setDescription(rs.getString("description"));
        incident.setStatus(rs.getString("status"));
        incident.setVictimId(rs.getInt("victim_id"));
        incident.setSuspectId(rs.getInt("suspect_id"));
        return incident;
    }
}
