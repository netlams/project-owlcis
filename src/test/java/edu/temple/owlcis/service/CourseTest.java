/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

/**
 * CIS4398 Projects Spring 2016 4/22/2016
 */
import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.runner.*;

/**
 * Unit test cases for course review
 *
 * @author
 */
public class CourseTest {

    private Database dbc;

    //Establish database connection
    @Before
    public void setUp() {
        dbc = new Database();
        try {
            Connection conn = dbc.getConn();
        } catch (Exception ex) {
            fail("No database connection.");
            System.out.println(ex.getMessage());
        }
    }

    //Close connection after tests completed
    @After
    public void tearDown() {
        try {
            dbc.closeConn();
        } catch (Exception ex) {
            System.out.println("Exception error " + ex.getMessage());
        }
    }

//Failed Test (Still workign on it)
    @Test

    public void testInsertingReview() {
        try {

            CourseReview test_review = new CourseReview();
            test_review.setUserID(12);
            test_review.setCourseID("CIS 2168");
            test_review.setSemester("FA08");
            test_review.setHelpfulness(5);
            test_review.setEasiness(5);
            test_review.setClarity(5);
            String reviewText = "It is an amazing class. I would highly recommend to take this course with Dr. Lakempear. It taught me great details of programming";
            test_review.setReviewText(reviewText);

            test_review.setHasRecElective(true);
            test_review.setElectiveSemester("SP15");
            test_review.setRecElectiveID("CIS 4360");
//            System.out.println("reached here for inserting review");
            test_review.deleteReview(dbc.getConn(), reviewText);
            boolean actual = test_review.insertReview(dbc.getConn());
            assertTrue("Should return true for inserting review", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: insert course review failed .");
        }
    }

    // Following tests have passed.
    @Test
    public void testInsertingComment() {
        try {
            CommentReview commentReview = new CommentReview();
            commentReview.setComemntText("testing for comment insert");
            commentReview.setreviewID(85);
            commentReview.setUserID(1);

            boolean actual = commentReview.insertComment(dbc.getConn());
            Assert.assertTrue("should return true for inserting comments", actual);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: insert comment  .");
        }

    }

    @Test
    public void testThumbsup() {
        int revid = 85;
        int up = 5;
        int down = 2;
        try {
            ThumbRatings thumbs = new ThumbRatings(revid, up, down);

            boolean actual = thumbs.incThumbsUp(dbc.getConn());
            assertTrue("thumbs up have been increamted  ", actual);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: thumbs up test failed.  .");
        }
    }

    @Test
    public void testThumbsdown() {
        int revid = 85;
        int up = 5;
        int down = 2;
        try {
            ThumbRatings thumbs = new ThumbRatings(revid, up, down);

            boolean actual = thumbs.incThumbsDown(dbc.getConn());
            assertTrue("thumbs down have been increamted  ", actual);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: thumbs down test failed.");
        }
    }

    @Test
    public void testDisplayLastReviews() {

        try {

            ViewReviews last = new ViewReviews();

            Object actual = last.getLastReviews();
            assertNotNull("Should return true", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: get last reviews has failed.");
        }
    }

    @Test
    public void testDisplayCourseCount() {

        try {

            CourseCount last = new CourseCount();

            Object actual = last.getCoursesCount();
            assertNotNull("Should return courses with highest feedback", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: course count for highes feedback failed.");
        }
    }

    @Test
    public void testCourseList() {

        try {

            Courselist last = new Courselist();

            Object actual = last.getAllCourses();
            assertNotNull("Should return courses list for dropdwon", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: ccourse list failed.");
        }
    }

    @Test
    public void testReviewsforSelected() {

        try {

            ViewReviews last = new ViewReviews();
            last.setCourseid("CIS 1001");
            Object actual = last.getAllReviews();
            assertNotNull("Should return true for selected course id", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: reviews for selected had been failed.");
        }
    }

    @Test
    public void testReviewsforNullSelected() {

        try {

            ViewReviews last = new ViewReviews();
            last.setCourseid(null);
            Object actual = last.getAllReviews();
            assertNotNull("Should return true for selected course id", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: reviews for selected had been failed.");
        }
    }

    @Test
    public void testDeleteReview() {
        try {
            CourseReview delete = new CourseReview();

            boolean actual = delete.deleteReview(dbc.getConn(), "xxxxx");
            Assert.assertTrue("should delete review with  xxxxx string", actual);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: delete reviewTest has filed.");
        }

    }

    @Test
    public void testDisplayComments() {

        try {

            CommentReview last = new CommentReview();

            Object actual = last.getAllComment();
            assertNotNull("Should return comments for reviw", actual);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fail("Test: display comments test has failed.");
        }
    }

}
