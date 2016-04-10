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

    //Constructor that sets all variables to empty
    public Profile(Member mem) {
        member = mem;
        takenCourses = new LinkedList<>();
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
                    sql = "INSERT INTO takes (mem_id, course_id, semester) "
                            + "VALUES (?,?,?)";
                    stmt = conn.prepareStatement(sql);

                    //Set parameters
                    stmt.setInt(1, this.member.getId());
                    stmt.setString(2, sch.getCourseID().toUpperCase());
                    stmt.setString(3, sch.getSemester().toUpperCase());

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
                    if (!conn.isClosed()) {
                        try {
                            conn.close();
                        } catch (SQLException sqlEx) {
                        } //ignore
                    }
                }
            }
        }
        return false;
    }

    //for delete, don't need to check in db first
    public boolean removeTakenCourse(Schedule sch, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;

        if (conn != null) {
            try {
                sql = "DELETE FROM takes "
                        + "WHERE mem_id = ? "
                        + "AND course_id = ? "
                        + "AND semester = ?";
                stmt = conn.prepareStatement(sql);

                //Set parameters
                stmt.setInt(1, this.member.getId());
                stmt.setString(2, sch.getCourseID().toUpperCase());
                stmt.setString(3, sch.getSemester().toUpperCase());

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
        String sql;

        if (conn != null) {
            try {
                /* Querying takes table ....................... */
                sql = "SELECT * FROM takes "
                        + "WHERE mem_id = ?";
                stmt = conn.prepareStatement(sql);

                //Set param
                stmt.setInt(1, this.member.getId());
                System.out.println(this.member.getId());

                //Execute query
                ResultSet results = stmt.executeQuery();

                while (results.next()) {
                    Schedule schedule = new Schedule(results.getString(2), results.getString(3));
                    takenCourses.add(schedule);
                    schedule.toString();
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

}
