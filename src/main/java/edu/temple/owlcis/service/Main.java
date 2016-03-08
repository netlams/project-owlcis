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

        // root API
        get(API_LOC + "/", (request, response) -> "<h1>/ root directory</h1> <p>Try /hello, /hello/yourname, /feedbacks/someid, /redirect</p> ");

        // login
        post("/login", (request, response) -> {
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList("221190717599-nkm4ci5r8eipdiqjbn8n0rr2m4tnvb78.apps.googleusercontent.com"))
                    .setIssuer("accounts.google.com")
                    .build();
            String idTokenString = request.body();

            GoogleIdToken idToken = verifier.verify(idTokenString); // verify if valid google id token

            if (idToken != null) { // valid
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                // Use or store profile information
                request.session().attribute("email", email);
                // CHECK DATABASE FOR USER's ROLENAME AND SET ROLE
                request.session().attribute("role", "member");
                // IF USER NOT FOUND, THEN ADD THIS PERSON

                response.cookie("email", email, 3600); // frontend can only access cookies, so cookies lets frontend knows
                response.cookie("role", "member", 3600); 
                return "BACKEND SAYS OK. User Email: " + email;
            } else {
                System.out.println("Invalid ID token.");
                return "invalid ID";
            }
        });

        get(API_LOC + "/check-user", (request, response) -> {
           return "Your role from cookie is: " + request.cookie("role")
                   + "\n" + "Your email from session is: " + request.session().attribute("email");
        });
    }
}
