/**
 * CIS4398 Projects
 * Spring 2016
 * 4/24/2016
 */
package edu.temple.owlcis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
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
    private Profile profile;//the user of the student who created the schedule (e.g., tue11223@temple.edu)

    /**
     * Default constructor.
     */
    public ScheduleBuilder() {
        this.profile = new Profile();
    }

    /**
     * Parameterized constructor for the ScheduleBuilder object.
     * takes user from sesssion and cast to member
     *
     * @param u the user
     */
    public ScheduleBuilder(User u) throws Exception, NullPointerException {
        this();
        this.profile = new Profile(new Member(u));
        loadMember();
        this.profile.setCompleted((short) 0);
    }

    /**
     * Check if the user is a member by trying to fetch his/her profile.
     *
     * @return True if member; otherwise throw exceptions
     * @throws Exception if any errors
     * @throws NullPointerException if non-existing member
     */
    public boolean loadMember() throws Exception, NullPointerException {
        Database dbc = new Database();
        // Database connection OK
        if (dbc.getError().length() == 0) {
            try {
                if (!profile.fetchProfile(dbc.getConn())) {
                    throw new NullPointerException();
                }
                return true;
            } catch (Exception ex) {
                throw new Exception();
            } finally {
                if (!dbc.getConn().isClosed()) {
                    dbc.closeConn();
                }
            }
        }
        return false;
    }
    
    /**
     * Get user's schedule.
     * 
     * @param conn
     * @return LinkedList of user schedules
     * @throws SQLException if any database error
     */
    public LinkedList getSchedule(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet results = null;
        String sql;
        LinkedList<Schedule> list = new LinkedList<>();

        if (conn != null) {
            try {
                /* Querying takes table ....................... */
                sql = "SELECT takes.course_id, takes.semester, course_title "
                        + "FROM takes INNER JOIN course "
                        + "ON takes.course_id=course.course_id where mem_id=? "
                        + "ORDER BY SUBSTR(takes.semester FROM 3 FOR 4), "
                        + "takes.semester DESC";
                stmt = conn.prepareStatement(sql);
                //Set param
                stmt.setInt(1, this.getProfile().getMember().getId());
                //Execute query
                results = stmt.executeQuery();

                while (results.next()) {
                    Schedule schedule
                            = new Schedule(results.getString("takes.course_id"),
                                    results.getString("course_title"),
                                    results.getString("semester"));
                    list.add(schedule);
                }

                return list;
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                        ;
                    } 
                }
            }
        }
        return null; //will only return false if connection is null
    }

    /**
     * Purpose: add schedule.
     * Pre-conditions: the schedule does not contain the course to be added 
     * Post-conditions: the schedule contains the course
     *
     * @param sch
     * @param conn
     * @return true if the course was successfully added to the schedule; false
     * if it was not added
     * @throws java.sql.SQLException if any database error
     */
    public boolean addSchedule(Schedule sch, Connection conn) throws SQLException {
        this.getProfile().setTakenCourses(this.getSchedule(conn));
        try {
            if (this.getProfile().addTakenCourse(sch, conn)) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            throw new SQLException(ex);
        }
        return false;
    }

    /**
     * Purpose: delete schedule from a schedule.
     * Pre-conditions: the schedule contains the course to be deleted.
     * Post-conditions: the schedule does not contain the course
     *
     * @param sch
     * @param conn
     * @return true if the course was successfully deleted from the schedule;
     * false if it was not deleted
     * @throws java.sql.SQLException if any database error
     */
    public boolean removeSchedule(Schedule sch, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;

        if (conn != null) {
            try {
                sql = "DELETE FROM takes "
                        + "WHERE mem_id = ? "
                        + "AND course_id = ? "
                        + "AND semester = ? ";
                stmt = conn.prepareStatement(sql);

                //Set parameters
                stmt.setInt(1, this.getProfile().getMember().getId());
                stmt.setString(2, sch.getCourseID().toUpperCase());
                stmt.setString(3, sch.getSemester().toUpperCase());

                //Execute query
                stmt.executeUpdate();

                return true;
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                        ;
                    } 
                }
            }
        }
        return false;
    }

    /**
     * Delete all schedules user have in the database. 
     * Pre-conditions: the database has a previously saved schedule to be removed 
     * Post-conditions: the database does not contain any saved schedule 
     * associated with the current user
     * 
     * @param conn
     * @return true if schedule was successfully removed from database; false if
     * schedule was not removed
     * @throws SQLException
     */
    public boolean removeAllSchedule(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        String sql;

        if (conn != null) {
            try {
                sql = "DELETE FROM takes "
                        + "WHERE mem_id = ? ";
                stmt = conn.prepareStatement(sql);

                //Set parameters
                stmt.setInt(1, this.getProfile().getMember().getId());
                //Execute query
                stmt.executeUpdate();
                return true;
            } catch (SQLException ex) {
                //Handle errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new SQLException(ex);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } //ignore
                }
            }
        }
        return false;
    }
    
    /**
     * Load sample schedule list for specified degree into user's schedule
     * @param degree the degree name
     * @param year the declared year
     * @param conn
     * @return true if success; otherwise false
     */
    public boolean loadDegreeIntoSchedule(String degree, int year, Connection conn) {
        StudentFlowchart fc = this.generateFlowchart(degree, year, true);
        int fallSemesterCnt = Integer.parseInt(String.valueOf(year).substring(2));
        int springSemesterCnt = fallSemesterCnt + 1;

        for (int i = 0; i < fc.getList().size(); i++) {
            LinkedList<Schedule> semesterList = (LinkedList) fc.getList().get(i);

            String semester = ((i % 2) == 0) ? "FA" + (fallSemesterCnt++) : "SP" + (springSemesterCnt++);
            for (Schedule sch : semesterList) {
                try {
                    sch.setSemester(semester);
                    this.addSchedule(sch, conn);
                } catch (Exception ex) {
                    System.out.println("Error in ScheduleBuilder.loadDegreeIntoSchedule() " + ex.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Purpose: generate a flowchart based off of a generated schedule
     * Post-conditions: a flowchart is generated and displayed on the webpage
     * for the user
     *
     * @return StudentFlowchart if flowchart was successfully generated; false
     * if flowchart
     * was not generated
     * @param y
     * @param d
     */
    public StudentFlowchart generateFlowchart(String d, int y, boolean randomizeElect) {
        /**
         * Testing stuff
         */
        // ********* csbsDegree requirements
        Degree degree = new Degree(d, y);
        Map<DegreeReq, List<String>> csbsDegree = degree.getDegree();
        ArrayList<DegreeReq> courseList = new ArrayList<>();
        courseList.add(new DegreeReq("CIS 1001", DegreeReq.CORE));
        courseList.add(new DegreeReq("CIS 1166", DegreeReq.CORE));
        courseList.add(new DegreeReq("HIST 3333", DegreeReq.CORE));

//        System.out.println("degree: " + csbsDegree);
        // ******** ^done for csbsDegree req

        // ******** sample student desired course list
        Map<String, Integer> completedList = new TreeMap<>();

        // *** 04/09 - A real student schedule costs of schedules (cid and semstr)
        HashMap<String, Schedule> studentScheduleMap = new HashMap<>();
        ArrayList<Schedule> scheduleFromDB = new ArrayList<>();
        scheduleFromDB.add(new Schedule("CIS 1001", "FA15"));
//        scheduleFromDB.add(new Schedule("CIS 2308", "SP16"));
//        scheduleFromDB.add(new Schedule("CIS 2107", "SP16"));
        scheduleFromDB.add(new Schedule("CIS 1166", "FA15"));
        scheduleFromDB.add(new Schedule("CIS 1068", "FA15"));
//        scheduleFromDB.add(new Schedule("MATH 1022", "FA15"));
//        scheduleFromDB.add(new Schedule("HIST 1001", "SP16"));
        for (int i = 0; i < scheduleFromDB.size(); i++) {
            studentScheduleMap.put(scheduleFromDB.get(i).getCourseID(),
                    new Schedule(scheduleFromDB.get(i).getCourseID(), scheduleFromDB.get(i).getSemester()));
        }
//        System.out.println("Map of student schedule: " + studentScheduleMap);
        // ******** ^done sample course list

        // ******** comparsion start
        LinkedList<DegreeReq> matchedDegreeCourseList = courseList
                .stream()
                .filter(c -> csbsDegree.containsKey(c))
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<DegreeReq> remainingDegreeCourseList = csbsDegree
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .filter(c -> !matchedDegreeCourseList.contains(c))
                .collect(Collectors.toCollection(LinkedList::new));
        //map of student schedule to be made into FLOWCHART!!
        TreeMap<String, List<Schedule>> flowchart = new TreeMap<>();
//        System.out.println("matched courses: " + matchedDegreeCourseList);
//        System.out.println("remaining courses: " + remainingDegreeCourseList);

        Integer semesterCnt = 1;
        int electiveCnt = 0;

        boolean needPass = true; // controls the number of pass (if !needPass, then cancel while loop)
        while (needPass || matchedDegreeCourseList.isEmpty()) { // loop this based on the number courses in matched list
            List<DegreeReq> newPass = matchedDegreeCourseList.stream().collect(Collectors.toList());
            //list to keep track of what is allowed on each pass
            List<Schedule> allowedList = new LinkedList<>();
            for (DegreeReq course : newPass) {
//                System.out.println("Comparing " + course.toString() + ", req: " + csbsDegree.get(course).toString());
                if (csbsDegree.get(course).isEmpty()) {
                    allowedList.add(studentScheduleMap.get(course.toString())); //allowed to student schedule
                    completedList.put(course.toString(), semesterCnt);//add to current semester, will be moving to next semester for next pass
                    matchedDegreeCourseList.remove(course);//no longer needed to review this next pass, so take out
                    if (course.isElective()) {
                        electiveCnt++; // increment elective count
                    }
                } else {
                    for (String prereq : csbsDegree.get(course)) { // get the prerequisites for this course
                        if (completedList.containsKey(prereq)) { //does student's history meet prereq?
                            int takenSemester = completedList.get(prereq); // when did student took this? (for non-concurrent requirement)
                            if (takenSemester < semesterCnt) { //if taken at an older/earlier semester
                                allowedList.add(studentScheduleMap.get(course.toString()));//meets the prerequirsite, so allowed to student schedule
                                completedList.put(course.toString(), semesterCnt);//add to current semester, will be moving to next semester for next pass
                                matchedDegreeCourseList.remove(course);//no longer needed to review this
                                if (course.isElective()) {
                                    electiveCnt++; // increment elective count
                                }
                            }
                        }
                    }
                }
            }
            needPass = (newPass.size() == matchedDegreeCourseList.size()) ? false : true;
//            flowchart.put(semesterCnt.toString(), allowedList);
//            System.out.println("\n FINISHED PASS #" + semesterCnt + ", need pass? " + needPass);
//            System.out.println("->" + allowedList);
            if (needPass) {
                flowchart.put(semesterCnt.toString(), allowedList);
                semesterCnt++;
            }
        }

        semesterCnt = 1; // reset count to beginning
        needPass = true; // control while loop
        // strip out the electives 
        remainingDegreeCourseList = remainingDegreeCourseList.stream()
                .filter(c -> c.isCore())
                .collect(Collectors.toCollection(LinkedList::new));
        if (matchedDegreeCourseList.size() > 0) {
            remainingDegreeCourseList.addAll(matchedDegreeCourseList); // combine the remainding stuff
        }

        // remaining req
        while (needPass || !remainingDegreeCourseList.isEmpty()) { // loop this based on the number courses in matched list
            int coursePerSemesterCnt = 0; // track length (we don't want to overload course work per semester)
            List<DegreeReq> newPass = remainingDegreeCourseList.stream().collect(Collectors.toList());
            //list to keep track of what is allowed on each pass
            List<Schedule> allowedList = new LinkedList<>();
            for (DegreeReq course : newPass) {
//                System.out.println("NICE BOY " + course.toString() + ", req: " + csbsDegree.get(course).toString());
                if (csbsDegree.get(course).isEmpty()) {
                    allowedList.add(new Schedule(course.toString())); //allowed to student schedule
                    completedList.put(course.toString(), semesterCnt);//add to current semester, will be moving to next semester for next pass
                    remainingDegreeCourseList.remove(course);//no longer needed to review this next pass, so take out
                } else if (csbsDegree.get(course).size() == 1) {
                    String prereq = csbsDegree.get(course).get(0);  // get the prerequisites for this course
                    if (completedList.containsKey(prereq)) { //does student's history meet prereq?
                        int takenSemester = completedList.get(prereq); // when did student took this? (for non-concurrent requirement)
                        if (takenSemester < semesterCnt) { //if taken at an older/earlier semester
                            allowedList.add(new Schedule(course.toString()));//meets the prerequirsite, so allowed to student schedule
                            completedList.put(course.toString(), semesterCnt);//add to current semester, will be moving to next semester for next pass
                            remainingDegreeCourseList.remove(course);//no longer needed to review this
                            coursePerSemesterCnt++;
                        }
                    }
                } else {
                    boolean flag = true;
                    for (String prereq : csbsDegree.get(course)) {
//                        System.out.println("\tTEST: " + prereq);
                        //does student's history meet prereq?
                        if (flag && !completedList.containsKey(prereq)) {
                            flag = false;
                        } else //completedList contains this, now we check if this is concurrent
                        if (flag) {
                            flag = (completedList.get(prereq) < semesterCnt) ? true : false;
                        }
                    }
                    if (flag) {
                        allowedList.add(new Schedule(course.toString()));//meets the prerequirsite, so allowed to student schedule
                        completedList.put(course.toString(), semesterCnt);//add to current semester, will be moving to next semester for next pass
                        remainingDegreeCourseList.remove(course);//no longer needed to review this
                        coursePerSemesterCnt++;
                    }
                    if (coursePerSemesterCnt >= 4) { // try to keep it under 4 courses per semester
                        break;
                    }
                }
            }
            needPass = (newPass.size() == remainingDegreeCourseList.size()) ? false : true;

//            System.out.println("\n FINISHED PASS #" + semesterCnt + ", need pass? " + needPass);
//            System.out.println("->" + allowedList);
            if (needPass) {
                List<Schedule> previousList = flowchart.get(semesterCnt.toString());
                if (previousList != null) {
//                    System.out.println("Previously have some courses... ");
                    allowedList.addAll(previousList);
                }
                    flowchart.put(semesterCnt.toString(), allowedList);
                    semesterCnt++;
            }
        }

        int reqElectiveCount = degree.getRequiredElectCnt(); // get required count
        int maxSemester = semesterCnt - 1;
        semesterCnt = semesterCnt - ((reqElectiveCount-1)/(electiveCnt+1)); // rewind some semesters
        // don't add real elective courses
        if (!randomizeElect) {
            // add the min. electives
            while (electiveCnt < reqElectiveCount) {
//                System.out.println("Elective cnt: " + electiveCnt);
                List<Schedule> previousList = flowchart.get(semesterCnt.toString());
                if (previousList.size() >= 4 && semesterCnt < maxSemester) { // check how many courses already in
                    semesterCnt++;
                    previousList = flowchart.get(semesterCnt.toString());
                }
                previousList.add(new Schedule("CIS ELECTIVE"));
                electiveCnt++;
//                System.out.println("added");
                if (electiveCnt == reqElectiveCount) {
                    flowchart.put(semesterCnt.toString(), previousList);
                    break; // fulfilled all electives
                }
                if (previousList.size() >= 4 && semesterCnt < maxSemester) { // check how many courses already in
                    semesterCnt++;
                    previousList = flowchart.get(semesterCnt.toString());
                }
                if (!d.equals("CSM_BS")) {
                    previousList.add(new Schedule("CIS ELECTIVE"));
                } else {
                    previousList.add(new Schedule("MATH ELECTIVE"));
                }
                electiveCnt++;
//                System.out.println("added");
                flowchart.put(semesterCnt.toString(), previousList);
                if (semesterCnt < maxSemester) 
                    semesterCnt++;
            }
        } else { // pick random elective courses to add
            // add the min. electives
            LinkedList<DegreeReq> electList = csbsDegree
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .filter(c -> c.isElective())
                    .collect(Collectors.toCollection(LinkedList::new));

            Random random = new Random();
            LinkedList<Integer> tracker = new LinkedList<>();

            while (electiveCnt < reqElectiveCount) {
//                System.out.println("Elective cnt: " + electiveCnt);
                int rand = random.nextInt(electList.size()); // get random index
                while (tracker.contains(rand)) { // we only want unique electives
                    rand = random.nextInt(electList.size());
                }
                tracker.add(rand);  // track this index
                DegreeReq randomElective = electList.get(rand); // retrieve elective course
//                System.out.println("The random elective: " + randomElective);
                List<Schedule> previousList = flowchart.get(semesterCnt.toString());
                if (previousList.size() >= 4 && semesterCnt < maxSemester) { // check how many courses already in
                    semesterCnt++;
                    previousList = flowchart.get(semesterCnt.toString());
                }
                previousList.add(new Schedule(randomElective.toString()));
                flowchart.put(semesterCnt.toString(), previousList);
                electiveCnt++;
                if (semesterCnt < maxSemester) {
                    semesterCnt++;
                }
            }
        }

//        System.out.println("remainders: " + matchedDegreeCourseList);
        // ******** ^comparsion end

        List<List> returnList = new ArrayList<>(flowchart.values());
        this.getProfile().getMember().setMajor(d);
        StudentFlowchart flow = new StudentFlowchart(returnList, this.getProfile().getMember());
        return flow;
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
