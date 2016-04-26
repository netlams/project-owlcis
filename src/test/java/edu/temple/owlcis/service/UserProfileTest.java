/**
 * CIS4398 Projects Spring 2016 4/22/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 * Unit test cases for User Profile feature.
 * @author Rachel Tritsch
 */
public class UserProfileTest {
    
    private Database dbc;

    //Establish database connection
    @Before
    public void setUp() {
        dbc = new Database();
        try {
            Connection conn = dbc.getConn();
        } catch (Exception ex) {
            fail("No database connection.");
            System.out.println(ex.getMessage());
        }
    }

    //Close connection after tests completed
    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }
    
    
    
    /** Test: fetchProfile(Connection conn)
     *  NOTE: the following queries were executed to create a test user
     *  for this test case.
     *  INSERT INTO user
            VALUES (100, 'me', 'Jane', 'Doe', 'janedoe@temple.edu');
        INSERT INTO member
            VALUES (100, 1, '2018-05-01', 'CS', 'BS');
     */
    @Test
    public void fetchProfile() {
        //Create new member and set information
        Member test_member = new Member();
        test_member.setId(100);
        
        try {
            //Create new Profile instance
            Profile test_profile = new Profile(test_member);
            
            //fetchProfile() should return true upon success
            boolean actual = test_profile.fetchProfile(dbc.getConn());
            assertTrue("Should return true", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: fetchProfile() failed due to exception error.");
        }
    }
    
    
    
    /** Test: fetchTakenCourses(Connection conn)
     *  NOTE: the following queries were executed to create a test user with
     *  a history of completed courses for this test case.
     *  INSERT INTO user
            VALUES (100, 'me', 'Jane', 'Doe', 'janedoe@temple.edu');
        INSERT INTO member
            VALUES (100, 1, '2018-05-01', 'CS', 'BS');
        INSERT INTO takes
            VALUES (100, 'CIS 1068', 'FA14', 1);
     */
    @Test
    public void fetchTakenCourses() {
        //Create new member and set information
        Member test_member = new Member();
        test_member.setId(100);
        
        try {
            //Create new Profile instance
            Profile test_profile = new Profile(test_member);
            
            //fetchTakenCourses() should return true upon success
            boolean actual = test_profile.fetchTakenCourses(dbc.getConn());
            assertTrue("Should return true", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: fetchTakenCourses() failed due to exception error.");
        } 
    }
    
    
    
    /** Test: updateProfile(Connection conn)
     *  NOTE: the following queries were executed to create a test user with
     *  a history of completed courses for this test case.
     *  INSERT INTO user
            VALUES (100, 'me', 'Jane', 'Doe', 'janedoe@temple.edu');
        INSERT INTO member
            VALUES (100, 1, '2018-05-01', 'CS', 'BS');
     */
    @Test
    public void updateProfile() {
        //Create new Member with updated values to be inserted into the database
        Member test_member = new Member();
        test_member.setId(100);
        test_member.setStudentOrAlumni("alumni");
        test_member.setGradDate("05/01/2015");
        test_member.setMajor("IST");
        test_member.setDegreeType("BA");
        
        try {
            //Create new Profile instance
            Profile test_profile = new Profile(test_member);
            
            System.out.println("reached here");
            
            //updateProfile() should return true upon success
            boolean actual = test_profile.updateProfile(dbc.getConn());
            assertTrue("Should return true", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: updateProfile() failed due to exception error.");
        }
    }
    
    
    
    /** Test: addTakenCourse(Schedule sch, Connection conn)
     *  NOTE: the following queries were executed to create a test user with
     *  a history of completed courses for this test case.
     *  INSERT INTO user
            VALUES (100, 'me', 'Jane', 'Doe', 'janedoe@temple.edu');
        INSERT INTO member
            VALUES (100, 1, '2018-05-01', 'CS', 'BS');
     */
    @Test
    public void addTakenCourse() {
        //Create Schedule instance to add course and semester to user's history
        Schedule test_schedule = new Schedule();
        test_schedule.setCourseID("CIS 4398");
        test_schedule.setCourseTitle("Projects in Computer Science");
        test_schedule.setSemester("SP16");
        
        //Create Member instance
        Member test_member = new Member();
        test_member.setId(100);
        
        try {
            //Create Profile instance with test_member; fetch taken courses for this user
            Profile test_profile = new Profile(test_member);
            test_profile.fetchTakenCourses(dbc.getConn());
            
            //addTakenCourse() should return true upon success
            boolean actual = test_profile.addTakenCourse(test_schedule, dbc.getConn());
            assertTrue("Should return true", actual);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: addTakenCourse() failed due to exception error.");
        }
    }
    
    
    
    /** Test: removeTakenCourse(Schedule sch, Connection conn)
     *  NOTE: the following queries were executed to create a test user with
     *  a history of completed courses for this test case.
     *  INSERT INTO user
            VALUES (100, 'me', 'Jane', 'Doe', 'janedoe@temple.edu');
        INSERT INTO member
            VALUES (100, 1, '2018-05-01', 'CS', 'BS');
        INSERT INTO takes
            VALUES (1, 'CIS 4398', 'SP16', 1);
     */
    @Test
    public void removeTakenCourse() {
        //Create Schedule instance to add course and semester to user's history
        Schedule test_schedule = new Schedule();
        test_schedule.setCourseID("CIS 4398");
        test_schedule.setCourseTitle("Projects in Computer Science");
        test_schedule.setSemester("SP16");
        
        //Create Member instance
        Member test_member = new Member();
        test_member.setId(100);
        
        try {
            //Create Profile instance with test_member
            Profile test_profile = new Profile(test_member);
            
            //removeTakenCourse() should return true upon success
            boolean actual = test_profile.removeTakenCourse(test_schedule, dbc.getConn());
            assertTrue("Should return true", actual);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: removeTakenCourse() failed due to exception error.");
        }
    }
    
}
