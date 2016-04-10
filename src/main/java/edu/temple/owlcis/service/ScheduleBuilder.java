/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * The purpose of the ScheduleBuilder class is to provide users with the ability
 * to build a course schedule to save for personal reference. The schedule will
 * store the semesters and the desired courses for each semester. The class
 * provides methods to add and delete courses from a schedule. Methods are also
 * included to save the schedule or remove it from the database. Finally, a
 * method is included that allows users to generate a flowchart from a
 * previously built schedule.
 *
 * @author Rachel Tritsch
 */
public class ScheduleBuilder {

    //Global variables
    private String username; //the username of the student who created the schedule (e.g., tue11223@temple.edu)
    private String creationDateTime; //the time and date at which the schedule was created

    /**
     * the constructor for the ScheduleBuilder object
     *
     * @param un the student's username
     */
    public ScheduleBuilder(String un) {
        username = un;
    }

    /**
     * Purpose: add courses to a schedule Pre-conditions: the schedule does not
     * contain the course to be added Post-conditions: the schedule contains the
     * course
     *
     * @param courseID the ID of the course to be added to the schedule
     * @return true if the course was successfully added to the schedule; false
     * if it was not added
     */
    private boolean addCourse(String courseID) {
        return true;
    }

    /**
     * Purpose: delete courses from a schedule Pre-conditions: the schedule
     * contains the course to be deleted Post-conditions: the schedule does not
     * contain the course
     *
     * @param courseID the ID of the course to be deleted from the schedule
     * @return true if the course was successfully deleted from the schedule;
     * false if it was not deleted
     */
    private boolean deleteCourse(String courseID) {
        return true;
    }

    /**
     * Purpose: save a schedule to the database Post-conditions: the database
     * contains the schedule
     *
     * @return true if schedule was successfully inserted into database; false
     * if schedule was not inserted
     */
    private boolean insertIntoDB() {
        //NOTE: the creation date and time will be determined here
        return true;
    }

    /**
     * Purpose: remove a schedule from the database Pre-conditions: the database
     * has a previously saved schedule to be removed Post-conditions: the
     * database does not contain any saved schedule associated with the current
     * user
     *
     * @return true if schedule was successfully removed from database; false if
     * schedule was not removed
     */
    private boolean removeFromDB() {
        return true;
    }

    /**
     * Purpose: generate a flowchart based off of a generated schedule
     * Post-conditions: a flowchart is generated and displayed on the webpage
     * for the user
     *
     * @return true if flowchart was successfully generated; false if flowchart
     * was not generated
     */
    private boolean generateFlowchart() {
        return true;
    }

}
