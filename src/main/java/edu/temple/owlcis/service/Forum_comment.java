package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ricky
 */
public class Forum_comment {

    private String comment_text;
    private int user_id;
    private String post_id;

    public Forum_comment() {

        this.comment_text = "";
        this.user_id = 0;
        this.post_id = "";

    }

    public Forum_comment(String posted, int u_id,String p_id) {

        this.comment_text = posted;
        this.user_id = u_id;
        this.post_id = p_id;
    }

    public String getUserEnteredAns() {
        return comment_text;
    }

    public void setUserEnteredAns(String posted) {
        this.comment_text = posted;

    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int id) {
        this.user_id = id;

    }

     public String getPostId() {
        return post_id;
    }

    public void setPostId(String id) {
        this.post_id = id;

    }

    
    
    public boolean insertAllForum_comment(Connection conn) throws SQLException {

        PreparedStatement stmt = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO owlcis.forum_post_comment VALUES(null, '"+post_id+"' , '"+user_id  +"','"+ comment_text + "',NOW());";

                System.out.println("SQL is :  " + sql);
                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                System.out.println("getAllPost_text Query executed.");

                return true;
            } catch (SQLException ex) {
                // handle any errors
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

