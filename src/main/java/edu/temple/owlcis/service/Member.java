/**
 * Member class 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * Member class is any user that is a Temple University student or alumni
 * @author Lam
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
        this.setMajor("Computer Science");
        this.setStudentOrAlumni("student");
        this.setGradDate("May 2016");
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
    public Member(String f, String l, int i, String e, String m, String sa, String gd) {
        this.setFname(f);
        this.setFname(l);
        this.setId(i);
        this.setEmail(e);
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
     * Gets
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return the studentOrAlumni
     */
    public String getStudentOrAlumni() {
        return studentOrAlumni;
    }

    /**
     * @param studentOrAlumni the studentOrAlumni to set
     */
    public void setStudentOrAlumni(String studentOrAlumni) {
        this.studentOrAlumni = studentOrAlumni;
    }

    /**
     * @return the gradDate
     */
    public String getGradDate() {
        return gradDate;
    }

    /**
     * @param gradDate the gradDate to set
     */
    public void setGradDate(String gradDate) {
        this.gradDate = gradDate;
    }
    
}
