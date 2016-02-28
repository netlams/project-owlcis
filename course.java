/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

/**
 *
 * @author sheth
 */
public class course {

     //Global variables
    private String courseID; // cis course
    private String courseName; //course name
    private String elective; //recommendation for elective course
    private String semester; //semester when the course was taken 
    private double help; // store helpfulness score to db
    private double easiness; // store easiness 
    private double clarity; // store clarity;
   
    protected course() {
        courseID = "";
        courseName = "";
        elective = "";
        semester = "";
        help = 0;
        easiness =0;
        clarity = 0;
    }
    
   
    protected void setCourseID(String id) {
        courseID = id;
    }
    
    
    protected void setCourseName(String name) {
        courseName = name;
    }
    
   
    protected void setelective(String elecive) {
        elective= elective;
    }
    
    protected void setSemester(String sem) {
        semester = sem;
    }
    
    
    protected void sethelp (double helpful){
        help= helpful;
        
    }
    
     protected void seteasy (double easy){
       easy= easiness;
        
    }
      protected void setclarity (double clarity){
       clarity= clarity;
        
    }
    
    protected String getCourseID() {
        return courseID;
    }
    
    
    protected String getCourseName() {
        return courseName;
    }
   
    protected String getelective() {
        return elective;
    }
  
    protected String getSemester() {
        return semester;
    }
    
      
    protected double gethelp (){
        return help;
        
    }
    
      protected double geteasiness (){
        return easiness;
        
    }
       protected double getclarity (){
        return clarity;
        
    }
}
