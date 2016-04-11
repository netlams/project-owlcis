/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sheth
 */
public class CourseCount {

    private String course_id;

    public CourseCount() {

        this.course_id = "";
    }

    public static List getCoursesCount() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Courselist> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT  course_id from course_review\n"
                        + "group by course_id\n"
                        + "order by count(course_id) DESC LIMIT 5;";
                stmt = dbc.getConn().createStatement();
                // execute query
                rs = stmt.executeQuery(sql);

                System.out.println("course count Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new Courselist(rs.getString(1)));

                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {

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
            dbc.closeConn();
            return list;
        }
        return null;
    }

    /**
     * @return the name
     */
    public String getCourseId() {
        return course_id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String course_id) {
        this.course_id = course_id;
    }
}
