/**
 * CIS4398 Projects
 * Spring 2016
 * 3/17/2016
 */
package edu.temple.owlcis.service;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests Cases for Sprint 1's User Types and Methods
 *
 * @author Lam
 */
public class UserTypeTest {

    /**
     * Case 1 - User object should have correct attributes.
     * Input: User type with email, first name, last name, role
     * Expected Results: String representation (toString method) matches input
     * parameters.
     */
    @Test
    public void userShouldHaveCorrectAttr() {
        // Parameterized Constructor
        User user = new User(1, "ex@temple.edu", "Jane", "Doe", "advisor");
        assertEquals("User should have correct attributes",
                "First Name: Jane "
                + "Last Name: Doe "
                + "Email: ex@temple.edu "
                + "Role: advisor",
                user.toString());
    }

    /**
     * Case 2 - Member object should have correct attributes.
     * Input: Member type with email, first name, last name, major, status
     * (student or alumni), graduation date
     * Expected Results: String representation (toString method) matches input
     * parameters.
     *
     */
    @Test
    public void memberShouldHaveCorrectAttr() {
        // Parameterized Constructor
        User user = new Member("ex@temple.edu", "Jane", "Doe", "CS", "student", "2016-05-06");
        String expected = "First Name: Jane "
                + "Last Name: Doe "
                + "Email: ex@temple.edu "
                + "Role: member "
                + "Major: CS "
                + "studentOrAlumni: student "
                + "GradDate: 2016-05-06";
        assertEquals("Member should have correct attributes", expected, user.toString());
    }

    /**
     * Case 3 - Moderator object should have correct attributes
     */
    @Test
    public void moderatorShouldHaveCorrectAttr() {
        // Parameterized Constructor
        User user = new Moderator("ex@temple.edu", "Jane", "Doe");
        String expected = "First Name: Jane "
                + "Last Name: Doe "
                + "Email: ex@temple.edu "
                + "Role: moderator";
        assertEquals("User should have correct attributes", expected, user.toString());
    }

    /**
     * Case 4 - Advisor object should have correct attributes
     */
    @Test
    public void advisorShouldHaveCorrectAttr() {
        // Parameterized Constructor
        User user = new Advisor("ex@temple.edu", "Jane", "Doe", 0);
        String expected =  "First Name: Jane "
                + "Last Name: Doe "
                + "Email: ex@temple.edu "
                + "Role: advisor "
                + "DeptId: 0";
        assertEquals("User should have correct attributes", expected, user.toString());
    }
    
    /**
     * Case 5 - UserFactory should return the User subtype.
     * Input: Member, Moderator, Advisor
     * Expected Results: Member type, Moderator type, Advisor type
     */
    @Test
    public void userFactoryShouldReturnCorrectSubType() {
        User member = User.userFactory("Member");
        User moder = User.userFactory("Moderator");
        User advis = User.userFactory("Advisor");
        
        assertTrue("Should be Member subtype", member instanceof Member);
        assertTrue("Should be Moderator subtype", moder instanceof Moderator);
        assertTrue("Should be Advisor subtype", advis instanceof Advisor);
    }
    
    /**
     * Case 6 - User class constants should match.
     * Input: Strings (Member, Moderator, Advisor)
     * Expected Results: User class constants matches input
     */
    @Test
    public void UserClassConstantsShouldMatch() {
        assertEquals("Should match member", User.MEMBER, "member");
        assertEquals("Should match moderator", User.MODERATOR, "moderator");
        assertEquals("Should match advisor", User.ADVISOR, "advisor");
    }
}
