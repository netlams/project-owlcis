/**
 * CIS4398 
 * Projects Spring 
 * 2016 2/25/2016
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

    public static Connection getConn() throws SQLException {
        /*
         This method will intialize Database connection for OWL CIS to backend.
         */
        if (connection != null) {
            return connection;
        } else {

            String driver = "com.mysql.jdbc.Driver";
            /* try for connection by passing location of mysql, user and
             password to connect it. We will utlize getConnection method in drivermanager
             class
             */

            /* catch all exceptions for connection */
            return connection;
        }

    }

    public static Connection close() throws SQLException {
        //it will forcefully close any open connection in Database.
        return null;
        /* forcefully close the open connection */
    }

}
