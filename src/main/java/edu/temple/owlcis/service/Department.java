/**
 * CIS4398 Projects
 * Spring 2016
 * 3/16/2016
 */
package edu.temple.owlcis.service;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * The Department class is used for department table in the database.
 *
 * @author Lam
 */
public class Department {

    private int id;
    private String shortname;
    private String name;
    private String chair;

    public Department() {
        this.id = 0;
        this.shortname = "";
        this.name = "";
        this.chair = "";
    }

    public Department(int id, String s, String n, String c) {
        this.id = id;
        this.shortname = s;
        this.name = n;
        this.chair = c;
    }

    public static List getAllDepartments() throws SQLException {
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Department> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "SELECT * FROM department ORDER BY dept_id ASC";
                stmt = dbc.getConn().createStatement();
                // execute query
                rs = stmt.executeQuery(sql);
                System.out.println("getAllDept Query executed.");

                // add to list
                while (rs.next()) {
                    list.add(new Department(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)));
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortname the shortname to set
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the chair
     */
    public String getChair() {
        return chair;
    }

    /**
     * @param chair the chair to set
     */
    public void setChair(String chair) {
        this.chair = chair;
    }

}
