/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

/**
 *
 * @author dhruvin sheth
 */
public class Course {

    //global variables to be used in posting reviews
    private String courseID; // CIS course
    private String courseName; //course name
    private String semester; //semester when the course was taken
    private double help; // store helpfulness score to db
    private double easiness; // store easiness
    private double clarity; // store clarity;
    private String elective; //recommendation for elective course

    protected Course() {
        courseID = "";
        courseName = "";
        elective = "";
        semester = "";
        help = 0;
        easiness = 0;
        clarity = 0;
    }

    /* It sets the ID of the course to the parameter string
     */
    protected void setCourseID(String id) {
        courseID = id;
    }

    /* It sets the course name to the parameter string
     */
    protected void setCourseName(String name) {
        courseName = name;
    }

    /* It sets recommened electives to the parameter string
     */
    protected void setelective(String elecive) {
        elective = elective;
    }
    /* It sets semester when the course
     was taken to the parameter string
     */

    protected void setSemester(String sem) {
        semester = sem;
    }

    /* It sets helpfulness score provided by student when posting
     review to paramaeter double
     */
    protected void sethelp(double helpful) {
        help = helpful;

    }
    /* It sets easiness of course provided by student when posting
     review to paramaeter double
     */

    protected void seteasy(double easy) {
        easy = easiness;

    }
    /* It sets clarity score provided by student when posting
     review to paramaeter double
     */

    protected void setclarity(double clarity) {
        clarity = clarity;

    }
    /* It gets the ID of the course and returns it
     */

    protected String getCourseID() {
        return courseID;
    }

    /* It get course name and returns it
     */
    protected String getCourseName() {
        return courseName;
    }
    /* It gets elective course recommend by students and returns it
     */

    protected String getelective() {
        return elective;
    }
    /* It gets which semester course was taken and returns it
     */

    protected String getSemester() {
        return semester;
    }

    /* It gets helpfulness score provided by student when posting
     review and returns the value
     */
    protected double gethelp() {
        return help;

    }
    /* It gets easiness score provided by student when posting
     review and returns the value
     */

    protected double geteasiness() {
        return easiness;

    }
    /* It gets clarity score provided by student when posting
     review and returns the value
     */

    protected double getclarity() {
        return clarity;

    }
}
