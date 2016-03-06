/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * A User can Log into his/her account.
 *
 * @author Mounya
 */
public class SignIn {

    //Global Variables
    private String email;
    private String password;

    /**
     * This method takes username and password from the user and validates the
     * information by checking the User table columns Returns true if validation
     * was successful
     */
    public boolean logIn() {
        return true;
    }

    public static String test() throws SQLException {
        Connection conn = Database.getConn();
        StringBuilder ret = new StringBuilder("");
        Statement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM test");
                System.out.println("Query executed");
                // Now do something with the ResultSet ....
                while (rs.next()) {
                    ret.append(rs.getString(1));
                    ret.append(rs.getString(2));
                }
                return ret.toString();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
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

        return Database.getError();

    }
}
