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
public class User {
    private String fname;
    private String lname;
    private int id;
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
     * Constructor for User object
     * @param u
     * @param id
     * @param e 
     */
    public User(String f, String l, int i, String e) {
        this.fname = f;
        this.lname = l;
        this.id = i;
        this.email = e;
    }
    
    /**
     * @return void
     */
    public boolean canReadReviews() {
        return true;
    }
    
    /**
     * 
     * @return canWriteReview access
     */
    public boolean canWriteReview() {
        return false;
    }
    
    /**
     * 
     * @return canFlag access
     */
    public boolean canFlag() {
        return false;
    }
    
    /**
     * 
     * @return canUnFlag access
     */
    public boolean canUnFlag() {
        return false;
    }
    
    /**
     * @return canDeleteUser access
     */
    public boolean canDeleteUser() {
        return false;
    }
    
    /**
     * @return canChangeUserRole access
     */
    public boolean canChangeUserRole() {
        return false;
    }
    
    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
                
}
