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
public class Advisor extends User {
    
    private int deptId;
    
    /**
     * Default Constructor for Advisor
     */
    public Advisor() {
        super();
        this.setDeptId(0);
    }
    
    /**
     * Parameterized Constructor for User object
     *
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     * @param i the id to give to this object
     * @param e the email to give to this object
     * @param did
     */
    public Advisor(String f, String l, int i, String e, int did) {
        this.setFname(f);
        this.setLname(l);
        this.setId(i);
        this.setEmail(e);
        this.setDeptId(did);
    }
    
    /**
     * @return canWriteForumPost access
     */
    public boolean canWriteForumPost() {
        return true;
    }
    
    /**
     * @return canCommentReview access
     */
    public boolean canCommentReview() {
        return true;
    }

    /**
     * @return the deptId
     */
    public int getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    
}