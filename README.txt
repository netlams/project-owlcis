OWL CIS v1.0

Contributors: Dhruvin Sheth, Dau Lam, Rachel Tritsch, Jeff Heckman, Mounya Puppala
----------------------------------------------------------------------------------

Release Date:
------------
April 25, 2016

What is it?
-----------

The OWL CIS is a web-based app that helps Temple University Computer and Information Science Students
to view course review, build and generate their schedules and flowcharts. It also helps them to
stay on track.

The Latest Version: v1.0
------------------------

Details of the latest version can be found on the github  https://github.com/netlams/project-owlcis.
It is a private repository. Please contact developers to get latest version of it.

Installation and Configuration
------------------------------

Please see the document called User Manual for detailed instructions.

Configuration:
Project is currently set to connect to our mysql server at http://owlcis.me. Configure the system to communicate with your database. Locate the Database.java from Backend system directory and update the three strings: dbUrl, username, passwd to your database credentials. (Note: comments in source)
Backend system is located in owlcisservice/src/main/java/edu/temple/owlcis/service/
Frontend system is located in owlcisservice/src/main/resources/public/

How to Build and Run:
Note: This is a Maven project and requires a java-enabled web server such as Apache Tomcat or Glassfish, Jave 8, etc. Please refer to the Requirements document. Reccomend you use NetBeans (Java EE or Complete bundle) 8.0.2.
1. Tunnel in using some command-line interface: terminal (bash), SSH Secure Shell, or PuTTy.
Sample Command: ssh -L LOCAL_PORT_USED:MYSQL_SERVER:SERVER_PORT YOUR_NAME@owlcis.me
NOTE: LOCAL_PORT_USED is the number that you used in Database.java's dbUrl variable (usually 3307 or 3306)
Example: ssh -L 3307:localhost:3306 USERNAME@owlcis.me or ssh -L 3306:localhost:3306 USERNAME@owlcis.me

2. Open project in NetBeans
3. Clean and Build (right-click on project)
4. If all tests pass, Run it (right-click on project)
5. Browser opens up

How to Deploy (to our owlcis.me server):
NOTE: Refer to the User Manual document for login credentials. Don't share it :-)
NOTE: if Database.java is pointing to localhost:3307, change that to localhost:3306 since our mysql server is always listening on 3306.
1. Clean and Build (right-click on project)
2. Delete old "ROOT" folder on server at /opt/tomcat/webapps/
3. Navigate to your local NetBeanProjects/owlcisservice/target folder
4. Move owlcisservice-1.0 folder to server's /opt/tomcat/webapps/
5. Rename the folder to ROOT on server
6. test by going to http://owlcis.me

New Features in this version:
----------------------------

The following features have been implemented in OWL CIS successfully.
Schedule/ flow chart:
Flow chart generated for first time users (freshmen)
Able to add and delete courses from their lists.
Personalized flowchart for each user and save it in PDF
Able to print their schedule, download it in either excel or pdf.

Course Reviews:
Members can post course reviews
Users can express their views by clicking it on thumbs up and down buttons.
Members can see comments posted by advisors.
Advisors can post comment on course reviews.
Users can search for reviews by selecting course id dropdown menu
Anyone can search for course reviews

Profile:
Users can manage their profiles.
Users can add completed courses, delete courses or view completed courses.
Users can update their profiles after signing up

Forum:
Members can post their questions in forum
Members and advisors will able to reply to questions.
Members can search by keyword in forum

Moderator:
Maintain Course Reviews and Forum can flag inappropriate content

Bugs
----
There are few minor bugs in OWL CIS, but it would not have a drastic impact on users or functionality.
1. flowchart download feature creates an imperfect copy (misaligned colors and text).
