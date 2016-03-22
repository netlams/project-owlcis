/**
 * CIS4398 Projects 
 * Spring 2016 
 * 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * Advisor class is used for any user that is a Temple University CST advisor.
 * This is a subtype of User class and have additional data field: deptId. Based
 * on this type's privileges, it overrides some access methods by setting them 
 * to true: canWriteForumPost, and canCommentReview.
 *
 * @author Dau
 */
public class Advisor extends User {
    /**
     * The Advisor's department Id
     */
    private int deptId;

    /**
     * Default Constructor for Advisor
     */
    public Advisor() {
        super();
        super.setRole(User.ADVISOR);
        this.setDeptId(0);
    }

    /**
     * Parameterized Constructor for Advisor object
     *
     * @param e the email to give to this object
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     * @param did the deptId to give to this object
     */
    public Advisor(String e, String f, String l, int did) {
        this();
        this.setFname(f);
        this.setLname(l);
        this.setEmail(e);
        this.setDeptId(did);
    }

    @Override
    public boolean canWriteForumPost() {
        return true;
    }

    @Override
    public boolean canCommentReview() {
        return true;
    }

    /**
     * Gets the deptId
     * @return the deptId
     */
    public int getDeptId() {
        return deptId;
    }

    /**
     * Sets the deptId
     * @param deptId the deptId to set
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    
    /**
     * Gets string representation of object
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        return super.toString() + " DeptId: " + this.getDeptId();
    }

}
