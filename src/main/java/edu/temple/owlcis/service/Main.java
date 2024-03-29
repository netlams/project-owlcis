/**
 * CIS4398 Projects Spring 2016 4/25/2016
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static jdk.nashorn.internal.runtime.JSType.toInt32;

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
    public static final String API_LOC = "api";
    public String postid;
    public String question;

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

        /**
         * Increment Thumbs-up Count
         */
        post(API_LOC + "/incthumbsup", (request, response) -> {
            Pattern p = Pattern.compile("\\d+");
            //Matcher m = p.matcher(request.body());
            System.out.println("Body: " + request.body());
            String reviewd = request.body().substring(1, request.body().indexOf(','));
            System.out.print("Review id in main" + reviewd);
            Matcher m = p.matcher(reviewd);
            int revid = -1;
            int upid = 0;
            String up = request.body().substring(request.body().indexOf(',') + 1, request.body().length() - 1);
            System.out.println("Thumsbs up" + up);
            /* String rb = request.body();
             int mylenth = rb.length();
             int up = toInt32(rb.substring(mylenth-1));
             */
            if (m.find()) {
                revid = toInt32(m.group());
                System.out.println("review id: " + revid);
            }
            Matcher t = p.matcher(up);
            if (t.find()) {
                upid = toInt32(t.group());
                System.out.println("thumbs up in matcher" + upid);
            }
            ThumbRatings tr = new ThumbRatings(revid, upid, 0);
            Database dbc = new Database();

            if (dbc.getError().length() == 0) {
                try {
                    /*if (tr.setThumbsUp(dbc.getConn())) { //retrieve current thumbs-up count from db
                     */
                    if (tr.incThumbsUp(dbc.getConn())) { //attempt to increment thumbs-up
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

        /* Increment Thumbs-down Count */
        post(API_LOC + "/incthumbsdown", (request, response) -> {
            Pattern p = Pattern.compile("\\d+");
            //Matcher m = p.matcher(request.body());
            System.out.println("Body: " + request.body());
            String reviewd = request.body().substring(1, request.body().indexOf(','));
            System.out.print("Review id in main" + reviewd);
            int revid = -1;
            Matcher m = p.matcher(reviewd);
            if (m.find()) {
                revid = toInt32(m.group());
                //System.out.println("review id: " + revid);
            }

            int upid = 0;
            String up = request.body().substring(request.body().indexOf(',') + 1, request.body().lastIndexOf(','));
            System.out.println("Thumbs Up in Main " + up);
            Matcher t = p.matcher(up);
            if (t.find()) {
                upid = toInt32(t.group());
                System.out.println("thumbs up in matcher " + upid);
            }

            int downid = 0;
            String down = request.body().substring(request.body().lastIndexOf(',') + 1, request.body().length() - 1);
            System.out.println("Thumsbs down in main" + down);
            Matcher u = p.matcher(down);
            if (u.find()) {
                downid = toInt32(u.group());
                System.out.println("thumbs down in matcher" + downid);
            }

            ThumbRatings tr = new ThumbRatings(revid, upid, downid);

            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    /*if (tr.setThumbsUp(dbc.getConn()) && tr.setThumbsDown(dbc.getConn())) { //retrieve thumbs-up and down counts from db
                     */
                    if (tr.incThumbsDown(dbc.getConn())) { //attempt to call method to inc thumbs-up
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

        /**
         * Get Member Profile Route
         */
        get(API_LOC + "/profile", (request, response) -> {
            // get session
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                // setting up member to fetch
                Member member = new Member();
                member.setId(user.getId());
                Profile profile = new Profile(member);
                Database dbc = new Database();
                // Database connection OK
                if (dbc.getError().length() == 0) {
                    try {
                        if (profile.fetchProfile(dbc.getConn())) {
                            // found member
                            response.status(200);
                            response.type("application/json");
                            return new Gson().toJson(profile.getMember());
                        } else {
                            // not valid member
                            response.status(400);
                            return "HTTP 400 - Bad Request";
                        }
                    } catch (Exception ex) {
                        response.status(500);
                        System.out.println("Error: " + ex.getMessage());
                        return "HTTP 500 - Internal Server Error";
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(401);
            return "HTTP 401 - Unauthorized";
        });

        /* Update Profile Route */
        post(API_LOC + "/profile", (request, response) -> {
            // get session
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                // get post data
                Gson gson = new Gson();
                Member member = gson.fromJson(request.body(), Member.class);
                member.setId(user.getId());
                Profile testProfile = new Profile(member);
                Database dbc = new Database();
                // Database connection OK
                if (dbc.getError().length() == 0) {
                    try {
                        if (testProfile.updateProfile(dbc.getConn())) {
                            response.status(200);
                            return "HTTP 200 - OK";
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Get Profile Taken Courses Route */
        get(API_LOC + "/profile/courses", (request, response) -> {
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                Member member = new Member(user);
                Profile profile = new Profile(member);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        if (profile.fetchTakenCourses(dbc.getConn())) {
                            response.status(200);
                            response.type("application/json");
                            return new Gson().toJson(profile.getTakenCourses());
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Add Taken Courses Route */
        post(API_LOC + "/profile/add", (request, response) -> {
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                Member member = new Member(user);
                Profile profile = new Profile(member);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        Gson gson = new Gson();
                        Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                        if (profile.fetchTakenCourses(dbc.getConn()) //load taken courses from db into linked list
                                && profile.addTakenCourse(schedule, dbc.getConn())) { //attempt to add new taken course
                            response.status(201);
                            return "HTTP 201 - CREATED";
                        } else {
                            response.status(400);
                            return "HTTP 400 - BAD REQUEST";
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /* Delete Taken Courses Route */
        post(API_LOC + "/profile/delete", (request, response) -> {
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                Member member = new Member(user);
                Profile profile = new Profile(member);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        Gson gson = new Gson();
                        Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                        System.out.println(schedule.toString());
                        if (profile.removeTakenCourse(schedule, dbc.getConn())) {
                            response.status(200);
                            return "HTTP 200 - OK";
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
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
                //List list = Forum_search.getAllForum_search();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /*
         * Forum_search GET Route
         */
        get(API_LOC + "/fs", (request, response) -> {
            try {
                Forum_search forum = new Forum_search();
                List list = forum.getAllForum_search();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /*
         * Forum_search GET Route
         */
        get(API_LOC + "/forum", (request, response) -> {
            try {
                User user = request.session().attribute("USER");
                Forum_search f = new Forum_search();
                List list = f.getAllForum_searchid(user.getId());
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        /**
         * Forum_post Route
         */
        post(API_LOC + "/fv", (request, response) -> {

            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());
            User user = request.session().attribute("USER");
            Forum_post post = new Forum_post();
            post.setUserId(user.getId());
            post.setUserEnteredQues(request.body());
            // post.setUserId();
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {

                    if (post.insertAllForum_post(dbc.getConn())) {
                        //response.status(200);
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

        /**
         * for flowchart
         */
        get(API_LOC + "/flowchart", (request, response) -> {
            // get session
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                try {
                    ScheduleBuilder sb = new ScheduleBuilder(user);
                    response.status(200);
                    response.type("application/json");
                    int year = 2012;
                    if (request.cookie("FC_YEAR") != null) {
                        year = Integer.parseInt(request.cookie("FC_YEAR"));
                    }
                    String degree = "CS_BS";
                    if (request.cookie("FC_DEGREE") != null) {
                        degree = request.cookie("FC_DEGREE");
                    }

                    return new Gson().toJson(sb.generateFlowchart(degree, year, false));
                } catch (NullPointerException ex) {
                    // not valid member
                    response.status(400);
                    System.out.println("Error: " + ex.getMessage());
                    return "HTTP 400 - Bad Request";
                } catch (Exception ex) {
                    response.status(500);
                    System.out.println("Error: " + ex.getMessage());
                    return "HTTP 500 - Internal Server Error";
                }
            }
            response.status(401);
            return "HTTP 401 - Unauthorized";
        });

        /**
         * Get Schedule Courses Route
         */
        get(API_LOC + "/schedule", (request, response) -> {
            User user = request.session().attribute("USER");
            // check if there's a logged-in session
            if (user != null) {
                ScheduleBuilder sb = new ScheduleBuilder(user);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        response.status(200);
                        response.type("application/json");
                        return new Gson().toJson(sb.getSchedule(dbc.getConn()));
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /**
         * post forum Route
         */
        post(API_LOC + "/schedule/add", (request, response) -> {
            User user = request.session().attribute("USER");
            if (user != null) {
                ScheduleBuilder sb = new ScheduleBuilder(user);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        Gson gson = new Gson();
                        Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                        if (sb.addSchedule(schedule, dbc.getConn())) { //attempt to add new taken course
                            response.status(201);
                            return "HTTP 201 - CREATED";
                        } else {
                            response.status(400);
                            return "HTTP 400 - BAD REQUEST";
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /**
         * post forum Route
         */
        post(API_LOC + "/schedule/remove", (request, response) -> {
            User user = request.session().attribute("USER");
            if (user != null) {
                ScheduleBuilder sb = new ScheduleBuilder(user);
                Database dbc = new Database();
                if (dbc.getError().length() == 0) {
                    try {
                        Gson gson = new Gson();
                        Schedule schedule = gson.fromJson(request.body(), Schedule.class);
                        if (sb.removeSchedule(schedule, dbc.getConn())) { //attempt to add new taken course
                            response.status(200);
                            return "HTTP 200 - OK";
                        } else {
                            response.status(400);
                            return "HTTP 400 - BAD REQUEST";
                        }
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } finally {
                        if (!dbc.getConn().isClosed()) {
                            dbc.closeConn();
                        }
                    }
                }
            }
            response.status(500);
            return "OWLCIS failed: HTTP 500 SERVER ERROR";
        });

        /**
         * Reset user schedule list Route
         */
        delete(API_LOC + "/schedule/reset", (request, response) -> {
            User user = request.session().attribute("USER");
            ScheduleBuilder sch = new ScheduleBuilder(user);
            Database dbc = new Database();
            sch.removeAllSchedule(dbc.getConn());
            return "HTTP 200 - OK";
        });

        /**
         * Load sample schedule list Route
         */
        post(API_LOC + "/schedule/load", (request, response) -> {
            User user = request.session().attribute("USER");
            JsonElement jelement = new JsonParser().parse(request.body());
            JsonObject jobj = jelement.getAsJsonObject();
            ScheduleBuilder sch = new ScheduleBuilder(user);
            Database dbc = new Database();
            if (sch.loadDegreeIntoSchedule(jobj.get("degree").getAsString(), jobj.get("year").getAsInt(), dbc.getConn())) {
                response.status(200);
                return "HTTP 200 - OK";
            } else {
                response.status(500);
                return "OWLCIS failed: HTTP 500 SERVER ERROR";
            }
        });

        /**
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

        /**
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

        /**
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
            System.out.println("CommentReview in MAin for Insert" + testComment);
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

        /**
         * post forum Route
         */
        post(API_LOC + "/fs", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting" + request.body());
            Forum_search s = new Forum_search();
            s.setUserEnteredQues(request.body());
            //setSelectedCourse(request.body());
            List list = s.getAllForum_search();
            response.status(200);
            System.out.println(gson.toJson(list));
            return gson.toJson(list);
        });

        /**
         * get EVERY review route
         */
        get(API_LOC + "/viewallreviews", (request, response) -> {
            try {
                ViewReviews rev = new ViewReviews();
                List list = rev.getEveryReview();
                //List list = Forum_search.getAllForum_search();
                response.type("application/json");
                response.status(200);

                return new Gson().toJson(list);
            } catch (Exception ex) {
                response.status(500);
                return "Error " + ex.getMessage();
            }
        });

        //Forum comment post route
        post(API_LOC + "/fvc", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());
            User user = request.session().attribute("USER");
            Forum_comment c = new Forum_comment();
            c.setUserId(user.getId());
            c.setUserEnteredAns(request.body());
            c.setPostId(postid);
            // post.setUserId();
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {
                    if (c.insertAllForum_comment(dbc.getConn())) {
                        //response.status(200);
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

        /**
         * Delete Review Route
         */
        post(API_LOC + "/review/delete", (request, response) -> {

            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());
            CourseReview rev = new CourseReview();
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {

                    if (rev.deleteReview(dbc.getConn(), request.body())) {
                        //response.status(200);
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

        //forum home to post text, and post it
        post(API_LOC + "/forumcom", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());

            String postId = request.body().substring(11, request.body().indexOf(','));
            System.out.print("post id in main:" + postId);
            //String Question = request.body().substring(request.body().indexOf(',') + 1, request.body().length() - 1);
            //String Question = request.body().substring(request.body().indexOf(',') + 9, request.body().length() - 2);
            String Question = request.body().substring(request.body().indexOf(',') + 9, request.body().length() - 2);
            // String Question = request.body();
            System.out.println("question in Main:" + Question);
            question = Question;
            // postid = request.body();
            postid = postId;
            Forum_ques s = new Forum_ques();
            // s.setPost_id(request.body());
            s.setPost_id(postId);
            List list = s.getQuestion();
            response.status(200);
            System.out.println(gson.toJson(list));
            return gson.toJson(list);
            //return ret;
        });

        //forum home to post text, and post it
        get(API_LOC + "/forumcom", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            Forum_ques s = new Forum_ques();
            s.setPost_id(postid);
            List list = s.getQuestion();
            response.status(200);
            //System.out.println(gson.toJson(question));
            System.out.println(gson.toJson(list));
            return (gson.toJson(list));
            //return ret;
        });

        //forum home to post text, and post it
        get(API_LOC + "/forumComQ", (request, response) -> {
            String ret = "";
            Gson gson = new Gson();
            //Forum_ques s = new Forum_ques();
            //s.setPost_id(postid);
            //List list = s.getQuestion();
            response.status(200);
            System.out.println(gson.toJson(question));
            //System.out.println(gson.toJson(list));
            return (gson.toJson(question));
            //return (question);
            //return ret;
        });

        /**
         * Delete Post Route
         */
        post(API_LOC + "/post/delete", (request, response) -> {

            String ret = "";
            Gson gson = new Gson();
            System.out.println("Starting . " + request.body());
            Forum_post post = new Forum_post();
            Database dbc = new Database();
            if (dbc.getError().length() == 0) {
                try {

                    if (post.deletePost(dbc.getConn(), request.body())) {
                        //response.status(200);
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
