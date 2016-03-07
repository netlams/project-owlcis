/**
 * CIS4398 Projects 
 * Spring 2016 
 * 2/25/2016
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
 * @author Jeff, Dhruvin
 */
public class Search {

    public Search() {
    }

    public static String display(Database dbc) {
        PreparedStatement stmt = null;
        ResultSet results = null;
        String sql;

        /*
         We should intialize Database connection by using preparestatement.
         Intialize sql query
         By having formatted those results according to our front end layouts by passing
         formatclass in parameter(still looking for which one to use). We should run
         result in loops until we get results for it.
         To prevent leaks we will close Database connections, stmt and results.
         this method would return results into string
         */
        return null;
    }

    public static void searchDB(String topicSearch, Database dbc) throws SQLException {
        String selectTopic = null;

        StringBuilder sb = null;
        try {
            sb = new StringBuilder("");
            PreparedStatement stmt = null;
            ResultSet results = null;

            String sql = "";

            if (null == selectTopic) {
                // append to defined sql to get result for given topic
            }

            stmt = dbc.getConn().prepareStatement(sql);

            results = stmt.executeQuery();

        } finally {
            dbc.closeConn();
        }

        /*close the open Database connection to prevent memory leak */
    }

    public void post(Forum forum, Database connection) throws SQLException {
        try {

            PreparedStatement stmt = null;
            //

            /*intialize insert into statements to post in Database*/
            stmt.executeUpdate();

            stmt.close(); //1

            /*do the same thing for remaining items to post in Database*/
            stmt.executeUpdate();

            stmt.close(); //2       

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConn();
        }
    }
}
