/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class manages the login requests.
 *
 * @author Mounya, Dau
 */
public class SignIn {

    /**
     * Find user role based on user information
     *
     * @param conn
     * @param user
     * @return user role
     * @throws SQLException
     */
    public static String findUserRole(Connection conn, User user) throws SQLException {
        String email = user.getEmail();
        String fname = user.getFname();
        String lname = user.getLname();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "SELECT user_type FROM user "
                        + "WHERE email = ? "
                        + "AND f_name = ? "
                        + "AND l_name = ?";
                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, email);
                stmt.setString(2, fname);
                stmt.setString(3, lname);

                // execute query
                rs = stmt.executeQuery();
                System.out.println("findUserRole Query executed.");

                // get first row found
                if (rs.first()) {
                    return User.getLongRoleName(rs.getString(1).toLowerCase());
                } else {
                    return null;
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
        }

        // failure
        return null;
    }

    /**
     * Find and return user row in database
     *
     * @param conn
     * @param user
     * @return User the found user row
     * @throws SQLException
     */
    public static User findUser(Connection conn, User user) throws SQLException {
        String email = user.getEmail();
        String fname = user.getFname();
        String lname = user.getLname();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "SELECT user_id, user_type FROM user "
                        + "WHERE email = ? "
                        + "AND f_name = ? "
                        + "AND l_name = ?";
                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, email);
                stmt.setString(2, fname);
                stmt.setString(3, lname);

                // execute query
                rs = stmt.executeQuery();
                System.out.println("findUser Query executed.");

                // get first row found
                if (rs.first()) {
                    user.setId(rs.getInt("user_id"));
                    user.setRole(User.getLongRoleName(rs.getString("user_type")));
                    return user;
                } else {
                    return null;
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
        }

        // failure
        return null;
    }

    /**
     * Check if email is from Temple
     *
     * @param email
     * @return true if Temple email; otherwise false
     */
    public static boolean isValidTempleEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(temple.edu)$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
