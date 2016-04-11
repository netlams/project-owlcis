/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import java.util.LinkedList;

/**
 * The purpose of the Schedule class is to store courses that students have
 * added to their desired schedule on the OWLCIS website. Each time a student
 * uses the Schedule Builder feature of the application, a new Schedule instance
 * is created. Each instance is maintained in the ScheduleBuilder class, where
 * courses are added and deleted.
 *
 * @author Rachel Tritsch
 *
 */
public class Schedule {

    //Global variables
    protected String courseID; //ID of course taken this semester
    protected String courseTitle; //title of course taken this semester
    protected String semester; //semester in which course was taken

    /**
     * the constructor for the Schedule object
     */
    public Schedule() {
        courseID = "";
        courseTitle = "";
        semester = "";
    }

    /**
     * the constructor for the Schedule object
     */
    public Schedule(String cid) {
        this();
        courseID = cid;
        semester = "";
    }

    /**
     * the constructor for the Schedule object
     */
    public Schedule(String cid, String sem) {
        this();
        courseID = cid;
        semester = sem;
    }

    /**
     * the constructor for the Schedule object
     */
    public Schedule(String cid, String cn, String sem) {
        this();
        courseID = cid;
        courseTitle = cn;
        semester = sem;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID.toUpperCase();
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester.toUpperCase();
    }

    /**
     * @return the courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * @param courseTitle the courseTitle to set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public String toString() {
        return "Schedule{" + "courseID=" + courseID + ", semester=" + semester + '}';
    }

}
