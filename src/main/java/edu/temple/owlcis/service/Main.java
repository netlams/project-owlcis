/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
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

        /* root API */
        get(API_LOC + "/", (request, response) -> "<h1>/ root directory</h1> ");       

        /* Post Review Route */
        post(API_LOC + "/coursereviews", (request, response) -> {
            Gson gson = new Gson();
            CourseReview testReview = gson.fromJson(request.body(), CourseReview.class);
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
        
        
        /* Update Profile Route */
        post(API_LOC + "/updateprofile", (request, response) -> {
            Gson gson = new Gson();
            Profile testProfile = gson.fromJson(request.body(), Profile.class);
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    //this only gets called if they update profile and courses taken
                    if (testProfile.updateProfile(dbc.getConn())
                            && testProfile.updateTakenCourses(dbc.getConn())) {
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
        
        
        get(API_LOC + "/courselist", (request, response) -> {
            User user = request.session().attribute("USER");
            Member member = (Member)user;
            Profile profile = new Profile(member);
            Database dbc = new Database();
            profile.updateTakenCourses(dbc.getConn());
            Gson gson = new Gson();
            System.out.println("You've reached this page");
            return gson.toJson(profile.getTakenCourses());
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

                /* Check if email is from Temple University */
                if (SignIn.isValidTempleEmailAddress(user.getEmail()) == true) {
                    try {
                        String userRole = SignIn.findUserRole(dbc.getConn(), user);
                        if (userRole != null) {
                            // found user
                            response.status(200);
                            request.session(true);
                            request.session().attribute("USER", user);
                            // frontend can only access cookies, so we setting cookies here
                            response.cookie("EMAIL", user.getEmail(), 3600);
                            response.cookie("FNAME", user.getFname(), 3600);
                            response.cookie("ROLE", userRole, 3600);
                            ret = "User logged in: " + user.getEmail()
                                    + ". \n\"HTTP 200 OK\"";
                            System.out.println(ret);
                        } else {
                            // no such user
                            // notify client to ask for additional info
                            response.status(202);
                            request.session().attribute("USER", user);
                            // frontend can only access cookies, so we setting cookies here
                            // set cookie to timeout in 900 seconds
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
         * Signup Route
         */
        post("/signup", (request, response) -> {
            String ret = "";
            System.out.println("Starting signup. " + request.body());

            try {
                if (request.session().attributes() != null) {
                    // Parser 
                    Gson gson = new Gson();
                    // get user data from session
                    User user = request.session().attribute("USER");
                    System.out.println("Existing User: " + user.toString());

                    // temporary holder to determine which user type
                    User newUser = gson.fromJson(request.body(), User.class);
                    System.out.println("NO PROBLEM USER");
                    newUser = gson.fromJson(request.body(), User.userFactory(newUser.getRole()).getClass());
                    System.out.println("New User's class " + newUser.getClass());

                    // Set new user's info from session data
                    newUser.setEmail(user.getEmail());
                    newUser.setFname(user.getFname());
                    newUser.setLname(user.getLname());
                    System.out.println("New User's: " + newUser.toString());

                    // Ready to add to database
                    Database dbc = new Database();
                    if (dbc.getError().length() == 0) {
                        ret = SignUp.addNewUser(dbc.getConn(), newUser);
                        request.session().attribute("USER", newUser);
                        response.cookie("EMAIL", user.getEmail(), 3600);
                        response.cookie("FNAME", user.getFname(), 3600);
                        response.cookie("ROLE", user.getRole(), 3600);
                        response.status(203);
//                        System.out.println(newUser.toString());
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
                if (list.isEmpty()) 
                    response.status(404);
                else 
                    response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

    }
}
