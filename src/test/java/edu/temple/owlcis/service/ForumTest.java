/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Ricky
 */
public class ForumTest {
   
    private Database dbc;

    /**
     * Set up: Init Database object and Open database connection
     */
    @Before
    public Connection setUp() {
        Connection conn = null;
        dbc = new Database();
        try {
            conn = dbc.getConn();
        } catch (Exception ex) {
            fail("No database connection.");
            System.out.println(ex.getMessage());
        }
        return conn;
    }

    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }
    
    /* Use case where a Post text gets stored into BD */
    
     @Test
    public void shouldReturnTrueForInsertions() throws SQLException {
        Forum_post post = new Forum_post();
        
        boolean actual = post.insertAllForum_post(setUp());
        assertTrue("Should Return True", actual);
    }
    
     /* Use case replyToQuestion should not accept a Post with null attributes*/
    
    public void shouldReturnFalseForNullInsertions() throws SQLException {
        Forum_comment com = new Forum_comment();
        
        boolean actual = com.insertAllForum_comment(setUp());
        assertTrue("Should Return True", actual);
    }
    
    /* Use case createQuestion should not accept a Post with null attributes*/
    
    public void shouldReturnFalseForNullInsertionsPost() throws SQLException {
        Forum_post empty = new Forum_post();
        
        boolean actual = empty.insertAllForum_post(setUp());
        assertTrue("Should Return True", actual);
    }
    
    /*Use case getAllQuestions should return a List of 0 or more Post object*/
     public List shouldReturnList() throws SQLException {
        Forum_search getlist = new Forum_search();
        
        List list = getlist.getAllForum_search();
        //assertTrue("Should Return True", actual);
        return list;
    }
     
     /*Use case getPostByQuestion should return a List of 0 or more Post object*/
    public List shouldgetPostByQuestion () throws SQLException {
        Forum_search getlist = new Forum_search();
        getlist.setUserEnteredQues("example");
        List list = getlist.getAllForum_search();
        //assertTrue("Should Return True", actual);
        return list;
    }
    
}
