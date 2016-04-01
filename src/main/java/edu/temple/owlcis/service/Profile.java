/**
 * CIS4398 Projects
 * Spring 2016
 * 3/23/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

/**
 * This class is for the user profiles.
 * @author Rachel Tritsch
 */
public class Profile {
    //Global variables
    private Member member;
    private LinkedList<Schedule> takenCourses;
    
    
    //Constructor that sets all variables to empty
    public Profile() {
        member = new Member();
        takenCourses = new LinkedList<>();
    }

    /**
     * 
     * @return degree type (BA or BS) 
     */
    public String getDegreeType() {
        return member.getDegreeType();
    }

    /**
     * sets degree type
     * @param degreeType 
     */
    public void setDegreeType(String degreeType) {
        this.member.degreeType = degreeType;
    }

    /**
     * 
     * @return student/alumni status of this user
     */
    public String getStudentOrAlumni() {
        return member.getStudentOrAlumni();
    }

    /**
     * sets the student/alumni status of this user
     * @param studentOrAlumni 
     */
    public void setStudentOrAlumni(String studentOrAlumni) {
        this.member.setStudentOrAlumni(studentOrAlumni);
    }

    /**
     * 
     * @return user's graduation date
     */
    public String getGradDate() {
        return this.member.getGradDate();
    }

    /**
     * sets the user's graduation date
     * @param gradDate 
     */
    public void setGradDate(String gradDate) {
        this.member.setGradDate(gradDate);
    }

    /**
     * 
     * @return user's major
     */
    public String getMajor() {
        return this.member.getMajor();
    }

    /**
     * 
     * @param major set user's major
     */
    public void setMajor(String major) {
        this.member.setMajor(major);
    }
    
    /**
     * adds the parameter course ID to the list
     * @param sch the schedule to be added to the list
     */
    public void addTakenCourse(Schedule sch) {
        //first search if list already has this schedule
        for (Schedule takenCourse : takenCourses) {
            if (takenCourse.courseID.equals(sch.getCourseID()) && takenCourse.semester.equals(sch.getSemester())) {
                //list already has this schedule, so don't add it
                break;
            }
        }
        //if reaches here, then this schedule is not in list, so add it
        takenCourses.add(sch);
    }
    
    /**
     * removes the parameter course ID and semester from both lists
     * @param sch the schedule containing the course ID and semester to be removed from list
     */
    public void removeTakenCourse(Schedule sch) {
        for (int i = 0; i < takenCourses.size(); i++) {
            if (takenCourses.get(i).courseID.equals(sch.getCourseID())
                    && takenCourses.get(i).semester.equals(sch.getSemester())) { //if found this course ID and semester
                takenCourses.remove(i); //remove this schedule from taken courses
            }
        }
    }
    
    
    /**
     * updates profile information for members, including student/alumni status,
     * graduation date, major, and degree type
     * @param conn
     * @return true if SQL query was successfully executed; false otherwise
     * @throws SQLException
     * @throws ParseException 
     */
    public boolean updateProfile(Connection conn) throws SQLException, ParseException {
        PreparedStatement stmt = null;

        if (conn != null) {
            try {
                /* Updating member table.................... */
                
                String sql = "UPDATE member " //mem id, course id, semester
                        + "SET is_curr_student = ?, grad_date = ?, "
                        + "major = ?, degree_type = ? "
                        + "WHERE mem_id = ?";
                
                stmt = conn.prepareStatement(sql);
                
                //Set parameters
                stmt.setInt(1, ("student".equals(this.member.getStudentOrAlumni().toLowerCase()) ? 1 : 0)); //student or alumni
                java.util.Date dateNoFormat = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(this.member.getGradDate());
                java.sql.Date dateFormatted = new java.sql.Date(dateNoFormat.getTime());
                stmt.setDate(2, dateFormatted); //grad date
                stmt.setString(3, this.member.getMajor()); //major
                stmt.setString(4, this.member.getDegreeType()); //degree type
                stmt.setInt(5, this.member.getId()); //member id
                                
                //Execute query
                stmt.executeUpdate();
                System.out.println("updateProfile query executed.");
                
                return true;
                
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
                
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {} //ignore
                }
                if (!conn.isClosed()) {
                    try {
                        conn.close();
                    } catch (SQLException sqlEx) {} //ignore
                }
            }
        }
        return false;
    }
    
    /**
     * inserts courses and semesters into this member's list of previously
     * taken courses
     * @param conn
     * @return true if SQL query was successfully executed; false otherwise
     * @throws SQLException 
     */
    public boolean updateTakenCourses (Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;
        
        if (conn != null) {
            try {
               /* Updating takes table....................... */
                
                //if linked list contains items
                if (!takenCourses.isEmpty()) {
                    //get all data from takes table
                    sql = "SELECT * FROM takes "
                            + "WHERE mem_id = ?";
                    stmt = conn.prepareStatement(sql);
                    
                    //Set param
                    stmt.setInt(1, this.memID);
                    
                    //Execute query
                    ResultSet results = stmt.executeQuery();
                    String course_id, semester;
                    sql = "INSERT INTO takes (mem_id, course_id, semester) "
                            + "VALUES (?,?,?)";
                    stmt = conn.prepareStatement(sql);
                    boolean insertCourse; //this var says whether to insert course into table
                    
                    //for each taken course for this member
                    for (int i = 0; i < takenCourses.size(); i++) {
                        //reset iterator here
                        insertCourse = true;
                        results.beforeFirst();
                        while (results.next()) {                                    
                            //get course ID and semester for current row
                            course_id = results.getString("course_id").toUpperCase();
                            semester = results.getString("semester").toUpperCase();
                            
                            //if current row is for the course you are trying to add
                            if (course_id.equals(takenCourses.get(i))
                                    && semester.equals(semesters.get(i))) {
                                insertCourse = false;
                                break;//skip to next course and semester
                            } //else move to next row in results
                        }
                        //if course/semester set were never found in table, insert them
                        if (insertCourse) {
                            //Set parameters
                            stmt.setInt(1, this.memID);
                            stmt.setString(2, takenCourses.get(i));
                            stmt.setString(3, semesters.get(i));
                            
                            //Execute query
                            stmt.executeQuery();
                        }
                        //move to next course in linked list
                    }
                }
                return true;
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {} //ignore
                }
                if (!conn.isClosed()) {
                    try {
                        conn.close();
                    } catch (SQLException sqlEx) {} //ignore
                }
            }
        }
        return false;
    }
    
}
