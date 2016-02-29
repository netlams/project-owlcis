/**
 * CIS4398 Projects 
 * Spring 2016 
 * 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 *
 * @author Jeff
 */
public class Forum {

    //global variables
    private String post_title; //topic of the post
    private String post_body; //body of the post
    private String course_id; //course id
    private String course_name; //course name

    /**
     * initialization
     */
    protected Forum() {
        post_title = "";
        post_body = "";
        course_id = "";
        course_name = "";
    }

    /*
     *Set Methods
     */
    /**
     * set Forum post_title to the passed in title
     *
     * @param title
     */
    protected void setTitle(String title) {
        post_title = title;
    }

    /**
     * set Forum post_body to the passed in body
     *
     * @param body
     */
    protected void setBody(String body) {
        post_body = body;
    }

    /**
     * set Forum course_id to the passed in id
     *
     * @param id
     */
    protected void setCourseId(String id) {
        course_id = id;
    }

    /**
     * set Forum course_name to the passed in name
     *
     * @param name
     */
    protected void setCourseName(String name) {
        course_name = name;
    }

    /*
     *get methods for Forum
     */
    /**
     * get the post_title
     *
     * @return
     */
    protected String getTitle() {
        return post_title;
    }

    /**
     * get the post_body
     *
     * @return
     */
    protected String getBody() {
        return post_body;
    }

    /**
     * get the course id for the post
     *
     * @return
     */
    protected String getCourseId() {
        return course_id;
    }

    /**
     * get the course name for the post
     *
     * @return
     */
    protected String getCourseName() {
        return course_name;
    }
}
