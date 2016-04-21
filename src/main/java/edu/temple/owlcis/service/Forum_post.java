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
 * @author Ricky
 */
public class Forum_post {

    private String forum_text;
    private int user_id;

    public Forum_post() {

        this.forum_text = "";
        this.user_id = 0;

    }

    public Forum_post(String posted, int id) {

        this.forum_text = posted;
        this.user_id = id;
    }

    public String getUserEnteredQues() {
        return forum_text;
    }

    public void setUserEnteredQues(String posted) {
        this.forum_text = posted;

    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int id) {
        this.user_id = id;

    }

    public boolean insertAllForum_post(Connection conn) throws SQLException {

        PreparedStatement stmt = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO owlcis.forum_post VALUES(null,'" + user_id + "','1','" + forum_text + "',NOW());";

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
    
    public boolean deletePost(Connection conn, String post) throws SQLException {
        PreparedStatement stmt = null;
        String sql;

        if (conn != null) {
            try {
                sql = "DELETE FROM forum_post "
                        + "WHERE post_text = '" + post + "'";
                stmt = conn.prepareStatement(sql);


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

}
