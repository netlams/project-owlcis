/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ricky
 */
public class Forum_ques {
    private String post_text;
     private String comment_text;
      private String time_stamp;
    private String f_name;
    private String l_name;
    private String post_id;
    
    public Forum_ques() {

        
        this.post_text="";
       this.comment_text="";
      this.time_stamp="";
    this.f_name="";
    this.l_name="";
     this.post_id="";
        

    }

    public Forum_ques(String pt,String ct, String ts, String fn, String ln,String pid) {

        
        this.post_text=pt;
       this.comment_text=ct;
      this.time_stamp=ts;
    this.f_name=fn;
    this.l_name=ln;
    this.post_id=pid;
        

    }
    
    
    
    

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post) {
        this.post_id = post;

    }
    
    
    public List getQuestion() throws SQLException {

       
        Database dbc = new Database();
        if (dbc.getError().length() == 0) {
            // no errors
            ArrayList<Forum_ques> list = new ArrayList();
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                String sql = "select p.post_text,c.comment_text,c.time_stamp, substring(u.f_name,1,1),substring(u.l_name,1,1),p.post_id " +
                                "from owlcis.forum_post p  join owlcis.forum_post_comment c on c.post_id = p.post_id " +
                                //"join owlcis.forum_post_comment c on c.post_id = p.post_id " +
                                "join owlcis.user u on u.user_id = c.user_id " +
                                "where p.post_id = '"+post_id+"'";

                stmt = dbc.getConn().createStatement();
                rs = stmt.executeQuery(sql);
                System.out.println("getAllPost_text Query executed.");

                // add to list
                while (rs.next()) {
                    String text = rs.getString(3);
                    Timestamp timestamp = Timestamp.valueOf(text);
                    //System.out.println("Timestamp: " + timestamp);
                    Date date = new Date(timestamp.getTime());
                    //System.out.println("Date: " + date);
                    
                    list.add(new Forum_ques(rs.getString(1), rs.getString(2),date.toString(),rs.getString(4),rs.getString(5),rs.getString(6)));
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
    
    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post) {
        this.post_text = post;

    }
    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String ct) {
        this.comment_text= ct;

    }
     public String getTime() {
        return time_stamp;
    }

    public void setTime(String time) {
        this.time_stamp= time;

    }
    public String getFname() {
        return f_name;
    }

    public void setFname(String fname) {
        this.f_name= fname;

    }
    public String getLname() {
        return time_stamp;
    }

    public void setLname(String lname) {
        this.l_name= lname;

    }
    
    
    
    
    
}
