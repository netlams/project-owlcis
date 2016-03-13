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
 * @author Mounya, Dau
 */
public class SignUp {

    public static String addNewUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                String sql = "INSERT INTO user (user_id, user_type, f_name, l_name, email) "
                        + "VALUES (1, ?, ?, ?, ?)";

                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, User.getShortRoleName(user.getRole()));
                stmt.setString(2, user.getFname());
                stmt.setString(3, user.getLname());
                stmt.setString(4, user.getEmail());

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser Query executed.");
//                INSERT INTO member VALUES ( (SELECT user_id FROM user WHERE email='ba@temple.edu'), 1, '2016-05-06', 'CIS' );
                switch (user.getRole()) {
                    case User.MEMBER:
                        sql = "INSERT INTO member (mem_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ))";
                        break;
                    case User.MODERATOR:
                        sql = "INSERT INTO moderator (mod_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ))";
                        break;
                    case User.ADVISOR:
                        sql = "INSERT INTO advisor (adv_id) VALUES ("
                                + "(SELECT user_id FROM user WHERE email = ? ))";
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid user type: " + user.getRole());
                }

                stmt = conn.prepareStatement(sql);

                // set the params one-by-one
                stmt.setString(1, user.getEmail());

                // execute query
                stmt.executeUpdate();
                System.out.println("addNewUser type Query executed.");

                return "Good";
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
