/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

/**
 *
 * @author Lam
 */
public class Member extends User {
    private String major;
    private String studentOrAlumni;
    private String gradDate;
    
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
     * Constructor for Member
     * @param f
     * @param l
     * @param i
     * @param e
     * @param m 
     * @param sa
     * @param gd
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
    
    /**
     * @return canWriteReview access
     */
    public boolean canWriteReview() {
        return true;
    }

    /**
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
