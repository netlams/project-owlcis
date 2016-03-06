/**
 * CIS4398 Projects 
 * Spring 2016 
 * 3/06/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jeff, Dhruvin
 */
public class Database {

    private static Connection connection = null;
    private static final String dbUrl = "jdbc:mysql://localhost:3306/owlcis";
    private static final String username = "user";
    private static final String pwrd = "temple2016";
    private static StringBuilder err = new StringBuilder("");
    
    public static String getError() {
        return err.toString();
    }

    public static Connection getConn() throws SQLException {
        /*
         This method will intialize Database connection for OWL CIS to backend.
         */
        if (connection != null) {
            return connection;
        } else {
            try {
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(dbUrl, username, pwrd);
                System.out.println ("Database connection established");
                return connection;
            } catch (SQLException ex) {
                // handle any errors
                err.append("Error encountered!\n");
                err.append("SQLException: ");
                err.append(ex.getMessage());
                err.append("\n");
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } catch (ClassNotFoundException ex) {
                err.append("Error encountered!\n");
                err.append("Driver class not found: ");
                err.append(ex.getMessage());
                err.append("\n");
                System.out.println("Driver class not found: " + ex.getMessage());
            } catch (Exception ex) {
                err.append("Error encountered!\n");
                err.append("Connection not created: ");
                err.append(ex.getMessage());
                err.append("\n");
                System.out.println("Connection not created: " + ex.getMessage());
            }
            return null;
        }
    }

    public static void close() throws SQLException {
        try {
            //it will forcefully close any open connection in Database.
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error: Connection not closed: " + ex.getMessage());
        }
    }

}
