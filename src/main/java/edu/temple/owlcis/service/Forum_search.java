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
     public String post_id1;
    private int post_id;
    private String f_name;
    private String l_name;

    public Forum_search() {

        this.search_keywords = "";
        this.time_stamp = "";
        this.post_id = 0;
        this.f_name ="";
        this.l_name="";
    }

    public Forum_search(String question, String time, int id,String f,String l) {

        this.search_keywords = question;
        this.time_stamp = time;
        this.post_id = id;
        this.f_name= f;
        this.l_name =l;
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
                //String sql = " SELECT post_text,time_stamp,post_id FROM owlcis.forum_post where post_text like '%" + search_keywords + "%' ORDER BY time_stamp DESC";
                String sql = "SELECT fp.post_text,fp.time_stamp,fp.post_id,substring(u.f_name,1,1),substring(u.l_name,1,1) " +
                                "FROM owlcis.forum_post fp " +
                                "JOIN owlcis.user u on u.user_id = fp.user_id " +
                                "where fp.post_text like '%" + search_keywords + "%' ORDER BY fp.time_stamp DESC";

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

                    list.add(new Forum_search(rs.getString(1), date.toString(),rs.getInt(3),rs.getString(4),rs.getString(5)));
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
                String sql = "SELECT owlcis.forum_post.post_text,time_stamp,post_id,user_id,is_question FROM owlcis.forum_post where user_id = '" + user_id + "' order by owlcis.forum_post.time_stamp DESC";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("getAllPost_text Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new Forum_search(rs.getString(1), rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5)));
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
    public String getf_name() {
        return f_name;
    }

    public void setf_name(String n) {
        this.f_name = n;
    }

}
