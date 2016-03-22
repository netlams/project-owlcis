/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import java.time.Instant;
import java.util.Date;

/**
 * Member class is used for any user that is a Temple University student or
 * alumni. This is a subtype of User class and have additional data fields:
 * major, studentOrAlumni, and gradDate. Based on this type's privileges, it
 * overrides some access methods by setting them to true: canWriteReview,
 * canWriteForumPost, canSaveSchedule.
 *
 * @author Dau
 */
public class Member extends User {

    /**
     * The Member's degree major
     */
    protected String major;
    /**
     * The Member's status: either is a student or alumni
     */
    protected String studentOrAlumni;
    /**
     * The Member's graduation date
     */
    protected String gradDate;

    /**
     * Default Constructor for Member
     */
    public Member() {
        super();
        super.setRole(User.MEMBER);
        this.setMajor("Computer Science");
        this.setStudentOrAlumni("student");
        this.setGradDate("2016-05-12");
    }

    /**
     * Parameterized Constructor for Member object
     *
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     * @param i the id to give to this object
     * @param e the email to give to this object
     * @param m the major to give to this object
     * @param sa the status to give this object (Student or Alumni)
     * @param gd the graduation date to give this object
     */
    public Member(int i, String e, String f, String l, String m, String sa, String gd) {
        this();
        this.setId(i);
        this.setEmail(e);
        this.setFname(f);
        this.setLname(l);
        this.setMajor(m);
        this.setStudentOrAlumni(sa);
        this.setGradDate(gd);
    }
    
    /**
     * Parameterized Constructor for Member object
     *
     * @param e the email to give to this object
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     * @param m the major to give to this object
     * @param sa the status to give this object (Student or Alumni)
     * @param gd the graduation date to give this object
     */
    public Member(String e, String f, String l, String m, String sa, String gd) {
        this();
        this.setEmail(e);
        this.setFname(f);
        this.setLname(l);
        this.setMajor(m);
        this.setStudentOrAlumni(sa);
        this.setGradDate(gd);
    }

    @Override
    public boolean canWriteReview() {
        return true;
    }

    @Override
    public boolean canWriteForumPost() {
        return true;
    }

    @Override
    public boolean canSaveSchedule() {
        return true;
    }

    /**
     * Gets the major
     *
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the major
     *
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Gets the Member's status: student or alumni
     *
     * @return the studentOrAlumni
     */
    public String getStudentOrAlumni() {
        return studentOrAlumni;
    }

    /**
     * Sets the Member's status
     *
     * @param studentOrAlumni the studentOrAlumni to set
     */
    public void setStudentOrAlumni(String studentOrAlumni) {
        this.studentOrAlumni = studentOrAlumni;
    }

    /**
     * Gets the gradDate
     *
     * @return the gradDate
     */
    public String getGradDate() {
        return gradDate;
    }

    /**
     * Sets the gradDate
     *
     * @param gradDate the graduation date to set
     */
    public void setGradDate(String gradDate) {
        this.gradDate = gradDate;
    }

    /**
     * Gets string representation of object
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return super.toString() + " Major: " + this.getMajor()
                + " studentOrAlumni: " + this.getStudentOrAlumni() 
                + " GradDate: " + this.getGradDate();
    }
}
