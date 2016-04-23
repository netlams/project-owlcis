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
    public static final byte CORE = 1;
    public static final byte ELECTIVE = 0;

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
    
    /**
     * Is this requirement a core?
     * @return true if elective
     */
    public boolean isCore() {
        return this.coreOrElective == DegreeReq.CORE;
    }
    
    /**
     * Is this requirement an elective?
     * @return true if elective
     */
    public boolean isElective() {
        return this.coreOrElective == DegreeReq.ELECTIVE;
    }

    @Override
    public String toString() {
        return this.courseID;
    }
    
    @Override
    public int hashCode() {
        return (int)this.coreOrElective;
    }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof DegreeReq) && ((DegreeReq) o).getCourseID().equals(this.courseID)) {
            return true;
        }
        return false;
    }

}
