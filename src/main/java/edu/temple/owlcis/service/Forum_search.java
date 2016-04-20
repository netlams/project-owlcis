/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ricky
 */
public class Forum_search {

    private String search_keywords;
    private String time_stamp;
    public int user_id;

    public Forum_search() {

        this.search_keywords = "";
        this.time_stamp = "";
    }

    public Forum_search(String question, String time) {

        this.search_keywords = question;
        this.time_stamp = time;
    }

    public String getUserEnteredQues() {
        return search_keywords;
    }

    public void setUserEnteredQues(String question) {
        this.search_keywords = question;

    }

    public List getAllForum_search() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Forum_search> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = " SELECT post_text,time_stamp FROM owlcis.forum_post where post_text like '%" + search_keywords + "%' ORDER BY time_stamp DESC";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("getAllPost_text Query executed.");

                // add to list
                while (rs.next()) {

                    String text = rs.getString(2);
                    Timestamp timestamp = Timestamp.valueOf(text);
                    //System.out.println("Timestamp: " + timestamp);
                    Date date = new Date(timestamp.getTime());
                    //System.out.println("Date: " + date);

                    list.add(new Forum_search(rs.getString(1), date.toString()));
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

    public List getAllForum_searchid(int id) throws SQLException {

        user_id = id;
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Forum_search> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT owlcis.forum_post.post_text,time_stamp FROM owlcis.forum_post where user_id = '" + user_id + "' order by owlcis.forum_post.time_stamp DESC";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("getAllPost_text Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new Forum_search(rs.getString(1), rs.getString(2)));
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

    public String getTimeStamp() {
        return time_stamp;
    }

    public void setTimeStamp(String time) {
        this.time_stamp = time;
    }

}
