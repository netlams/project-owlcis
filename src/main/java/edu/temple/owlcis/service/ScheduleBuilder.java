/**
 * CIS4398 Projects
 * Spring 2016
 * 2/25/2016
 */
package edu.temple.owlcis.service;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The purpose of the ScheduleBuilder class is to provide
 * users with the ability to build a course schedule to save for personal
 * reference. The schedule will store the semesters and the desired courses for
 * each semester. The class provides methods to add and delete courses from a
 * schedule. Methods are also included to save the schedule or remove it from
 * the database. Finally, a method is included that allows users to generate a
 * flowchart from a previously built schedule.
 *
 * @author Rachel Tritsch, Dau
 */
public class ScheduleBuilder {

    //Global variables
    private String username; //the username of the student who created the schedule (e.g., tue11223@temple.edu)
    private String creationDateTime; //the time and date at which the schedule was created

    public ScheduleBuilder() {
        ;
    }

    /**
     * the constructor for the ScheduleBuilder object
     *
     * @param un the student's username
     */
    public ScheduleBuilder(String un) {
        username = un;
    }

    /**
     * Purpose: add courses to a schedule Pre-conditions: the schedule does not
     * contain the course to be added Post-conditions: the schedule contains the
     * course
     *
     * @param courseID the ID of the course to be added to the schedule
     * @return true if the course was successfully added to the schedule; false
     * if it was not added
     */
    public boolean addCourse(String courseID) {
        return true;
    }

    /**
     * Purpose: delete courses from a schedule Pre-conditions: the schedule
     * contains the course to be deleted Post-conditions: the schedule does not
     * contain the course
     *
     * @param courseID the ID of the course to be deleted from the schedule
     * @return true if the course was successfully deleted from the schedule;
     * false if it was not deleted
     */
    public boolean deleteCourse(String courseID) {
        return true;
    }

    /**
     * Purpose: save a schedule to the database Post-conditions: the database
     * contains the schedule
     *
     * @return true if schedule was successfully inserted into database; false
     * if schedule was not inserted
     */
    public boolean insertIntoDB() {
        //NOTE: the creation date and time will be determined here
        return true;
    }

    /**
     * Purpose: remove a schedule from the database Pre-conditions: the database
     * has a previously saved schedule to be removed Post-conditions: the
     * database does not contain any saved schedule associated with the current
     * user
     *
     * @return true if schedule was successfully removed from database; false if
     * schedule was not removed
     */
    public boolean removeFromDB() {
        return true;
    }

    /**
     * Purpose: generate a flowchart based off of a generated schedule
     * Post-conditions: a flowchart is generated and displayed on the webpage
     * for the user
     *
     * @return Map if flowchart was successfully generated; false if flowchart
     * was not generated
     */
    public List generateFlowchart() {
        /**
         * DUMMY TEST
         */
        // ********* csbsDegree requirements
        Map<String, List<String>> csbsDegree = new HashMap<>();
        //set up
        //prereq
        List<String> math1022 = new ArrayList<>();
        math1022.add("MATH 1022");
        //map set up (REQUIREMENTS)
        csbsDegree.put("CIS 1068", math1022);
        csbsDegree.put("CIS 1166", math1022);
        csbsDegree.put("CIS 2033", Arrays.asList("CIS 1068", "MATH 1041"));
        csbsDegree.put("CIS 2107", Arrays.asList("CIS 1068"));
        csbsDegree.put("CIS 2166", Arrays.asList("CIS 1166", "CIS 1068", "MATH 1041"));
        csbsDegree.put("CIS 2168", Arrays.asList("CIS 1166", "CIS 1068", "MATH 1022"));
        csbsDegree.put("CIS 2308", Arrays.asList("CIS 2107", "CIS 2168"));
        csbsDegree.put("CIS 3207", Arrays.asList("CIS 1166", "CIS 2107", "CIS 2168"));
        csbsDegree.put("CIS 3223", Arrays.asList("CIS 2166", "MATH 1042"));
        csbsDegree.put("CIS 3238", Arrays.asList("CIS 2166", "CIS 3207"));
        csbsDegree.put("CIS 4398", Arrays.asList("CIS 2166", "CIS 3238"));
        csbsDegree.put("MATH 1022", Collections.emptyList());
        csbsDegree.put("MATH 1041", math1022);
        csbsDegree.put("MATH 1042", Arrays.asList("MATH 1041"));
        csbsDegree.put("CIS 1001", Collections.emptyList());

        System.out.println("degree: " + csbsDegree);
        // ******** ^done for csbsDegree req

        // ******** sample student desired course list
        ArrayList<String> courseList = new ArrayList<>();
        Map<String, Integer> completedList = new TreeMap<>();
//        completedList.put("MATH 1022", 0); // testing, when I add 1022 it allowed 1166 and 1068 on first pass
        courseList.add("CIS 1001");
        courseList.add("CIS 2308");
        courseList.add("CIS 2107");
        courseList.add("CIS 1166");
        courseList.add("CIS 1068");
        courseList.add("MATH 1022");
        courseList.add("HIST 1001"); // irrelevant course

        // *** 04/09 - A real student schedule costs of schedules (cid and semstr)
        HashMap<String, Schedule> studentScheduleMap = new HashMap<>();
        ArrayList<Schedule> scheduleFromDB = new ArrayList<>();
        scheduleFromDB.add(new Schedule("CIS 1001", "FA15"));
        scheduleFromDB.add(new Schedule("CIS 2308", "SP16"));
        scheduleFromDB.add(new Schedule("CIS 2107", "SP16"));
        scheduleFromDB.add(new Schedule("CIS 1166", "FA15"));
        scheduleFromDB.add(new Schedule("CIS 1068", "FA15"));
        scheduleFromDB.add(new Schedule("MATH 1022", "FA15"));
        scheduleFromDB.add(new Schedule("HIST 1001", "SP16"));
        for (int i = 0; i < scheduleFromDB.size(); i++) {
            studentScheduleMap.put(scheduleFromDB.get(i).getCourseID(),
                    new Schedule(scheduleFromDB.get(i).getCourseID(), scheduleFromDB.get(i).getSemester()));
        }
        System.out.println("Map of student schedule: " + studentScheduleMap);
        // ******** ^done sample course list

        // ******** comparsion start
        //list of all courses that are relevant (those that matters for the csbsDegree)
        LinkedList<String> matchedDegreeCourseList = courseList
                .stream()
                .filter(c -> csbsDegree.containsKey(c))
                .collect(Collectors.toCollection(LinkedList::new));
        //list of leftover requirements
        LinkedList<String> remainingDegreeCourseList = csbsDegree
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .filter(c -> !matchedDegreeCourseList.contains(c))
                .collect(Collectors.toCollection(LinkedList::new));
        //map of student schedule to be made into FLOWCHART!!
        TreeMap<String, List<Schedule>> flowchart = new TreeMap<>();
        System.out.println("matched courses: " + matchedDegreeCourseList);
        System.out.println("remaining courses: " + remainingDegreeCourseList);

        Integer semesterCnt = 1;
        boolean needPass = true; // controls the number of pass (if !needPass, then cancel while loop)
        while (needPass || !matchedDegreeCourseList.isEmpty()) { // loop this based on the number courses in matched list
            List<String> newPass = matchedDegreeCourseList.stream().collect(Collectors.toList());
            //list to keep track of what is allowed on each pass
            List<Schedule> allowedList = new LinkedList<>();
            for (String course : newPass) {
                System.out.println("Comparing " + course + ", req: " + csbsDegree.get(course).toString());
                if (csbsDegree.get(course).isEmpty()) {
                    allowedList.add(studentScheduleMap.get(course)); //allowed to student schedule
                    completedList.put(course, semesterCnt);//add to current semester, will be moving to next semester for next pass
                    matchedDegreeCourseList.remove(course);//no longer needed to review this next pass, so take out
                } else {
                    for (String prereq : csbsDegree.get(course)) { // get the prerequisites for this course
                        if (completedList.containsKey(prereq)) { //does student's history meet prereq?
                            int takenSemester = completedList.get(prereq); // when did student took this? (for non-concurrent requirement)
                            if (takenSemester < semesterCnt) { //if taken at an older/earlier semester 
                                allowedList.add(studentScheduleMap.get(course));//meets the prerequirsite, so allowed to student schedule
                                completedList.put(course, semesterCnt);//add to current semester, will be moving to next semester for next pass
                                matchedDegreeCourseList.remove(course);//no longer needed to review this
                            }
                        }
                    }
                }
            }
            needPass = (newPass.size() == matchedDegreeCourseList.size()) ? false : true;
//            flowchart.put(semesterCnt.toString(), allowedList);
            System.out.println("\n FINISHED PASS #" + semesterCnt + ", need pass? " + needPass);
            System.out.println("->" + allowedList);
            if (needPass) {
                flowchart.put(semesterCnt.toString(), allowedList);
                semesterCnt++;
            }
        }

        needPass = true;
        // remaining req
        while (needPass || !remainingDegreeCourseList.isEmpty()) { // loop this based on the number courses in matched list
            List<String> newPass = remainingDegreeCourseList.stream().collect(Collectors.toList());
            //list to keep track of what is allowed on each pass
            List<Schedule> allowedList = new LinkedList<>();
            for (String course : newPass) {
                System.out.println("NICE BOY " + course + ", req: " + csbsDegree.get(course).toString());
                if (csbsDegree.get(course).isEmpty()) {
                    allowedList.add(new Schedule(course)); //allowed to student schedule
                    completedList.put(course, semesterCnt);//add to current semester, will be moving to next semester for next pass
                    remainingDegreeCourseList.remove(course);//no longer needed to review this next pass, so take out
                } else if (csbsDegree.get(course).size() == 1) {
                    String prereq = csbsDegree.get(course).get(0);  // get the prerequisites for this course
                    if (completedList.containsKey(prereq)) { //does student's history meet prereq?
                        int takenSemester = completedList.get(prereq); // when did student took this? (for non-concurrent requirement)
                        if (takenSemester < semesterCnt) { //if taken at an older/earlier semester 
                            allowedList.add(new Schedule(course));//meets the prerequirsite, so allowed to student schedule
                            completedList.put(course, semesterCnt);//add to current semester, will be moving to next semester for next pass
                            remainingDegreeCourseList.remove(course);//no longer needed to review this
                        }
                    }
                } else {
                    boolean flag = true;
                    for (String prereq : csbsDegree.get(course)) {
                        System.out.println("\tTEST: " + prereq);
                        //does student's history meet prereq?
                        if (flag && !completedList.containsKey(prereq)) {
                            flag = false;
                        } else //completedList contains this, now we check if this is concurrent
                        if (flag) {
                            flag = (completedList.get(prereq) < semesterCnt) ? true : false;
                        }
                    }
                    if (flag) {
                        allowedList.add(new Schedule(course));//meets the prerequirsite, so allowed to student schedule
                        completedList.put(course, semesterCnt);//add to current semester, will be moving to next semester for next pass
                        remainingDegreeCourseList.remove(course);//no longer needed to review this
                    }
                }
            }
            needPass = (newPass.size() == remainingDegreeCourseList.size()) ? false : true;

            System.out.println("\n FINISHED PASS #" + semesterCnt + ", need pass? " + needPass);
            System.out.println("->" + allowedList);
            if (needPass) {
                flowchart.put(semesterCnt.toString(), allowedList);
                semesterCnt++;
            }
        }

//        System.out.println("allowedList: " + allowedList);
        System.out.println("remainders: " + matchedDegreeCourseList);
        // ******** ^comparsion end

        List<List> returnList = new LinkedList<List>(flowchart.values());

        return returnList;

        /**
         * ^ DUMMY TEST
         */
//
//        StudentFlowchart flow = new StudentFlowchart();
//        // query 'takes' db, ORDFER BY semester (FA, SP, SU)
//            // take each row and store in StudentSemester; increment semesterCnt
//        // add to list
//        Schedule element = new Schedule(new String("MATH 0701"), new String("FL15"));
//        Schedule algebra = new Schedule(new String("MATH 1021"), new String("SP16"));
//        Schedule precal = new Schedule(new String("MATH 1022"), new String("FL16"));
//        Schedule intro = new Schedule(new String("CIS 1001"), new String("FL15"));
//        Schedule abst = new Schedule(new String("CIS 1068"), new String("FL15"));
//        Schedule mathc = new Schedule(new String("CIS 1166"), new String("FL15"));
//        System.out.println("ELEMENT    "+element.toString());
//        CourseNode tree = new CourseNode("Start", "null");
//        tree.addCourse(element);
//        tree.getChildren().get(0).addCourse(algebra);
//        tree.getChildren().get(0).getChildren().get(0).addCourse(precal);
//        tree.addCourse(intro);
//        tree.getChildren().get(1).addCourse(abst);
//        tree.getChildren().get(1).getChildren().get(0).addCourse(mathc);
//
//        flow.getSemesterList().add(tree);
//
//        ArrayList<HeaderItem> list = new ArrayList<>();
//        list.add(new HeaderItem("FA15", 1));
//        list.add(new HeaderItem("SP16", 2));
//        list.add(new HeaderItem("FA16", 3));
//        flow.setHeader(list);
////        flow.setSemesterList(tree);
//        return flow;
    }

    public List getSchedule() {

        return null;
    }

}
