/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 *
 * @author Lam
 */

public class UserStory1_TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(UserTypeTest.class, SignInAndSignUpTest.class);
      for (Failure failure : result.getFailures()) {
            System.out.println("TESTS FAILED.");
            System.out.println(failure.toString());
      }
      System.out.println("ALL TESTS PASSED");
      System.out.println(result.wasSuccessful());
   }
}  
