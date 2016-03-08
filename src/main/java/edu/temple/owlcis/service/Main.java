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
 */
public class Main implements SparkApplication {

    public static final String API_LOC = "/api";

    /**
     * Executes the backend logic of the app
     */
    @Override
    public void init() {
        staticFileLocation("/public");

        /* root API */
        get(API_LOC + "/", (request, response) -> "<h1>/ root directory</h1> ");

        /* login */
        post("/login", (request, response) -> {
            String ret = null;
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
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                Database dbc = new Database();

                if (SignIn.isValidTempleEmailAddress(email) == true) {
                    // temple email
                    String userRole = SignIn.findUserRole(dbc.getConn(), email, givenName, familyName);
                    if (userRole != null) {
                        // found user
                        response.status(200);
                        request.session().attribute("email", email);
                        request.session().attribute("fname", givenName);
                        request.session().attribute("role", userRole);
                        // frontend can only access cookies, so we setting cookies here
                        response.cookie("email", email, 3600);
                        response.cookie("fname", givenName, 3600);
                        response.cookie("role", userRole, 3600);
                        ret = "User logged in: " + email 
                                + ". \n\"HTTP 200 OK\"";
                        System.out.println(ret);
                    }
                    else {
                        // no such user
                        // TODO
                        System.out.println();
                        ret = "User signed up: " + email 
                                + ". \n\"HTTP 201 - CREATED\"";
                    }
                }
                else {
                    // not temple email
                    response.status(404);
                    ret = "Invalid. Not Temple." 
                            + "\n\"HTTP 403 FORBIDDEN\"";
                }

                dbc.closeConn();
                return ret;
            } else {
                System.out.println("Invalid ID token.");
                return "invalid ID";
            }
        });

        /* check user info */
        get(API_LOC + "/check-user", (request, response) -> {
            return "Your role from cookie is: " + request.cookie("role")
                    + "\n" + "Your email from session is: " + request.session().attribute("email");
        });

    }
}
