/**
 * CIS4398 Projects
 * Spring 2016
 * 4/24/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 * Integration Tests Cases User Story 1-5
 *
 * @author Dau
 */
public class IntegrationTest1_5 {

    private Database dbc;
    private final int userId = 3;
    private final String email = "integtesting@temple.edu";
    private final String fname = "Integ";
    private final String lname = "Testing";

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
    }

    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }
    
    // clean up new user created during testing
    public void removeTestingUesr() {
        Statement stmt = null;
        String sql = "DELETE FROM user WHERE email=\"" + email + "\" AND f_name = \"" + fname + "\" AND l_name = \"" + lname + "\"";
        try {
            stmt = dbc.getConn().createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Error in Degree.getReqCourses()");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(IntegrationTest1_5.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * User Story 1 Test
     */
    @Test
    public void userStory1() {
        User testUser;
        User newUser;
        User signedInUser;
        try {
            // Case 1 - User signs up as member and could log in to that account.
            // Member signup
            testUser = new Member(email, fname, lname, "CS", "student", "2015-05-06", "BS");
            newUser = SignUp.addNewUserAndReturn(dbc.getConn(), testUser);
            assertEquals("Should be equal", newUser.getEmail(), testUser.getEmail());
            // Member signin
            signedInUser = SignIn.findUser(dbc.getConn(), testUser);
            assertEquals("Should be equal", signedInUser.getEmail(), testUser.getEmail());
            removeTestingUesr();

            // Case 2 - User signs up as advisor and could log in to that account.
            // Advisor signup
            testUser = new Advisor(email, fname, lname, 1);
            newUser = SignUp.addNewUserAndReturn(dbc.getConn(), testUser);
            assertEquals("Should be equal", newUser.getEmail(), testUser.getEmail());
            // Advisor signin
            signedInUser = SignIn.findUser(dbc.getConn(), testUser);
            assertEquals("Should be equal", signedInUser.getEmail(), testUser.getEmail());
            removeTestingUesr();

            // Case 3 - User signs up as moderator and could log in to that account.
            // Moderator signup
            testUser = new Moderator(email, fname, lname);
            newUser = SignUp.addNewUserAndReturn(dbc.getConn(), testUser);
            assertEquals("Should be equal", newUser.getEmail(), testUser.getEmail());
            // Moderator signin
            signedInUser = SignIn.findUser(dbc.getConn(), testUser);
            assertEquals("Should be equal", signedInUser.getEmail(), testUser.getEmail());
            removeTestingUesr();

        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
            fail("Test failed due to exception.");
        }
    }

    /**
     * User Story 2 Test
     */
    @Test
    public void userStory2() {
        ViewReviews test = new ViewReviews();
        try {
            // Case 1 - User (not logged in yet) could view recent Course Reviews. 
            List<ViewReviews> list = test.getLastReviews();
            assertNotNull("Should not be null", list);
            assertTrue("Should not be empty", (list.size() > 0));

            // Case 2 - User logs in as member, advisor, or moderator, and could view full list of Course Reviews. 
            User mem = new Member("member@temple.edu", "Member", "Testing", "CS", "student", "2015-05-06", "BS");
            User adv = new Advisor("advisor@temple.edu", "Advisor", "Testing", 1);
            User mod = new Moderator("integtesting@temple.edu", "Integ", "Testing");
            test.setSelectedCourse("CIS 1001");
            
            mem = SignIn.findUser(dbc.getConn(), mem);
            list = test.getAllReviews();
            assertNotNull("Should not be null", list);
            assertTrue("Should not be empty", (list.size() > 0));
            
            adv = SignIn.findUser(dbc.getConn(), adv);
            list = test.getAllReviews();
            assertNotNull("Should not be null", list);
            assertTrue("Should not be empty", (list.size() > 0));
            
            mod = SignIn.findUser(dbc.getConn(), mod);
            list = test.getAllReviews();
            assertNotNull("Should not be null", list);
            assertTrue("Should not be empty", (list.size() > 0));
            
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
            fail("Test failed due to exception.");
        }
    }
    
    /**
     * User Story 3 Test
     */
    @Test
    public void userStory3() {
        User mem = new Member("member@temple.edu", "Member", "Testing", "CS", "student", "2015-05-06", "BS");
        Schedule testSchedule = new Schedule("CIS 1001", "FA16");
        try {
            // Case 1 - User logs in as member, gets schedule list, and could add a course to his/her schedule list.
            mem = SignIn.findUser(dbc.getConn(), mem);
            ScheduleBuilder sb = new ScheduleBuilder(mem);
            sb.removeAllSchedule(dbc.getConn());
            LinkedList<Schedule> list = sb.getSchedule(dbc.getConn());
            assertNotNull("Should not be null", list);
            assertTrue("Resetted list. Should be empty", list.size() == 0);
            // add schedule
            sb.addSchedule(testSchedule, dbc.getConn());
            // fetch new list
            list = sb.getSchedule(dbc.getConn());
            assertTrue("Should not be empty", (list.size() > 0));
            
            //Case 2 - User logs in as member, gets schedule list, and could delete a course out of his/her schedule list.
            // remove an existing schedule
            sb.removeSchedule(testSchedule, dbc.getConn());
            list = sb.getSchedule(dbc.getConn());
            assertTrue("Should be back to original fetched list size", (list.size() == sb.getSchedule(dbc.getConn()).size()));
            
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
            fail("Test failed due to exception.");
        }
    }
    
    /**
     * User Story 4 Test
     */
    @Test
    public void userStory4() {
        User mem = new Member("member@temple.edu", "Member", "Testing", "CS", "student", "2015-05-06", "BS");
        Schedule testSchedule = new Schedule("CIS 1001", "FA16");
        String degreeName = "CS_BS";
        int declaredYear = 2012;
        
        try {
            // Case 1 - User logs in as member, gets completed course list, and gets degree requirements. 
            mem = SignIn.findUser(dbc.getConn(), mem);
            ScheduleBuilder sb = new ScheduleBuilder(mem);
            
            sb.getProfile().addTakenCourse(testSchedule, dbc.getConn());
            LinkedList<Schedule> completedList = sb.getProfile().getTakenCourses();
            assertNotNull("Should not be null", completedList.size());
            sb.getProfile().removeTakenCourse(testSchedule, dbc.getConn());
            
            boolean resetResult = sb.removeAllSchedule(dbc.getConn());
            assertTrue("Resetted list. Should be true", resetResult);
            boolean loadingResult = sb.loadDegreeIntoSchedule(degreeName, declaredYear, dbc.getConn());
            assertTrue("Degree loaded. Should be true", loadingResult);
            
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
            fail("Test failed due to exception.");
        }
    }
    
    /**
     * User Story 5 Test
     */
    @Test
    public void userStory5() {
        User mem = new Member(userId, "member@temple.edu", "Member", "Testing", "CS", "student", "2015-05-06", "BS");
        Schedule testSchedule = new Schedule("CIS 1001", "FA16");
        String degreeName = "CS_BS";
        int declaredYear = 2012;
        
        try {
            // Case 1 - User logs in as member, gets question list, and could gets reply posts to a question. 
            mem = SignIn.findUser(dbc.getConn(), mem);
            Forum_search forum = new Forum_search();
            List<Forum_search> questionList = forum.getAllForum_search();
            assertNotNull("Should not be null", questionList);
            
            List<Forum_search> commentList = forum.getAllForum_searchid(mem.getId());
            assertNotNull("Should not be null", commentList);
            
            
            // Case 2 - User logs in as member, and could post a question.
            String postQuestion = "A question to be posted";
            Forum_post post = new Forum_post();
            post.setUserId(mem.getId());
            post.setUserEnteredQues(postQuestion);
            boolean postResult = post.insertAllForum_post(dbc.getConn());
            
            assertTrue("Post success. Should be true", postResult);
            // Note: insertAllForum_post() closed my connection...
            dbc = new Database();
            boolean deleteResult = post.deletePost(dbc.getConn(), postQuestion);
            assertTrue("Delete success. Should be true", deleteResult);
            
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
            fail("Test failed due to exception.");
        }
    }
}
