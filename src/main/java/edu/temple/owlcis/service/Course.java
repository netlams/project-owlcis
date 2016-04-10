/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * The purpose of the Course class is to store data about each course that is
 * added to a student's schedule via the Schedule Builder feature. Each time a
 * student adds a course to his/her schedule, a new Course instance is created.
 * The instance will store the ID, title, associated CIS major, and associated
 * semester of the course. The Course objects will be stored in an instance of
 * the Schedule class.
 *
 * @author dhruvin sheth, Rachel Tritsch
 */
public class Course {

    //global variables to be used in posting reviews
    private String courseID; //the CIS course ID (e.g., CIS 4398)
    private String courseName; //the course name (e.g., Projects in Computer Science)
    private String cisMajor; //the major within CIS (e.g., Computer Science)
    private String semester; //the semester the course was/is to be taken (e.g., SP2016)
    private double help; // store helpfulness score to db
    private double easiness; // store easiness
    private double clarity; // store clarity;
    private String elective; //recommendation for elective course

    /**
     * the constructor for the Course object
     */
    protected Course() {
        courseID = "";
        courseName = "";
        elective = "";
        semester = "";
        help = 0;
        easiness = 0;
        clarity = 0;
    }

    /**
     * Purpose: set the ID of the course Post-conditions: the course ID variable
     * is set to the parameter string
     *
     * @param id the CIS course ID
     */
    protected void setCourseID(String id) {
        courseID = id;
    }

    /**
     * Purpose: set the title of the course Post-conditions: the course title
     * variable is set to the parameter string
     *
     * @param name the course title
     */
    protected void setCourseName(String name) {
        courseName = name;
    }

    /**
     * Purpose: set the major of the course Post-conditions: the major variable
     * is set to the parameter string
     *
     * @param major the major within CIS
     */
    protected void setCISMajor(String major) {
        cisMajor = major;
    }

    /**
     * Purpose: It sets recommened electives to the parameter string
     * Post-conditions: the elective variable is set to the parameter string
     *
     * @param elective
     */
    protected void setElective(String elective) {
        elective = elective;
    }

    /**
     * Purpose: It sets semester when the course was taken to the parameter
     * string Post-conditions: the semester variable is set to the parameter
     * string
     *
     * @param sem
     */
    protected void setSemester(String sem) {
        semester = sem;
    }

    /**
     * It sets helpfulness score provided by student when posting review to
     * parameter double Post-conditions: the helpful variable is set to the
     * parameter double
     *
     * @param helpful
     */
    protected void setHelp(double helpful) {
        help = helpful;
    }

    /**
     * It sets easiness of course provided by student when posting review to
     * parameter double Post-conditions: the easy variable is set to the
     * parameter double
     *
     * @param easy
     */
    protected void setEasy(double easy) {
        easy = easiness;
    }

    /**
     * It sets clarity score provided by student when posting review to
     * parameter double Post-conditions: the clarity variable is set to the
     * parameter double
     *
     * @param clarity
     */
    protected void setClarity(double clarity) {
        clarity = clarity;
    }

    /**
     * Purpose: get the ID of the course
     *
     * @return the CIS course ID
     */
    protected String getCourseID() {
        return courseID;
    }

    /**
     * Purpose: get the name of the course
     *
     * @return the course name
     */
    protected String getCourseName() {
        return courseName;
    }

    /**
     * Purpose: get the major of the course
     *
     * @return the major within CIS
     */
    protected String getCISMajor() {
        return cisMajor;
    }

    /**
     * Purpose: Gets elective course recommend by students and returns it
     *
     * @return the elective
     */
    protected String getelective() {
        return elective;
    }

    /**
     * Purpose: It gets which semester course was taken and returns it
     *
     * @return the semester
     */
    protected String getSemester() {
        return semester;
    }

    /**
     * Purpose: It gets helpfulness score provided by student when posting
     * review and returns the value
     *
     * @return the help
     */
    protected double gethelp() {
        return help;

    }

    /**
     * Purpose: It gets easiness score provided by student when posting review
     * and returns the value
     *
     * @return the easiness
     */
    protected double geteasiness() {
        return easiness;

    }

    /**
     * Purpose: It gets clarity score provided by student when posting review
     * and returns the value
     *
     * @return the clarity
     */
    protected double getclarity() {
        return clarity;

    }
}
