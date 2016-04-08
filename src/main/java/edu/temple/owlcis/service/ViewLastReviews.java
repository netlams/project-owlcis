/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.owlcis.service;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * The ViewLastReviews class is used for Course table in the database.
 *
 * @author sheth
 */
public class ViewLastReviews {

    private String comment_text;
    private String time_stamp;
    private String user_type;
    private String f_name;
    private String l_name;
    private String courseid;
    private String selected_courseid;
    private String courseName;
    private Double helpfulness;
    private Double easiness;
    private Double clarity;
    
    private String semester;
    private int thumbsup;
    private int thumbsdown;
    private int reviewid;

    public ViewLastReviews() {
        this.reviewid=0;
        this.comment_text = "";
        this.time_stamp = "";
        this.user_type = "";
        this.f_name = "";
        this.l_name = "";
        this.courseid = "";
        this.courseName = "";
        this.helpfulness = 0.0;
        this.easiness = 0.0;
        this.clarity = 0.0;
        this.selected_courseid = "";
        this.semester= "";
        this.thumbsdown= 0;
        this.thumbsup= 0;

    }

    public ViewLastReviews(int r,String c, String t, String c_id, String f, String l,
            double help, double e, double cl, String k, String s, int down, int up) {
       this.reviewid=r;
       this.comment_text = c;
        this.time_stamp = t;
        this.courseid = c_id;
        this.f_name = f;
        this.l_name = l;
        this.helpfulness = help;
        this.easiness = e;
        this.clarity = cl;
        this.selected_courseid = k;
        this.semester= s;
        this.thumbsdown= down;
        this.thumbsup= up;
    }

    public String getSelectedCourse() {
        return selected_courseid;
    }

    public void setSelectedCourse(String selected) {
        this.selected_courseid = selected;

    }

    public List getLastReviews() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<ViewLastReviews> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT  cr.review_id, cr.review_text, cr.time_stamp, cr.course_id, substring(u.f_name,1,1),substring(u.l_name,1,1), "
                        + "cr.helpfulness, cr.easiness,cr.clarity, cr.course_id, cr.semester, cr.thumbs_down, cr.thumbs_up"
                        + " FROM owlcis.course_review cr"
                        + " JOIN owlcis.user u ON u.user_id = cr.user_id"
                        +  "WHERE 1 ORDER BY cr.time_stamp DESC LIMIT 10";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("getFirstReviews Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new ViewLastReviews(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getDouble(7),
                            rs.getDouble(8),
                            rs.getDouble(9),
                            rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12),
                    rs.getInt(13)));
                }
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

    /**
     * @return the text
     */
    public String getcomment_text() {
        return comment_text;
    }

    /**
     * @param c the text to set
     */
    public void setcomment_text(String c) {
        this.comment_text = c;
    }

    /**
     * @return the time stamp
     */
    public String gettime_stamp() {
        return time_stamp;
    }

    /**
     * @param time the time to set
     */
    public void settime_stamp(String time) {
        this.time_stamp = time;
    }

    public String getuser_type() {
        return user_type;
    }

    /**
     * @param u the u to set
     */
    public void setuser_type(String u) {
        this.user_type = u;
    }

    public String getf_name() {
        return f_name;
    }

    /**
     * @param f the f to set
     */
    public void setuser_id(String f) {
        this.f_name = f;
    }

    /**
     * @return the last name
     */
    public String getl_name() {
        return l_name;
    }

    /**
     * @param l the l to set
     */
    public void setl_name(String l) {
        this.l_name = l;
    }

    /**
     * @return the course id
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     * @param l the l to set
     */
    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    /**
     * @return the course name/ title
     */
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;

    }

    /**
     * @return the helpfulness
     */
    public Double getHelpfulness() {
        return helpfulness;
    }

    public void setHelpfulness(Double help) {
        this.helpfulness = help;
    }

    /**
     * @return the easiness
     */
    public Double getEasiness() {
        return easiness;
    }

    public void setEasiness(Double easiness) {
        this.easiness = easiness;
    }

    /**
     * @return the clarity
     */
    public Double getClarity() {
        return clarity;
    }

    public void setClarity(Double clarity) {
        this.clarity = clarity;
    }
    
    
    public int getreviewID() {
        return reviewid;
    }

    public void setreviewid(int reviewid ) {
        this.reviewid = reviewid;

    }
    
      /**
     * @return the thumbsup
     */
    public int getthumbsup() {
        return thumbsup;
    }

    public void setthumbsup(int thumbsup) {
        this.thumbsup = thumbsup;
    }
    
    /**
     * @return the thumbsup
     */
    public int getthumbsdown() {
        return thumbsdown;
    }

    public void setthumbsdown(int thumbsdown) {
        this.thumbsdown = thumbsdown;
    }


}
