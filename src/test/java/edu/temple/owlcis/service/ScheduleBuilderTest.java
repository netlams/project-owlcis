/**
 * CIS4398 Projects
 * Spring 2016
 * 4/24/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 * Unit Tests Cases for Sprint 1's Sign-in, Sign-up, and Database
 * classes/methods
 *
 * @author Dau
 */
public class ScheduleBuilderTest {

    private Database dbc;
    private User user;
    
    /**
     * Set up: Init Database object, Open database connection, and init user 
     */
    @Before
    public void setUp() {
        // init database connection
        dbc = new Database();
        try {
            Connection conn = dbc.getConn();
        } catch (Exception ex) {
            fail("No database connection.");
            System.out.println(ex.getMessage());
        }
        
        // init user
        user = new User();
        user.setEmail("example@temple.edu");
        user.setFname("Testing");
        user.setLname("SomeoneAlwaysHere");
        user.setId(12);
    }

    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }

    /**
     * Case 1 - Schedule object should have correct attributes.
     * Input: New Schedule object with course and and semester season
     * Expected Result: String representation (toString method) matches input
     * parameters.
     */
    @Test
    public void scheduleShouldHaveCorrectAttributes() {
        Schedule test = new Schedule("CIS 1001", "FA16");
        String expected = "Schedule{courseID=CIS 1001, semester=FA16}";
        String actual = test.toString();
        assertEquals("Should be equal", actual, expected);
    }

    /**
     * Case 2 - ScheduleBuilder constructor should call loadMember() and fetch
     * profile.
     * Input: Valid user
     * Expected Result: ScheduleBuilder's profile user matches.
     */
    @Test
    public void scheduleBuilderShouldLoadMember() {
        user.setEmail("example@temple.edu");
        user.setFname("Testing");
        user.setLname("SomeoneAlwaysHere");
        user.setId(12);
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            String actual = test.getProfile().getMember().getEmail();
            String expected = user.getEmail();
            assertEquals("Should be equal", actual, expected);

            assertTrue(test.loadMember());
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Test failed due to exception error.");
        }
    }
    
    /**
     * Case 3 - addSchedule should not accept a schedule with null data members.
     * Input: Schedule object with a null course id
     * Expected Result: Exception
     */
    @Test 
    public void addScheduleShouldFail() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            String name = null;
            String semester = null;
            test.addSchedule(new Schedule(name, semester), dbc.getConn());
            fail("Exception should be thrown");
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * Case 4 - removeSchedule should not accept a schedule with null data members.
     * Input: Schedule object with a null course id
     * Expected Result: Exception
     */
    @Test 
    public void removeScheduleShouldFail() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            String name = null;
            String semester = null;
            test.removeSchedule(new Schedule(name, semester), dbc.getConn());
            fail("Exception should be thrown");
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * Case 5 - addSchedule should return true after successful insertion.
     * Input: Schedule object
     * Expected Result: true
     */
    @Test 
    public void addScheduleShouldReturnTrue() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            assertTrue("Should return true", test.addSchedule(new Schedule("CIS 1001", "FA16"), dbc.getConn()));
            test.removeSchedule(new Schedule("CIS 1001", "FA16"), dbc.getConn());
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
    
    /**
     * Case 6 - removeSchedule should return true after successful removal.
     * Input: Schedule object
     * Expected Result: true
     */
    @Test 
    public void removeScheduleShouldReturnTrue() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            test.addSchedule(new Schedule("CIS 1001", "FA16"), dbc.getConn());
            assertTrue("Should return true", test.removeSchedule(new Schedule("CIS 1001", "FA16"), dbc.getConn()));
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
    
    /**
     * Case 7 - getSchedule should return a List of 0 or more Schedule objects.
     * Input: Valid user
     * Expected Result: LinkedList of Semester
     */
    @Test 
    public void getScheduleShouldReturnLinkedList() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            test.addSchedule(new Schedule("CIS 1166", "FA16"), dbc.getConn());
            java.util.LinkedList<Schedule> result = test.getSchedule(dbc.getConn());
            assertNotNull("Should not be null", result);
            test.removeSchedule(new Schedule("CIS 1001", "FA16"), dbc.getConn());
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
    
    /**
     * Case 8 - loadDegreeIntoSchedule should true after successful load.
     * Input: Valid Degree
     * Expected Result: true
     */
    @Test 
    public void loadDegreeIntoScheduleShouldReturnTrue() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            String degreeName = "CS_BS";
            int declaredYear = 2012;
            boolean result = test.loadDegreeIntoSchedule(degreeName, declaredYear, dbc.getConn());
            
            assertNotNull("Should not be null", result);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
    
    /**
     * Case 9 - generateFlowchart should return a StudentFlowchart object with list.
     * Input: Valid user
     * Expected Result: true
     */
    @Test 
    public void generateFlowchartShouldReturnStudentFlowchart() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            String degreeName = "CS_BS";
            int declaredYear = 2012;
            StudentFlowchart result = test.generateFlowchart(degreeName, declaredYear, false);
            
            assertNotNull("Should not be null", result);
            List<List> list = result.getList();
            assertNotNull("Should not be null", list);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
    
    /**
     * Case 10 - removeAllSchedule should return true with successful removal.
     * Input: Valid user
     * Expected Result: true
     */
    @Test 
    public void removeAllScheduleShouldReturnTrue() {
        try {
            ScheduleBuilder test = new ScheduleBuilder(user);
            boolean result = test.removeAllSchedule(dbc.getConn());
            
            assertTrue("Should be true", result);
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            fail("Exception occurred");
        }
    }
}
