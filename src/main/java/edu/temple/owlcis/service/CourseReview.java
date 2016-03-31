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

/**
 * This class is for the course reviews feature.
 * @author Rachel Tritsch
 */
public class CourseReview {
    
    //Global variables
    private int userID;
    private String courseID;
    private String reviewText;
    private String semester;
    private int helpfulness;
    private int easiness;
    private int clarity;
    private boolean hasRecElective;
    private String recElectiveID;
    private String electiveSemester;
    private int thumbsUp;
    private int thumbsDown;
    
    //Constructor that sets all variables to empty
    public CourseReview() {
        userID = 0;
        courseID = "";
        reviewText = "";
        semester = "";
        helpfulness = 0;
        easiness = 0;
        clarity = 0;
        hasRecElective = false;
        recElectiveID = "";
        electiveSemester = "";
        thumbsUp = 0;
        thumbsDown = 0;
    }

    @Override
    public String toString() {
        return "CourseReview{" + "userID=" + userID + ", courseID=" + courseID + ", reviewText=" + reviewText + ","
                + " semester=" + semester + ", helpfulness=" + helpfulness + ", easiness=" + easiness + ", "
                + "clarity=" + clarity + ", hasRecElective=" + hasRecElective + ", thumbsUp=" + thumbsUp + ","
                + " thumbsDown=" + thumbsDown + '}';
    } 
    
    public String getRecElectiveID() {
        return recElectiveID;
    }

    public void setRecElectiveID(String recElectiveID) {
        this.recElectiveID = recElectiveID;
    }

    public String getElectiveSemester() {
        return electiveSemester;
    }

    public void setElectiveSemester(String electiveSemester) {
        this.electiveSemester = electiveSemester;
    }
    
    
       
    
    /**
     * 
     * @return ID of user associated with this course review
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets ID of user associated with this course review
     * @param userID 
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @return ID of course associated with this course review
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * Sets ID of course associated with this course review
     * @param courseID
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * 
     * @return review text of this review
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Sets review text
     * @param reviewText
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    /**
     * 
     * @return thumbs up count of this review
     */
    public int getThumbsUp() {
        return thumbsUp;
    }

    /**
     * Increments thumbs up count by 1
     * 
     * @param thumbsUp
     */
    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    /**
     * 
     * @return thumbs down count
     */
    public int getThumbsDown() {
        return thumbsDown;
    }

    /**
     * Increments thumbs down count by 1
     * 
     * @param thumbsDown
     */
    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    /**
     * 
     * @return semester the course was taken
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets semester the course was taken
     * @param semester 
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * 
     * @return helpfulness count (between 1 and 5)
     */
    public int getHelpfulness() {
        return helpfulness;
    }

    /**
     * Sets helpfulness (between 1 and 5)
     * @param helpfulness 
     */
    public void setHelpfulness(int helpfulness) {
        this.helpfulness = helpfulness;
    }

    /**
     * 
     * @return easiness count (between 1 and 5)
     */
    public int getEasiness() {
        return easiness;
    }

    /**
     * Sets easiness (between 1 and 5)
     * @param easiness 
     */
    public void setEasiness(int easiness) {
        this.easiness = easiness;
    }

    /**
     * 
     * @return clarity count (between 1 and 5)
     */
    public int getClarity() {
        return clarity;
    }

    /**
     * Sets clarity (between 1 and 5)
     * @param clarity 
     */
    public void setClarity(int clarity) {
        this.clarity = clarity;
    }

    /**
     * 
     * @return whether the course review has a recommended elective
     */
    public boolean hasRecElective() {
        return hasRecElective;
    }

    /**
     * Sets whether the review has a recommended elective
     * @param hasRecElective 
     */
    public void setHasRecElective(boolean hasRecElective) {
        this.hasRecElective = hasRecElective;
    }
     
    public boolean insertReview(Connection conn) throws SQLException {
        PreparedStatement stmt = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO course_review (user_id, course_id, "
                        + "semester, helpfulness, easiness, clarity, review_text, "
                        + "thumbs_up, thumbs_down) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                stmt = conn.prepareStatement(sql);
                
                //Set parameters
                stmt.setInt(1, userID);
                stmt.setString(2, courseID);
                stmt.setString(3, semester);
                stmt.setInt(4, helpfulness);
                stmt.setInt(5, easiness);
                stmt.setInt(6, clarity);
                stmt.setString(7, reviewText);
                stmt.setInt(8, thumbsUp);
                stmt.setInt(9, thumbsDown);
                
                //Execute query
                stmt.executeUpdate();
                System.out.println("insertReview query executed.");
                
                //If review has a recommended elective, insert into rec_elective
                if (hasRecElective) 
                {
                    sql = "INSERT INTO rec_elective (review_id, course_id, semester) "
                            + "VALUES ((SELECT review_id FROM course_review "
                            + "WHERE user_id = ? AND course_id = ?), ?, ?)";
                    
                    stmt = conn.prepareStatement(sql);

                    //Set parameters
                    stmt.setInt(1, userID);
                    stmt.setString(2, courseID);
                    stmt.setString(3, recElectiveID);
                    stmt.setString(4, electiveSemester);
                    
                    //Execute query
                    stmt.executeUpdate();
                    System.out.println("insertRecElective query executed.");
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
