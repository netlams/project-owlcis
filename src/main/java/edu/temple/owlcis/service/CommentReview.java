/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sheth
 *
 */
/*
 The intended goal for this class to display comments on our 
 reviewss page and post comments to database.
 insertCommnet=  insert comment to comment_review table in db
 displayComemnt= display comment under review from table in db
 */
public class CommentReview {

    //Global Variables
    private int userID;
    private int reviewID;
    // private int commentID;
    private String courseID;
    private String f_name;
    private String l_name;
    private String comment_text;
    private String semester;
    private String time_stamp;

//Constructor that sets all variables to empty
    public CommentReview() {
        this.userID = 0;
        this.reviewID = 0;
        // this.commentID= 0;
        this.courseID = "";
        this.semester = "";
        this.f_name = "";
        this.l_name = "";
        this.comment_text = "";
        this.semester = "";
        this.time_stamp = "";
    }

    /* @Override
     public String toString() {
     return "CommentReview{" + "userID=" + userID + ", reviewID=" + reviewID + ", "
     + "comment_text=" + comment_text + ", "
     + "f_name=" + f_name + ", l_name=" + l_name + ","
     + " semester=" + semester + '}';
     }
     */
    @Override
    public String toString() {
        return "CommentReview{" + "reviewID=" + reviewID + ", userID=" + userID + ", "
                + "comment_text=" + comment_text + '}';
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
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return ID of user associated with this course review
     */
    public int getreviewID() {
        return reviewID;
    }

    /**
     * Sets ID of user associated with this course review
     *
     * @param userID
     */
    public void setreviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     *
     * @return comment text of this review
     */
    public String getCommentText() {
        return comment_text;
    }

    /**
     * Sets review text
     *
     * @param reviewText
     */
    public void setComemntText(String comment_text) {
        this.comment_text = comment_text;
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
     *
     * @param semester
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getfName() {
        return f_name;
    }

    public void setfName(String f_name) {
        this.f_name = f_name;
    }

    public String getLName() {
        return l_name;
    }

    public void seeLName(String l_name) {
        this.l_name = l_name;
    }

    public String gettime_stamp() {
        return time_stamp;
    }

    public void settime_stamp(String time) {
        this.time_stamp = time;
    }

    public CommentReview(int r, String c, String c_id, String f, String l, String s, String t, int u) {
        this.reviewID = r;
        this.comment_text = c;
        this.courseID = c_id;
        this.f_name = f;
        this.l_name = l;
        this.semester = s;
        this.time_stamp = t;
        this.userID = u;

    }

    public List getAllComment() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<CommentReview> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT rc.review_id,  comment_text, course_id, substring(f_name,1,1), "
                        + "substring(l_name,1,1), semester, "
                        + " rc.time_stamp, rc.user_id\n"
                        + "FROM owlcis.course_review_comment as rc, owlcis.user as u, owlcis.course_review as cr\n"
                        + " where rc.user_id = u.user_id AND rc.review_id = cr.review_id;";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("Get All COmments Method Query executed.");

                // add to list
                while (rs.next()) {

                    list.add(new CommentReview(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getInt(8)));
                }
                System.out.println(list);
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }

            }
            dbc.closeConn();
            return list;
        }
        return null;
    }

    public boolean insertComment(Connection conn) throws SQLException {
        PreparedStatement stmt = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO course_review_comment (review_id, user_id, comment_text)"
                        + "VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);

                //Set parameters
                stmt.setInt(1, reviewID);
                stmt.setInt(2, userID);
                stmt.setString(3, comment_text);
                System.out.println("SQL FOR INSERTING COMMENT" + sql);

                stmt.executeUpdate();
                System.out.println("comment post reviews Query executed.");

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
