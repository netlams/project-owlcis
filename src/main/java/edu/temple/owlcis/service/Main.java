/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

import static spark.Spark.*;
import spark.servlet.SparkApplication;
import java.util.List;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static jdk.nashorn.internal.runtime.JSType.toInt32;
import spark.Request;
import spark.Response;

/**
 * The Main class contains the init method for OWLCIS, executing the backend
 * logic of the app.
 *
 * @author Dau
 */
public class Main implements SparkApplication {

    /**
     * A constant string to containing the backend system URL
     */
    public static final String API_LOC = "/api";

    /**
     * Executes the backend logic of the app
     */
    @Override
    public void init() {
        /* serve all public files from this location */
        staticFileLocation("/public");

        /**
         * root API
         */
        get(API_LOC + "/", (request, response) -> "<h1>/ root directory</h1> ");

        /**
         * Post Review Route
         */
        post(API_LOC + "/coursereviews", (request, response) -> {
            Gson gson = new Gson();
            User user = request.session().attribute("USER");
            CourseReview testReview = gson.fromJson(request.body(), CourseReview.class);
            testReview.setUserID(user.getId());
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    if (testReview.insertReview(dbc.getConn())) {
                        response.status(201);
                        return "HTTP 201 - CREATED";
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });
        /* Increment Thumbs-up Count
        post(API_LOC + "/incthumbsup", (Request request, Response response) -> {
            String rb = request.body().replaceAll("\\D+", ""); //extracts numbers from body to get review id in string
            int revid = toInt32(rb); //integer form of review id
            //System.out.println(rb);
           // System.out.println(rb.length());
            int mylenth = rb.length();
           int up = toInt32(rb.substring(mylenth-1));
            //int reviewid = toInt32(rb.substring(0,mylenth-1));
            //System.out.println("UP :"+up);
            //System.out.println("RV :"+reviewid);


            
            ThumbRatings tr = new ThumbRatings(revid,up,0);

          Database dbc = new Database();

            if (dbc.getError().length() == 0) {
                try {
                    if (tr.setThumbsUp(dbc.getConn())) { //retrieve current thumbs-up count from db
                        if (tr.incThumbsUp(dbc.getConn())) { //attempt to increment thumbs-up
                            response.status(201);
                            return "HTTP 201 - CREATED";
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });
        */
        /* Increment Thumbs-up Count */
        post(API_LOC + "/incthumbsup", (Request request, Response response) -> {
            Pattern p = Pattern.compile("\\d+");
            //Matcher m = p.matcher(request.body());
            System.out.println("Body: "+ request.body());
            String  reviewd= request.body().substring(1, request.body().indexOf(','));
            System.out.print("Review id in main"+ reviewd);
            Matcher m = p.matcher(reviewd);
            int revid = -1;
            int upid = 0;
             String  up= request.body().substring(request.body().indexOf(',')+1, request.body().length()-1);
             System.out.println("Thumsbs up"+ up);
            /* String rb = request.body();
             int mylenth = rb.length();
           int up = toInt32(rb.substring(mylenth-1));
            */
            if (m.find()) {
                revid = toInt32(m.group());
                System.out.println("review id: " + revid);
            }
            Matcher t = p.matcher(up);
            if (t.find()){
                upid = toInt32(t.group());
                System.out.println("thumbs up in matcher"+ upid);
            }
            ThumbRatings tr = new ThumbRatings(revid,upid,0);
            Database dbc = new Database();
            
            if (dbc.getError().length() == 0) {
                try {
                    if (tr.setThumbsUp(dbc.getConn())) { //retrieve current thumbs-up count from db
                        if (tr.incThumbsUp(dbc.getConn())) { //attempt to increment thumbs-up
                            response.status(201);
                            return "HTTP 201 - CREATED";
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });
        /* Increment Thumbs-down Count */
        post(API_LOC + "/incthumbsdown", (request, response) -> {
            String rb = request.body().replaceAll("\\D+", ""); //extracts numbers from body to get review id in string
            int revid = toInt32(rb); //integer form of review id
           
            ThumbRatings tr = new ThumbRatings(revid,0,0);

            Database dbc = new Database();

            if (dbc.getError().length() == 0) {
                try {
                    if (tr.setThumbsUp(dbc.getConn()) && tr.setThumbsDown(dbc.getConn())) { //retrieve thumbs-up and down counts from db
                        if (tr.incThumbsDown(dbc.getConn())) { //attempt to call method to inc thumbs-up
                            response.status(201);
                            return "HTTP 201 - CREATED";
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });
        /* Update Profile Route */
        post(API_LOC + "/updateprofile", (request, response) -> {
            Gson gson = new Gson();
            Profile testProfile = gson.fromJson(request.body(), Profile.class);
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    if (testProfile.updateProfile(dbc.getConn())) {
                        response.status(201);
                        return "HTTP 201 - CREATED";
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Get Profile Taken Courses Route */
        get(API_LOC + "/profilecourse", (request, response) -> {
            User user = request.session().attribute("USER");
            Member member = new Member(user);
            Profile profile = new Profile(member);
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    if (profile.fetchTakenCourses(dbc.getConn())) {
                        Gson gson = new Gson();
                        return gson.toJson(profile.getTakenCourses());
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                } finally {
                    if (!dbc.getConn().isClosed()) {
                        dbc.closeConn();
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Add Taken Courses Route */
        post(API_LOC + "/addtakencourses", (request, response) -> {
            //User user = request.session().attribute("USER");
            //Member member = new Member(user);
            Member member = new Member();
            member.setId(45);
            Profile profile = new Profile(member);
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    Gson gson = new Gson();
                    Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                    System.out.println(schedule.toString());
                    if (profile.fetchTakenCourses(dbc.getConn()) //load taken courses from db into linked list
                            && profile.addTakenCourse(schedule, dbc.getConn())) { //attempt to add new taken course
                        response.status(201);
                        return "HTTP 201 - CREATED";
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                } finally {
                    if (!dbc.getConn().isClosed()) {
                        dbc.closeConn();
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Delete Taken Courses Route */
        post(API_LOC + "/deletetakencourses", (request, response) -> {
            //User user = request.session().attribute("USER");
            //Member member = new Member(user);
            Member member = new Member();
            member.setId(45);
            Profile profile = new Profile(member);
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    Gson gson = new Gson();
                    Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                    System.out.println(schedule.toString());
                    if (profile.removeTakenCourse(schedule, dbc.getConn())) {
                        response.status(201);
                        return "HTTP 201 - CREATED";
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                } finally {
                    if (!dbc.getConn().isClosed()) {
                        dbc.closeConn();
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Login Route */
        post("/login", (request, response) -> {
            String ret = "";
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList("221190717599-nkm4ci5r8eipdiqjbn8n0rr2m4tnvb78.apps.googleusercontent.com"))
                    .setIssuer("accounts.google.com")
                    .build();
            String idTokenString = request.body();
            // verify if valid google id token
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) { // valid
                Payload payload = idToken.getPayload();

                User user = new User((String) payload.get("given_name"), (String) payload.get("family_name"), payload.getEmail());

                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    // Database errors
                    response.status(500);
                    ret = "OWLCIS failed: "
                            + "\n\"HTTP 500 SERVER ERROR\"";
                }
                /* Check if email is from Temple University */
                if (SignIn.isValidTempleEmailAddress(user.getEmail()) == true) {
                    try {
                        User newUser = SignIn.findUser(dbc.getConn(), user);

                        if (newUser != null) {
                            // found user
                            response.status(200);
                            request.session(true);
                            request.session().attribute("USER", newUser);
                            // frontend can only access cookies, so we setting cookies here
                            response.cookie("EMAIL", newUser.getEmail(), 3600);
                            response.cookie("FNAME", newUser.getFname(), 3600);
                            response.cookie("ROLE", newUser.getRole(), 3600);
                            ret = "User logged in: " + newUser.getEmail()
                                    + ". \n\"HTTP 200 OK\"";
                            System.out.println(ret);
                        } else {
                            // no such user
                            // notify client to ask for additional info
                            response.status(202);
                            request.session().attribute("USER", user);
                            // frontend can only access cookies, so we setting cookies here
                            // set cookie to timeout in 900 seconds or 15 minutes
                            response.cookie("EMAIL", user.getEmail(), 900);
                            ret = "User accepted: " + user.getEmail()
                                    + ". \n\"HTTP 202 - ACCEPTED\"";
                            System.out.println(ret);
                        }
                    } catch (SQLException ex) {
                        // Database errors
                        response.status(500);
                        ret = "OWLCIS failed: " + ex.getMessage()
                                + "\n\"HTTP 500 SERVER ERROR\"";
                    }
                } else {
                    // invalid email
                    response.status(403);
                    ret = "Invalid credentials. Non-Temple."
                            + "\n\"HTTP 400 BAD REQUEST\"";
                }

                // Close connection
                dbc.closeConn();
                return ret;
            } else {
                // invalid Google Token sent
                response.status(400);
                ret = "Invalid ID sent. Non-Temple."
                        + "\n\"HTTP 400 BAD REQUEST\"";
            }
            return ret;
        });

        /**
         * Sign up Route
         */
        post("/signup", (request, response) -> {
            String ret = "";
            System.out.println("Starting signup. ");
            try {
                if (request.session().attributes() != null) {
                    // Parser
                    Gson gson = new Gson();
                    // get user data from session
                    User user = request.session().attribute("USER");
                    System.out.println("Existing User: " + user.toString());

                    // temporary holder to determine which user type
                    User newUser = gson.fromJson(request.body(), User.class);
                    newUser = gson.fromJson(request.body(), User.userFactory(newUser.getRole()).getClass());

                    // Set new user's info from session data
                    newUser.setEmail(user.getEmail());
                    newUser.setFname(user.getFname());
                    newUser.setLname(user.getLname());
                    System.out.println("New User's: " + newUser.toString());

                    // Ready to add to database
                    Database dbc = new Database();
                    if (dbc.getError().length() == 0) {
                        newUser = SignUp.addNewUserAndReturn(dbc.getConn(), newUser);
                        request.session().attribute("USER", newUser);
                        response.cookie("EMAIL", newUser.getEmail(), 3600);
                        response.cookie("FNAME", newUser.getFname(), 3600);
                        response.cookie("ROLE", newUser.getRole(), 3600);
                        response.status(203);
                    } else {
                        ret = dbc.getError();
                        throw new SQLException();
                    }
                    // Close connection
                    dbc.closeConn();
                    ret += "\nUser created: " + user.getEmail()
                            + ". \n\"HTTP 201 - CREATED\"";
                    System.out.println(ret);
                }
            } catch (NullPointerException ex) {
                response.status(400);
                /* remove these cookies */
                response.removeCookie("EMAIL");
                response.removeCookie("FNAME");
                response.removeCookie("ROLE");
                System.out.println(ex.getMessage());
                ret = "Invalid usage."
                        + "\n\"HTTP 400 BAD REQUEST\"";
            } catch (Exception ex) {
                response.status(500);
                System.out.println(ex.getMessage());
                ret = "OWLCIS failed: " + ex.getMessage()
                        + "\n\"HTTP 500 SERVER ERROR\"";
            }

            return ret;
        });

        /**
         * Signout Route
         */
        get("/signout", (request, response) -> {
            String ret = "";

            /* check if user is logged in or not */
            if (!request.session().isNew() && request.cookie("EMAIL") != null) {
                /* remove sessions */
                request.session().removeAttribute("USER");
                /* remove these cookies */
                response.removeCookie("EMAIL");
                response.removeCookie("FNAME");
                response.removeCookie("ROLE");
                /* redirect back to homepage */
                response.redirect("/");
                ret = "User is signed out.";
            } else {
                response.status(400);
                response.redirect("/");
                ret = "\"HTTP 400 BAD REQUEST\"";
            }

            return ret;
        });

        /**
         * Departments GET Route
         */
        get(API_LOC + "/depts", (request, response) -> {
            try {
                List list = Department.getAllDepartments();
                response.type("application/json");
                if (list.isEmpty()) {
                    response.status(404);
                } else {
                    response.status(200);
                }

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /*
         * ViewCourseReviews GET Route
         */
        get(API_LOC + "/viewreviews", (request, response) -> {
            try {
                ViewReviews rev = new ViewReviews();
                List list = rev.getAllReviews();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /**
         * Get All Course Review Route
         */
        get(API_LOC + "/courselist", (request, response) -> {
            try {
                List list = Courselist.getAllCourses();
                response.type("application/json");
                if (list.isEmpty()) {
                    response.status(404);
                } else {
                    response.status(200);
                }

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /**
         * Get Course Reviews for one course id Route
         */
        post(API_LOC + "/viewreviews", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());
            ViewReviews selected = new ViewReviews();
            selected.setSelectedCourse(request.body());
            List list = selected.getAllReviews();
            response.status(200);

            System.out.println(gson.toJson(0));
            System.out.println(gson.toJson(list));
            return gson.toJson(list);
        });

        /*
         * ViewCourseReviews GET Route
         */
        get(API_LOC + "/viewlastreviews", (request, response) -> {
            try {
                ViewReviews rev = new ViewReviews();
                List list = rev.getLastReviews();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });
        /*
         * CourseCount GET Route
         */
        get(API_LOC + "/coursecount", (request, response) -> {
            try {
                List list = CourseCount.getCoursesCount();
                response.type("application/json");
                if (list.isEmpty()) {
                    response.status(404);
                } else {
                    response.status(200);
                }

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });
        
        /*
         * Comment Reviews GET Route
         */
        get(API_LOC + "/commentreview", (request, response) -> {
            try {
                CommentReview rev = new CommentReview();
                List list = rev.getAllComment();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });
        
        /**
         * Post Comment in review
         */
        post(API_LOC + "/postcomment", (request, response) -> {
            Gson gson = new Gson();
            User user = request.session().attribute("USER");
            CommentReview testComment = gson.fromJson(request.body(), CommentReview.class);
            testComment.setUserID(user.getId());
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    if (testComment.insertComment(dbc.getConn())) {
                        response.status(201);
                        return "HTTP 201 - CREATED";
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });
    }
    
}
