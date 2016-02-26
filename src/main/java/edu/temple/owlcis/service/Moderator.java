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
public class Moderator extends User {
    
    /**
     * Default Constructor for Moderator
     */
    public Moderator() {
        super();
    }
    
    /**
     * Parameterized Constructor for User object
     *
     * @param f the first name to give to this object
     * @param l the last name to give to this object
     * @param i the id to give to this object
     * @param e the email to give to this object
     */
    public Moderator(String f, String l, int i, String e) {
        this.setFname(f);
        this.setLname(l);
        this.setId(i);
        this.setEmail(e);
    }
    
    /**
     * @return canFlag access
     */
    public boolean canFlag() {
        return true;
    }
    
    /**
     * @return canUnFlag access
     */
    public boolean canUnFlag() {
        return true;
    }
    
    /**
     * @return canDeleteUser access
     */
    public boolean canDeleteUser() {
        return true;
    }
    
    /**
     * @return canChangeUserRole access
     */
    public boolean canChangeUserRole() {
        return true;
    }
   
}