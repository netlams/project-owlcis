/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dhruvin sheth
 */
public class search {

    public search() {
    }

    public static String display(database dbc) {
        PreparedStatement stmt = null;
        ResultSet results = null;
        String sql;

        /*
         We should intialize database connection by using preparestatement.
         Intialize sql query which we gives information for course name,
         semester taken, recommeneded elective, helpfulness, easiness,
         clarity score and comment posted by user. it has to same attributes
         and names as given in database.
         By having formatted those results according to our front end layouts by passing
         formatclass in parameter(still looking for which one to use). We should run
         result in loops until we get results for it.
         To prevent leaks we will close database connections, stmt and results.
         this method would return results into string
         */
        return null;
    }

    public static void searchDB(String courseSearch, database dbc) throws SQLException {
        String selectCourse = null;

        StringBuilder sb = null;
        try {
            sb = new StringBuilder("");
            PreparedStatement stmt = null;
            ResultSet results = null;

            String sql = "";

            if (null == selectCourse) {
                // append to defined sql to get result for given course
            }

            stmt = dbc.getConn().prepareStatement(sql);

            results = stmt.executeQuery();
            /*
             get the result in formatted by having following things listed course name, helpfulness, clarity 
             and easiness of course, posted review of that course, instructors, thumbs up, thumbs down
             */
        } finally {
            dbc.close();
        }

        /*close the open database connection to prevent memory leak */
    }

    public void post(course course, database connection) throws SQLException {
        try {

            PreparedStatement stmt = null;
           //

            /*intialize insert into statements to post reviews in database*/
            stmt.executeUpdate();

            stmt.close(); //1

            /*do the same thing for remaining items to post in database*/
            stmt.executeUpdate();

            stmt.close(); //2       

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
