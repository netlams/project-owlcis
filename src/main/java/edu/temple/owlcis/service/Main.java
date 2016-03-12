/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

import static spark.Spark.*;
import spark.servlet.SparkApplication;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Arrays;

/**
 * The Main class contains the init method for OWLCIS, executing the backend
 * logic of the app.
 *
 * @author Dau
 */
public class Main implements SparkApplication {

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

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("Valid Google token.");

                // Get profile information from payload
                String email = payload.getEmail();
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                User user = new User((String) payload.get("family_name"), 
                        (String) payload.get("given_name"), 
                        payload.getEmail());// TODO
                
                Database dbc = new Database();

                /* Check if email is from Temple University */
                if (SignIn.isValidTempleEmailAddress(email) == true) {

                    String userRole = SignIn.findUserRole(dbc.getConn(), email, givenName, familyName);
                    if (userRole != null) {
                        // found user
                        response.status(200);
                        request.session(true);
                        request.session().attribute("EMAIL", email);
                        request.session().attribute("FNAME", givenName);
                        request.session().attribute("ROLE", userRole);
                        // frontend can only access cookies, so we setting cookies here
                        response.cookie("EMAIL", email, 3600);
                        response.cookie("FNAME", givenName, 3600);
                        response.cookie("ROLE", userRole, 3600);
                        ret = "User logged in: " + email
                                + ". \n\"HTTP 200 OK\"";
                        System.out.println(ret);
                    } else {
                        // no such user
                        // add new user
                        SignUp.addNewUser();
                        // notify client to ask for additional info
                        response.status(201);
                        System.out.println();
                        ret = "User signed up: " + email
                                + ". \n\"HTTP 201 - CREATED\"";
                    }
                } else {
                    // not temple email
                    response.status(403);
                    ret = "Invalid credentials. Non-Temple."
                            + "\n\"HTTP 403 FORBIDDEN\"";
                }

                dbc.closeConn();
                return ret;
            } else {
                System.out.println("Invalid ID token.");
                return "invalid ID";
            }
        });

        /* Signout Route */
        get("/signout", (request, response) -> {
            String ret = "";

            /* check if user is logged in or not */
            if (!request.session().isNew() && request.cookie("email") != null) {
                /* remove these sessions */
                request.session().removeAttribute("EMAIL");
                request.session().removeAttribute("FNAME");
                request.session().removeAttribute("ROLE");
                /* remove these cookies */
                response.removeCookie("EMAIL");
                response.removeCookie("FNAME");
                response.removeCookie("ROLE");
                /* redirect back to homepage */
                response.redirect("/");
                ret = "User is signed out.";
            } else {
                response.status(400);
                ret = "\"HTTP 400 BAD REQUEST\"";
            }

            return ret;
        });

        /* check user info */
        get(API_LOC + "/check-user", (request, response) -> {
            return "Your role from cookie is: " + request.cookie("ROLE")
                    + "\n" + "Your email from session is: " 
                    + request.session().attribute("EMAIL");
        });

    }
}
