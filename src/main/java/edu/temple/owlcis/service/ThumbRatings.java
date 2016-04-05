/**
 * CIS4398 Projects
 * Spring 2016
 * 3/17/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ThumbRatings {
    
    //Global variables
    private int userID;
    private String courseID;
    private String semester;
    private int thumbsUp;
    private int thumbsDown;
    private final int THUMBS_DIFF = 10; //this number defines the max difference between thumbs up and down
                                        //before the course review is removed from the db
    
    /* Constructor sets values to dummy values */
    public ThumbRatings () {
        this.userID = 0;
        this.courseID = "CIS 1001";
        this.semester = "SP16";
        this.thumbsUp = 0;
        this.thumbsDown = 0;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    
    /**
     * Sets the current thumbs-up count for this review to the value from the db.
     * @param conn the db connection
     * @return true if thumbs-up was set, false if error occurred
     * @throws SQLException 
     */
    public boolean setThumbsUp (Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;
        ResultSet rs;
        
        if (conn != null) {
            try {
                sql = "SELECT thumbs_up "
                        + "FROM course_review "
                        + "WHERE user_id = ? "
                        + "AND course_id = ? "
                        + "AND semester = ?";
                
                stmt = conn.prepareStatement(sql);
                
                //Set parameters
                stmt.setInt(1, this.userID);
                stmt.setString(2, this.courseID);
                stmt.setString(3, this.semester);
                
                //Execute query
                rs = stmt.executeQuery();
                
                while (rs.next())
                    this.thumbsUp = rs.getInt(1);
                
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
            }
        }
        
        return false;
    }
    
    
    /**
     * Sets the current thumbs-down count for this review to the value from the db.
     * @param conn the db connection
     * @return true if thumbs-down was set, false if error occurred
     * @throws SQLException 
     */
    public boolean setThumbsDown (Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;
        ResultSet rs;
        
        if (conn != null) {
            try {
                sql = "SELECT thumbs_down "
                        + "FROM course_review "
                        + "WHERE user_id = ? "
                        + "AND course_id = ? "
                        + "AND semester = ?";
                
                stmt = conn.prepareStatement(sql);
                
                //Set parameters
                stmt.setInt(1, this.userID);
                stmt.setString(2, this.courseID);
                stmt.setString(3, this.semester);
                
                //Execute query
                rs = stmt.executeQuery();
                while (rs.next())
                    this.thumbsDown = rs.getInt(1);
                
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
            }
        }
        
        return false;
    }
    
    
    /**
     * Increments the thumbs-up count for this review in the db.
     * @param conn db connection
     * @return true if thumbs-up was successfully incremented, false if error occurred
     * @throws SQLException 
     */
    public boolean incThumbsUp (Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;
        
        if (conn != null) {
            try {
                sql = "UPDATE course_review "
                            + "SET thumbs_up = ? "
                            + "WHERE user_id = ? "
                            + "AND course_id = ? "
                            + "AND semester = ?";
                
                stmt = conn.prepareStatement(sql);
                
                //Set parameters
                stmt.setInt(1, this.thumbsUp + 1);
                stmt.setInt(2, this.userID);
                stmt.setString(3, this.courseID);
                stmt.setString(4, this.semester);
                
                //Execute query
                stmt.execute();
                
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
            }
        }
        return false;
    }
    
    
    /**
     * this method will increase the count of the thumbs-down for the
     * given course review. it first checks whether the increase will
     * cause the count of thumbs-down to be 10 more than
     * the thumbs-up count. if it is, then the review is automatically
     * removed from the db. otherwise, the thumbs-down count is incremented.
     * @param conn the db connection
     * @return true if query was executed successfully, false if error(s) occurred
     * @throws SQLException 
     */
    public boolean incThumbsDown (Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;
        
        if (conn != null) {
            try {
                //Check if incrementing thumbs-down count will make it 10 more than thumbs-up count
                //thumbs-up count. If this is true, remove the course review from the database.
                //Else, just increment the thumbs-down count.
                if ((this.thumbsDown + 1) == (this.thumbsUp + THUMBS_DIFF)) {
                    //remove the course review from db
                    sql = "DELETE FROM course_review "
                            + "WHERE user_id = ? "
                            + "AND course_id = ? "
                            + "AND semester = ?";
                    
                    stmt = conn.prepareStatement(sql);
                    
                    //Set parameters
                    stmt.setInt(1, this.userID);
                    stmt.setString(2, this.courseID);
                    stmt.setString(3, this.semester);
                    
                    //Execute query
                    stmt.execute();
                    
                } else {
                    //increment the thumbs-down count
                    sql = "UPDATE course_review "
                            + "SET thumbs_down = ? "
                            + "WHERE user_id = ? "
                            + "AND course_id = ? "
                            + "AND semester = ?";
                    
                    stmt = conn.prepareStatement(sql);
                    
                    //Set parameters
                    stmt.setInt(1, this.thumbsDown + 1);
                    stmt.setInt(2, this.userID);
                    stmt.setString(3, this.courseID);
                    stmt.setString(4, this.semester);
                    
                    //Execute statement
                    stmt.execute();
                    
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
            }
        }
        return false;
    }
    
}
