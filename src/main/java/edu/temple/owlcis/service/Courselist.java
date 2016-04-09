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
 * @author Ricky
 */
public class Courselist {
   


  
   
    private String course_id;
   
    public Courselist() {
       
        this.course_id= "";
       
    }

    public Courselist(String s) {
        
        
        this.course_id= s;
        
    }

    public static List getAllCourses() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Courselist> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT course_id FROM course ORDER BY course_id";
                stmt = dbc.getConn().createStatement();
                // execute query
                rs = stmt.executeQuery(sql);
                System.out.println("getAllCourse Query executed.");

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


