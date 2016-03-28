/**
 * CIS4398 Projects
 * Spring 2016
 * 3/17/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import junit.framework.*;

/**
 * Unit Tests Cases for Sprint 1's Sign-in, Sign-up, and Database
 * classes/methods
 *
 * @author Lam
 */
public class SignInAndSignUpTest {

    private Database dbc;

    /**
     * Set up: Init Database object and Open database connection
     */
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

    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }

    /**
     * Case 1 - isValidTempleEmailAddress should return true for Temple emails.
     * Input: Valid Temple email in the form “tua12345@temple.edu”
     * Expected Results: true
     */
    @Test
    public void shouldReturnTrueForTempleEmails() {
        boolean actual = SignIn.isValidTempleEmailAddress("ex@temple.edu");
        assertTrue("Should Return True", actual);
    }

    /**
     * Case 2 - isValidTempleEmailAddress should return false for non-Temple emails.
     * Input: Valid email in the form “newton@gmail.com”
     * Expected Results: false
     */
    @Test
    public void shouldReturnFalseForTempleEmails() {
        boolean actual = SignIn.isValidTempleEmailAddress("newton@gmail.com");
        assertFalse("Should Return False", actual);
    }

    /**
     * Case 3 - findUserRole should return a role name for existing users.
     * Input: Existing user in the user table
     * Expected Results: a string either “member”, “advisor”, or “moderator”
     */
    @Test
    public void shouldReturnRoleNameForExistingUsers() {
        String[] roles = {User.MEMBER, User.MODERATOR, User.ADVISOR};
        String existingUserEmail = "example@temple.edu";
        User user = new User();
        String roleName = "";

        if (SignIn.isValidTempleEmailAddress(existingUserEmail)
                && dbc.getError().length() == 0) {
            user.setEmail(existingUserEmail);
            user.setFname("Testing");
            user.setLname("SomeoneAlwaysHere");
            try {
                roleName = SignIn.findUserRole(dbc.getConn(), user);
                assertNotNull(roleName);

                if (!Arrays.asList(roles).contains(roleName)) {
                    fail("RoleName: " + roleName + " doesn't match actual roles.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                fail("Test failed due to exception error.");
            }
        } else {
            fail();
        }
    }

    /**
     * Case 4 - findUserRole should return null for non-existing or new users.
     * Input: Non-existing user in the user table
     * Expected Results: Return null
     */
    @Test
    public void shouldReturnNullForBadUsers() {
        String userEmail = "newton@temple.edu";
        User user = new User();
        String roleName = "";

        if (SignIn.isValidTempleEmailAddress(userEmail)
                && dbc.getError().length() == 0) {
            user.setEmail(userEmail);
            user.setFname("Someone");
            user.setLname("NotHere");
            try {
                roleName = SignIn.findUserRole(dbc.getConn(), user);
                assertNull(roleName);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                fail("Test failed due to exception error.");
            }
        } else {
            fail();
        }
    }

    /**
     * Case 5 - addNewUser should not accept invalid user role types.
     * Input: User object with role property set to “user”
     * Expected Results: IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptInvalidRoleTypes() throws IllegalArgumentException, SQLException {
        String invalidRole = "INVALID";
        User user = new User();
        user.setRole(invalidRole);

        String resp = SignUp.addNewUser(dbc.getConn(), user);
    }

    /**
     * Case 6 - addNewUser should not accept User with first name, last name, role, or
     * email set as null.
     * Input: User object with email set as null
     * Expected Results: Exception
     */
    @Test(expected = Exception.class)
    public void shouldNotAcceptBadUserProperties() throws SQLException {
        User user = new User();
        user.setRole(null);
        user.setEmail(null);
        user.setFname(null);
        user.setLname(null);

        String resp = SignUp.addNewUser(dbc.getConn(), user);
    }
    
    /**
     * Case 7 -	New Database object should have open connection.
     * Input: New Database object
     * Expected Result: Connection is open
     */
    @Test
    public void shouldReturnNewOpenConnection() throws SQLException {
        Database newDbc = new Database();
        newDbc.getConn().isValid(10); // 10 sec timeout
        newDbc.closeConn();
    }

    /**
     * Case 8 -	getConn should always return open connection.
     * Input: Database object with closed connection
     * Expected Result: Connect is open
     */
    @Test
    public void shouldReturnOpenConnectionAlways() throws SQLException {
        dbc.closeConn();
        dbc.getConn().isValid(10); // 10 sec timeout
    }
}
