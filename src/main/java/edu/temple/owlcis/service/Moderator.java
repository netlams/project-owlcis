/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * Moderator class is used for any user that moderate the content of OWLCIS app.
 * This is a subtype of User class. Based on this type's privileges, it
 * overrides some access methods by setting them to true: canFlag, canUnFlag,
 * canDeleteUser, and canChangeUserRole.
 *
 * @author Dau
 */
public class Moderator extends User {

    /**
     * Default Constructor for Moderator
     */
    public Moderator() {
        super();
        super.setRole(User.MODERATOR);
    }

    /**
     * Parameterized Constructor for Moderator object
     *
     * @param e the email to give to this object
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     */
    public Moderator(String e, String f, String l) {
        this();
        this.setEmail(e);
        this.setFname(f);
        this.setLname(l);
    }

    @Override
    public boolean canFlag() {
        return true;
    }

    @Override
    public boolean canUnFlag() {
        return true;
    }

    @Override
    public boolean canDeleteUser() {
        return true;
    }

    @Override
    public boolean canChangeUserRole() {
        return true;
    }

    /**
     * Gets string representation of object
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
