/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lam
 */
public class Degree {

    private String degreeName;
    private int declaredYear;
    private Map<String, List<String>> degree = new HashMap<>();
    private List<String> courseList = new ArrayList<>();
    
    /**
     * Parameterized Constructor.
     * Accepts the degree name and declared to fetch degree requirements
     * 
     * @param name the degree name i.e. CS_BS
     * @param year the year
     */
    public Degree(String name, int year) {
        this.degreeName = name;
        this.declaredYear = year;
        Database dbc = new Database();

        try {
            if (dbc.getError().length() == 0) {
                // get list of req courses
                getReqCourses(dbc.getConn());
                // get prerequisites
                getPrerequisites(dbc.getConn());
                dbc.closeConn();
            }
        } catch (Exception ex) {
            System.out.println("Error in Degree.Degree() " + ex.getMessage());
        }

    }

    /**
     * Get the required courses for this degree
     * 
     * @param conn the database connection
     * @throws SQLException 
     */
    public final void getReqCourses(Connection conn) throws SQLException {
        Statement stmt = null;
        String sql = "SELECT course_id FROM degree_requirement "
                + "INNER JOIN degree ON degree.degree_id=degree_requirement.degree_id "
                + "WHERE degree.degree_name = \"" + this.degreeName
                + "\" AND degree.declared_year = " + this.declaredYear;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                getCourseList().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Error in Degree.getReqCourses()");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new SQLException();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    /**
     * Get the prerequisite courses for each degree requirement.
     * 
     * @param conn the database connection
     * @throws SQLException 
     */
    public final void getPrerequisites(Connection conn) throws SQLException {
        Statement stmt = null;
        for (int i = 0; i < getCourseList().size(); i++) {
            String sql = "SELECT prereq_id FROM degree, prerequisite, degree_requirement "
                    + "WHERE degree_requirement.course_id=prerequisite.course_id "
                    + "AND degree.degree_name = \"" + this.degreeName
                    + "\" AND degree.declared_year = " + this.declaredYear
                    + " AND degree_requirement.course_id = \"" + this.getCourseList().get(i) + "\"";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                List<String> prereq = new LinkedList<>();
                while (rs.next()) {
                    prereq.add(rs.getString(1));
                }
                getDegree().put(this.getCourseList().get(i), prereq);
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("Error in Degree.getPrerequisites()");
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }

    /**
     * @return the degree
     */
    public Map<String, List<String>> getDegree() {
        return degree;
    }

    /**
     * @return the courseList
     */
    public List<String> getCourseList() {
        return courseList;
    }
}
