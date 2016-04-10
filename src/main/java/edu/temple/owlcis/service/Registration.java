/**
 * CIS4398 Projects Spring 2016 2/25/2016
 */
package edu.temple.owlcis.service;

/**
 * A first time User can create a new account. The information that the user
 * provides will be saved into DB.
 *
 * @author Mounya
 */
public class Registration {

    //Global variables
    private String email;
    private String password;
    private String userName;
    private String major;

    /**
     * constructor for the individual Registration account
     */
    protected Registration() {
        userName = "";
        password = "";
        major = "";
        email = "";
    }

    //Set methods 
    protected void SetEmail(String Email) {
        email = Email;
    }

    protected void SetPassword(String Password) {
        password = Password;
    }

    protected void SetUsername(String UserName) {
        userName = UserName;
    }

    protected void SetMajor(String Major) {
        major = Major;
    }

    /**
     * Stores the User details in a table called User in the DB Returns true if
     * insertion into DB was successful
     */
    private boolean createNewAccount() {
        return true;
    }

}
