/**
 * CIS4398 Projects 
 * Spring 2016 
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import static spark.Spark.*;
import spark.servlet.SparkApplication;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;

/**
 * The Main class contains the init method for OWLCIS, executing the backend
 * logic of the app.
 *
 */
public class Main implements SparkApplication {

    
    /**
     * Executes the backend logic of the app
     */
    @Override
    public void init() {
        staticFileLocation("/public");

        get("/hello", (request, response) -> "Hello World");

        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        get("/say/*/to/*", (request, response) -> {
            return "Number of splat parameters: " + request.splat().length;
        });

        post("/hello", (request, response)
                -> "Hello World: " + request.body()
        );

        get("/private", (request, response) -> {
            response.status(401);
            return "Go Away!!!";
        });

        get("/feedbacks/:id", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><feedback>" + request.params("id") + "</feedback>";
        });

        get("/protected", (request, response) -> {
            halt(403, "I don't think so!!!");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/api/feedbacks/101");
            return null;
        });

//        get("/", (request, response) -> "<h1>/ root directory</h1> <p>Try /hello, /hello/yourname, /feedbacks/someid, /redirect</p> ");
    }
}