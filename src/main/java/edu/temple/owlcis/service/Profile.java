/**
 * CIS4398 Projects Spring 2016 3/23/2016
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
 *
 * @author Rachel Tritsch
 */
public class Profile {

    //Global variables
    private Member member;
    private LinkedList<Schedule> takenCourses;
    private short completed;
    
    /**
     * Constructor
     */
    public Profile() {
        this.member = new Member();
        this.takenCourses = new LinkedList<>();
        this.completed = 1;
    }

    /**
     * Constructor that sets all variables to empty
     */
    public Profile(Member mem) {
        this();
        member = mem;
        takenCourses = new LinkedList<>();
    }

    /**
     * Fetch member data from database and set into data member 'member'
     * @param conn db connection
     * @return true if successful, false if error occurred
     * @throws java.sql.SQLException
     */
    public boolean fetchProfile(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet results = null;
        String sql;
        boolean fetch = false;

        // Interact with database
        if (conn != null) {
            try {
                sql = "SELECT f_name, l_name, email, is_curr_student, "
                        + "grad_date, major, degree_type "
                        + "FROM user INNER JOIN member "
                        + "ON user.user_id=member.mem_id "
                        + "WHERE user.user_id = ?";
                stmt = conn.prepareStatement(sql);
                //Set parameters
                stmt.setInt(1, this.getMember().getId());
                //Execute query
                results = stmt.executeQuery();
                while (results.next()) {
                    this.member.setFname(results.getString("f_name"));
                    this.member.setLname(results.getString("l_name"));
                    this.member.setEmail(results.getString("email"));
                    this.member.setStudentOrAlumni(
                            (results.getInt("is_curr_student") == 1) ? "student" : "alumni");
                    this.member.setGradDate(results.getString("grad_date"));
                    this.member.setMajor(results.getString("major"));
                    this.member.setDegreeType(results.getString("degree_type"));
                    fetch = true;
                }

            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    results = null;
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
            }
        }
        return fetch;
    }

    /**
     * adds the parameter course ID to the db for this member, if doesn't
     * already exist there
     *
     * @param sch the course and semester to be added to the db
     * @param conn db connection
     * @return true if successful, false if error occurred
     * @throws java.sql.SQLException
     */
    public boolean addTakenCourse(Schedule sch, Connection conn) throws SQLException {
        //Check if this course ID and semester is already in db for this member
        boolean insertCourse = true;
        for (Schedule takenCourse : takenCourses) {
            if (takenCourse.getCourseID().equals(sch.getCourseID())
                    && takenCourse.getSemester().equals(sch.getSemester())) {
                insertCourse = false;
                break;
            }
        }

        //Insert this course ID and semester into db if it was not in there already
        if (insertCourse) {
            PreparedStatement stmt = null;
            String sql;

            if (conn != null) {
                try {
                    sql = "INSERT INTO takes (mem_id, course_id, semester, completed) "
                            + "VALUES (?,?,?,?)";
                    stmt = conn.prepareStatement(sql);

                    //Set parameters
                    stmt.setInt(1, this.getMember().getId());
                    stmt.setString(2, sch.getCourseID().toUpperCase());
                    stmt.setString(3, sch.getSemester().toUpperCase());
                    stmt.setInt(4, completed);

                    //Execute query
                    stmt.executeUpdate();
                    System.out.println("insertTakes executed.");
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
                        } catch (SQLException sqlEx) {
                        } //ignore
                    }
                }
            }
        }
        return false;
    }

    /**
     * for delete, don't need to check in db first
     *
     */
    public boolean removeTakenCourse(Schedule sch, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;

        if (conn != null) {
            try {
                sql = "DELETE FROM takes "
                        + "WHERE mem_id = ? "
                        + "AND course_id = ? "
                        + "AND semester = ? "
                        + "AND completed = ?";
                stmt = conn.prepareStatement(sql);

                //Set parameters
                stmt.setInt(1, this.getMember().getId());
                stmt.setString(2, sch.getCourseID().toUpperCase());
                stmt.setString(3, sch.getSemester().toUpperCase());
                stmt.setInt(4, completed);

                //Execute query
                stmt.executeUpdate();

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
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
            }
        }
        return false;
    }

    /**
     * retrieves previously taken courses from database for this member, storing
     * them in takenCourses linked list
     *
     * @param conn the db connection
     * @return true if SQL query was successfully executed; false otherwise
     * @throws SQLException
     */
    public boolean fetchTakenCourses(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet results = null;
        String sql;

        if (conn != null) {
            try {
                /* Querying takes table ....................... */
                sql = "SELECT takes.course_id, takes.semester, course_title "
                        + "FROM takes INNER JOIN course "
                        + "ON takes.course_id=course.course_id where mem_id=? "
                        + "AND completed = ? "
                        + "ORDER BY SUBSTR(takes.semester FROM 3 FOR 4), "
                        + "takes.semester DESC";
                stmt = conn.prepareStatement(sql);
                //Set param
                stmt.setInt(1, this.getMember().getId());
                stmt.setInt(2, completed);
                //Execute query
                results = stmt.executeQuery();

                while (results.next()) {
                    Schedule schedule
                            = new Schedule(results.getString("takes.course_id"),
                                    results.getString("course_title"),
                                    results.getString("semester"));
                    takenCourses.add(schedule);
                }

                return true;
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
            }
        }
        return false; //will only return false if connection is null
    }

    /**
     * updates profile information for members, including student/alumni status,
     * graduation date, major, and degree type
     *
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
                int temp = ("student".equals(this.getMember().getStudentOrAlumni().toLowerCase()) ? 1 : 0);
//                stmt.setInt(1, ("student".equals(this.getMember().getStudentOrAlumni().toLowerCase()) ? 1 : 0)); //student or alumni
                stmt.setInt(1, temp); //student or alumni
                System.out.println("the student: " + temp);
                java.util.Date dateNoFormat = new java.text.SimpleDateFormat("MM/dd/yyyy").parse(this.getMember().getGradDate());
                java.sql.Date dateFormatted = new java.sql.Date(dateNoFormat.getTime());
                stmt.setDate(2, dateFormatted); //grad date
                stmt.setString(3, this.getMember().getMajor()); //major
                stmt.setString(4, this.getMember().getDegreeType()); //degree type
                stmt.setInt(5, this.getMember().getId()); //member id

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
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
                if (!conn.isClosed()) {
                    try {
                        conn.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
            }
        }
        return false;
    }

    /**
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }
    
    /**
     * 
     * @return completed
     */
    public short getCompleted() {
        return completed;
    }
    
    /**
     * 
     * @param completed set completed value
     */
    public void setCompleted(short completed) {
        this.completed = completed;
    }
    
    /**
     * getter for takenCourses list
     *
     * @return takenCourses linked list
     */
    public LinkedList getTakenCourses() {
        return this.takenCourses;
    }
    
    /**
     * 
     * @param takenCourses 
     */
    public void setTakenCourses(LinkedList<Schedule> takenCourses) {
        this.takenCourses = takenCourses;
    }
    
    
}
