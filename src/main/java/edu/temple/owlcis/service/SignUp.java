/**
 * CIS4398 Projects 
 * Spring 2016 
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * When a User clicks on SignUp tab, the web page redirects itself to
 * registration page
 *
 * @author Mounya
 */
public class SignUp {
    
    
    public static String addNewUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO user (user_type, f_name, l_name) "
                                + "VALUES (?, ?, ?)";
                
                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, user.getRole());
                stmt.setString(2, user.getFname());
                stmt.setString(3, user.getLname());

                // execute query
                rs = stmt.executeQuery();
                System.out.println("addNewUser Query executed.");
                
                switch (user.getRole()) {
                    case User.MEMBER: 
                        sql = "INSERT INTO member (user_type, f_name, l_name) "
                                + "VALUES (?, ?, ?)";
                }
                
                

                // get first row found
//                if (rs.first())
//                    return getLongRoleName(rs.getString(1).toLowerCase());
//                else
//                    return null;
//                rs.first();
                    return null;
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
        }

        // failure
        return null;
    }
}
