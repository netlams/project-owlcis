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
public class DegreeReq {
    private String courseID;
    private byte coreOrElective;
    public static final byte CORE = 0;

    public static final byte ELECTIVE = 1;
    
    /**
     * Default Constructor
     */
    public DegreeReq() {
        this.courseID = "";
        this.coreOrElective = DegreeReq.CORE;
    }
    
    /**
     * Constructor
     */
    public DegreeReq(String cid) {
        this();
        this.courseID = cid;
    }
    
    
    /**
     * Constructor
     */
    public DegreeReq(String cid, byte t) {
        this();
        this.courseID = cid;
        this.coreOrElective = t;
    }

    /**
     * @return the courseID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * @param courseID the courseID to set
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * @return the coreOrElective
     */
    public byte getCoreOrElective() {
        return coreOrElective;
    }

    /**
     * @param coreOrElective the coreOrElective to set
     */
    public void setCoreOrElective(byte coreOrElective) {
        this.coreOrElective = coreOrElective;
    }
    
    @Override
    public String toString() {
        return "DegreeReq: [" + this.courseID + ", " 
                + ((DegreeReq.CORE==coreOrElective)?"core":"elective") + "]"; 
    }
}
