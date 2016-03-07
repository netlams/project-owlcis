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
 * A User can Log into his/her account.
 *
 * @author Mounya
 */
public class SignIn {

    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    /**
     * This method takes user email, fname, lname from the user and validates
     * the
     * information by checking the User table columns Returns user role name if
     * validation
     * was successful
     */
    public static String findUserRole(Database dbc, String email, String fname, String lname) throws SQLException {
        Connection conn = dbc.getConn();
        StringBuilder ret = new StringBuilder("");

        if (conn != null) {
            try {
                String sql = "SELECT role FROM test "
                        + "WHERE email = ? "
                        + "AND fname = ? "
                        + "AND lname = ?";
                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, email);
                stmt.setString(2, fname);
                stmt.setString(3, lname);

                rs = stmt.executeQuery();
                System.out.println("findUserRole Query executed");
                // Now do something with the ResultSet ....
                while (rs.next()) {
                    ret.append(rs.getString("role"));
                }
                conn.close();
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

        return null;
    }

    public static String test(Database dbc) throws SQLException {
        Connection conn = dbc.getConn();
        StringBuilder ret = new StringBuilder("");

        if (conn != null) {
            try {
                stmt = conn.prepareStatement("SELECT * FROM test");
                rs = stmt.executeQuery();
                System.out.println("Test Query executed");
                // Now do something with the ResultSet ....
                while (rs.next()) {
                    ret.append(rs.getString(1));
                    ret.append(rs.getString(2));
                }
                conn.close();
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
        return dbc.getError();
    }
}
