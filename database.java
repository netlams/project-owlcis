/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dhruvin sheth
 */
public class database {

    private static Connection connection = null;

    public static Connection getConn() throws SQLException {
        /*
         This method will intialize database connection for OWL CIS to backend.
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
        //it will forcefully close any open connection in database.
        return null;
        /* forcefully close the open connection */
    }
}
