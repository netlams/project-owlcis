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
 * @author Mounya, Dau
 */
public class SignIn {

    /**
     * This method takes user email, fname, lname from the user and validates
     * the
     * information by checking the User table columns Returns user role name if
     * validation
     * was successful
     */
    public static String findUserRole(Connection conn, String email, String fname, String lname) throws SQLException {
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
                rs.first();
                return getLongRoleName(rs.getString(1).toLowerCase());
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

        // failure
        return null;
    }

    public static String test(Connection conn) throws SQLException {
        StringBuilder ret = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet rs = null;

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
//                conn.close();
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
        // failture
        return null;
    }

    public static boolean isValidTempleEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(temple.edu)$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String getLongRoleName(String type) {
        String roleName;
        switch (type) {
            case "me":
                roleName = "member";
                break;
            case "mo":
                roleName = "moderator";
                break;
            case "ad":
                roleName = "advisor";
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
        return roleName;
    }
}
