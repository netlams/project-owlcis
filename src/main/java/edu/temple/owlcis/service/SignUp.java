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
 * This class manages the signup requests.
 *
 * @author Mounya, Dau
 */
public class SignUp {

    /**
     * Add new user to database
     *
     * @param conn
     * @param user
     * @return status result
     * @throws SQLException
     */
    public static String addNewUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO user (user_type, f_name, l_name, email) "
                        + "VALUES (?, ?, ?, ?)";

                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, User.getShortRoleName(user.getRole()));
                stmt.setString(2, user.getFname());
                stmt.setString(3, user.getLname());
                stmt.setString(4, user.getEmail());

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser Query executed.");
                switch (user.getRole()) {
                    case User.MEMBER:
                        try {
                            Member newM = (Member) user;
                            sql = "INSERT INTO member (mem_id, is_curr_student, major, grad_date) VALUES ("
                                    + "(SELECT user_id FROM user WHERE email = ? ), ?, ?, ?)"; //TODO add gradDate
                            stmt = conn.prepareStatement(sql);
                            // set the params one-by-one
                            stmt.setString(1, newM.getEmail());
                            stmt.setInt(2, (newM.getStudentOrAlumni().toLowerCase() == "student" ? 1 : 0));
                            stmt.setString(3, newM.getMajor());
                            java.util.Date date1 = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(newM.getGradDate());
                            java.sql.Date newDate = new java.sql.Date(date1.getTime());
                            stmt.setDate(4, newDate);
                        } catch (Exception ex) {
                            System.out.println("ERROR in adding to MEMBER TABLE");
                        }
                        break;
                    case User.MODERATOR:
                        sql = "INSERT INTO moderator VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ), now())";
                        stmt = conn.prepareStatement(sql);
                        // set the params one-by-one
                        stmt.setString(1, user.getEmail());

                        break;
                    case User.ADVISOR:
                        Advisor newA = (Advisor) user;
                        sql = "INSERT INTO advisor (adv_id, dept_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ), ?)";
                        stmt = conn.prepareStatement(sql);
                        // set the params one-by-one
                        stmt.setString(1, user.getEmail());
                        stmt.setInt(2, newA.getDeptId());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid user type: " + user.getRole());
                }

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser type Query executed.");

                return "Added New User";
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
     * Add new user to database
     *
     * @param conn
     * @param user
     * @return status result
     * @throws SQLException
     */
    public static String addNewUser(Connection conn, Member user) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO user (user_type, f_name, l_name, email) "
                        + "VALUES (?, ?, ?, ?)";

                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, User.getShortRoleName(user.getRole()));
                stmt.setString(2, user.getFname());
                stmt.setString(3, user.getLname());
                stmt.setString(4, user.getEmail());

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser Query executed.");
                switch (user.getRole()) {
                    case User.MEMBER:
                        sql = "INSERT INTO member (mem_id, is_curr_student, major) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ), ?, ?)"; //TODO add gradDate
                        stmt = conn.prepareStatement(sql);
                        // set the params one-by-one
                        stmt.setString(1, user.getEmail());

                        break;
                    case User.MODERATOR:
                        sql = "INSERT INTO moderator (mod_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ))";
                        stmt = conn.prepareStatement(sql);
                        // set the params one-by-one
                        stmt.setString(1, user.getEmail());

                        break;
                    case User.ADVISOR:
                        sql = "INSERT INTO advisor (adv_id, dept_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ), ?)";
                        stmt = conn.prepareStatement(sql);
                        // set the params one-by-one
                        stmt.setString(1, user.getEmail());

                        break;
                    default:
                        throw new IllegalArgumentException("Invalid user type: " + user.getRole());
                }

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser type Query executed.");

                return "Added New User";
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
