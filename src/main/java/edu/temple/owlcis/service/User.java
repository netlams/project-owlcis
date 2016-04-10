/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * User serves as the base class for all User role subtypes (Member, Advisor,
 * and Moderator). The subtypes inherits all required properties and overrides
 * any appropriate methods. Common properties include fname, lname, id, and
 * email. Common methods include access checkers for subtype privileges.
 *
 * @author Dau
 */
public class User {

    /**
     * Constants
     */
    public static final String MEMBER = "member";
    /**
     * Constants
     */
    public static final String ADVISOR = "advisor";
    /**
     * Constants
     */
    public static final String MODERATOR = "moderator";

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
     * The User's email address
     */
    private String role;

    /**
     * Default constructor for User object
     */
    public User() {
        this.id = 0;
        this.fname = "Jane";
        this.lname = "Doe";
        this.email = "jd@example.com";
        this.role = User.MEMBER;
    }

    /**
     * Parameterized Constructor for User object
     *
     * @param i the id to give to User object
     * @param e the email to give to User object
     * @param f the first name to give to User object
     * @param l the last name to give to User object
     * @param r the role to give to User object
     */
    public User(int i, String e, String f, String l, String r) {
        this();
        this.fname = f;
        this.lname = l;
        this.id = i;
        this.email = e;
        this.role = r;
    }

    /**
     * Parameterized Constructor for User object
     *
     * @param f the first name to give to User object
     * @param l the last name to give to User object
     * @param e the email to give to User object
     */
    public User(String f, String l, String e) {
        this();
        this.fname = f;
        this.lname = l;
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
        return this.id;
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

    /**
     * Gets the role
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets string representation of object
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "First Name: " + this.getFname() + " Last Name: "
                + this.getLname() + " Email: " + this.getEmail()
                + " Role: " + this.getRole();
    }

    /**
     * Converts short role name to long name
     *
     * @param type the short role name
     * @return long role name
     */
    public static String getLongRoleName(String type) {
        String roleName;
        type = type.toLowerCase();
        switch (type) {
            case "me":
                roleName = User.MEMBER;
                break;
            case "mo":
                roleName = User.MODERATOR;
                break;
            case "ad":
                roleName = User.ADVISOR;
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
        return roleName;
    }

    /**
     * Converts long role name to short name
     *
     * @param roleName the long name
     * @return the short name
     */
    public static String getShortRoleName(String roleName) {
        roleName = roleName.toLowerCase();
        switch (roleName) {
            case User.MEMBER:
                roleName = "me";
                break;
            case User.MODERATOR:
                roleName = "mo";
                break;
            case User.ADVISOR:
                roleName = "ad";
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + roleName);
        }
        return roleName;
    }
}
