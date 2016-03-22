/**
 * CIS4398 Projects
 * Spring 2016
 * 3/16/2016
 */
package edu.temple.owlcis.service;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * The ViewReviews class is used for Course table in the database.
 *
 * @author Puppala
 */
public class ViewReviews {

    private String comment_text;
    private String time_stamp;
    private String user_type;
    private String f_name;
    private String l_name;

    public ViewReviews() {
        this.comment_text = "";
        this.time_stamp = "";
        this.user_type = "";
        this.f_name = "";
        this.l_name = "";

    }

    public ViewReviews(String c, String t, String u, String f, String l) {
        this.comment_text = c;
        this.time_stamp = t;
        this.user_type = u;
        this.f_name = f;
        this.l_name = l;

    }

    public static List getAllReviews() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<ViewReviews> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                //String sql = "SELECT * FROM course_review_comment ORDER BY time_stamp";
                String sql = "SELECT crc.comment_text, crc.time_stamp, u.user_type, u.f_name, u.l_name "
                        + "FROM owlcis.course_review_comment crc "
                        + "JOIN owlcis.user u ON u.user_id = crc.user_id "
                        + "ORDER BY crc.time_stamp DESC";
                stmt = dbc.getConn().createStatement();
                // execute query
                rs = stmt.executeQuery(sql);
                System.out.println("getAllReviews Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new ViewReviews(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)));
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

}
