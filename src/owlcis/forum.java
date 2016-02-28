/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owlcis;

/**
 *
 * @author Jeff
 */
public class forum {

    //global variables
    private String post_title; //topic of the post
    private String post_body; //body of the post
    private String course_id; //course id
    private String course_name; //course name

    /**
     *initialization
     */
        protected forum() {
        post_title = "";
        post_body = "";
        course_id = "";
        course_name = "";
    }

    /*
     *Set Methods
     */
        
    /**
     * set forum post_title to the passed in title
     * @param title
     */
        protected void setTitle(String title) {
        post_title = title;
    }

    /**
     * set forum post_body to the passed in body
     * @param body
     */
        protected void setBody(String body) {
        post_body = body;
    }

    /**
     * set forum course_id to the passed in id
     * @param id
     */
        protected void setCourseId(String id) {
        course_id = id;
    }

    /**
     * set forum course_name to the passed in name
     * @param name
     */
        protected void setCourseName(String name) {
        course_name = name;
    }
        
    /*
    *get methods for forum
    */

    /**
     * get the post_title
     * @return
     */
         protected String getTitle() {
        return post_title;
    }

    /**
     * get the post_body
     * @return
     */
        protected String getBody() {
        return post_body;
    }

    /**
     * get the course id for the post
     * @return
     */
        protected String getCourseId() {
        return course_id;
    }

    /**
     * get the course name for the post
     * @return
     */
        protected String getCourseName() {
        return course_name;
    }
}
