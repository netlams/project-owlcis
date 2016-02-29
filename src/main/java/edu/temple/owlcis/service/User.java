/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * User serves as the base class for all User role subtypes (Member, Advisor,
 * and Moderator). It cannot be instantiated by itself; only its subtypes can be
 * instantiated. The subtypes inherits all required properties and overrides any
 * appropriate methods. Common properties include fname, lname, id, and email.
 * Common methods include access checkers for subtype privileges.
 *
 * @author Dau
 */
public abstract class User {

    /**
     * The User's first name
     */
    private String fname;
    /**
     * The User's last name
     */
    private String lname;
    /**
     * The User's id (identifier)
     */
    private int id;
    /**
     * The User's email address
     */
    private String email;

    /**
     * Default constructor for User object
     */
    public User() {
        this.fname = "Jane";
        this.lname = "Doe";
        this.id = 0;
        this.email = "jd@example.com";
    }

    /**
     * Parameterized Constructor for User object
     *
     * @param f the first name to give to User object
     * @param l the last name to give to User object
     * @param i the id to give to User object
     * @param e the email to give to User object
     */
    public User(String f, String l, int i, String e) {
        this.fname = f;
        this.lname = l;
        this.id = i;
        this.email = e;
    }

    /**
     * Factory to make new user subtypes based on input
     *
     * @param roleType the name of the User subtype to make. Acceptable values
     * are Member, Advisor, and Moderator.
     * @return new User subtype if roleType is acceptable
     * @throws IllegalArgumentException if invalid roleType
     */
    public static User userFactory(String roleType) {
        if (roleType.equalsIgnoreCase("Member")) {
            return new Member();
        } else if (roleType.equalsIgnoreCase("Advisor")) {
            return new Advisor();
        } else if (roleType.equalsIgnoreCase("Moderator")) {
            return new Moderator();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks whether this object could read reviews
     *
     * @return true if object have ReadReviews access; false otherwise.
     */
    public boolean canReadReviews() {
        return true;
    }

    /**
     * Checks whether this object could write reviews
     *
     * @return true if object have WriteReview access; false otherwise.
     */
    public boolean canWriteReview() {
        return false;
    }

    /**
     * Checks whether this object could write forum posts
     *
     * @return true if object have WriteForumPost access; false otherwise.
     */
    public boolean canWriteForumPost() {
        return false;
    }

    /**
     * Checks whether this object could save schedules
     *
     * @return true if object have SaveSchedule access; false otherwise.
     */
    public boolean canSaveSchedule() {
        return false;
    }

    /**
     * Checks whether this object could comment on reviews
     *
     * @return true if object have CommentReview access; false otherwise.
     */
    public boolean canCommentReview() {
        return false;
    }

    /**
     * Checks whether this object could flag reviews
     *
     * @return true if object have Flag access; false otherwise.
     */
    public boolean canFlag() {
        return false;
    }

    /**
     * Checks whether this object could unflag
     *
     * @return true if object have UnFlag access; false otherwise.
     */
    public boolean canUnFlag() {
        return false;
    }

    /**
     * Checks whether this object could delete an user
     *
     * @return true if object have DeleteUser access; false otherwise.
     */
    public boolean canDeleteUser() {
        return false;
    }

    /**
     * Checks whether this object could change an user role
     *
     * @return true if object have ChangeUserRole access; false otherwise.
     */
    public boolean canChangeUserRole() {
        return false;
    }

    /**
     * Gets the first name
     *
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the first name
     *
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Gets the last name
     *
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the last name
     *
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Gets the id (identifier)
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id (identifier)
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the email address
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}