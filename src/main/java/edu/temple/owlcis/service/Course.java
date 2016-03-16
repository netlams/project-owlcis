package edu.temple.owlcis.service;

/**
 *
 * @author Rachel Tritsch
 * The purpose of the Course class is to store data about each course that
 * is added to a student's schedule via the Schedule Builder feature. Each
 * time a student adds a course to his/her schedule, a new Course instance
 * is created. The instance will store the ID, title, associated CIS major,
 * and associated semester of the course. The Course objects will be stored
 * in an instance of the Schedule class.
 */
public class Course {
    
    //Global variables
    private String courseID; //the CIS course ID (e.g., CIS 4398)
    private String courseTitle; //the course title (e.g., Projects in Computer Science)
    private String cisMajor; //the major within CIS (e.g., Computer Science)
    private String semester; //the semester the course was/is to be taken (e.g., SP2016)
    
    /**
     * the constructor for the Course object
     */
    protected Course(String cid, String ctitle, String cmajor, String csemester) {
        courseID = cid;
        courseTitle = ctitle;
        cisMajor = cmajor;
        semester = csemester;
    }
    
    /**
     * Purpose: set the ID of the course
     * Post-conditions: the course ID variable is set to the parameter string
     * @param id the CIS course ID
     */
    protected void setCourseID(String id) {
        courseID = id;
    }
    
    /**
     * Purpose: set the title of the course
     * Post-conditions: the course title variable is set to the parameter string
     * @param title the course title
     */
    protected void setCourseTitle(String title) {
        courseTitle = title;
    }
    
    /**
     * Purpose: set the major of the course
     * Post-conditions: the major variable is set to the parameter string
     * @param major the major within CIS
     */
    protected void setCISMajor(String major) {
        cisMajor = major;
    }
    
    /**
     * Purpose: set the semester of the course
     * Post-conditions: the semester variable is set to the parameter string
     * @param sem the semester the course was/is to be taken
     */
    protected void setSemester(String sem) {
        semester = sem;
    }
    
    /**
     * Purpose: get the ID of the course
     * @return the CIS course ID
     */
    protected String getCourseID() {
        return courseID;
    }
    
    /**
     * Purpose: get the title of the course
     * @return the course title
     */
    protected String getCourseTitle() {
        return courseTitle;
    }
    
    /**
     * Purpose: get the major of the course
     * @return the major within CIS
     */
    protected String getCISMajor() {
        return cisMajor;
    }
    
    /**
     * Purpose: get the semester of the course
     * @return the semester the course was/is to be taken
     */
    protected String getSemester() {
        return semester;
    }
}
