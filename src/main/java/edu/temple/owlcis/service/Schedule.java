package edu.temple.owlcis.service;
import java.util.LinkedList;

/**
 *
 * @author Rachel Tritsch
 * The purpose of the Schedule class is to store courses that students have
 * added to their desired schedule on the OWLCIS website. Each time a student
 * uses the Schedule Builder feature of the application, a new Schedule
 * instance is created. Each instance is maintained in the ScheduleBuilder class,
 * where courses are added and deleted.
 */
public class Schedule {
   
    //Global variables
    protected LinkedList<Course> courses; //the list of courses in the schedule
    
    /**
     * the constructor for the Schedule object
     */
    protected Schedule() {
        courses = new LinkedList<>();
    }
    
}
