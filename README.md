# How to deploy to server:
NOTE: if Database.java is pointing to localhost:3307, change that to localhost:3306 since our mysql server is always listening on 3306.</br>
1.) Clean and Build </br>
2.) Delete old "ROOT" folder on server at /opt/tomcat/webapps/ </br>
3.) Navigate to your local NetBeanProjects/owlcisservice/target folder </br>
4.) Move owlcisservice-1.0 folder to server's /opt/tomcat/webapps/ </br>
5.) Rename the folder to ROOT on server </br>
6.) test on browser </br>

# How to run database connections:
Tunnel in using some command-line interface: terminal, SSH Secure Shell, or PuTTy. 
> Command: <code>ssh -L LOCAL_PORT_USED:MYSQL_SERVER:SERVER_PORT YOUR_NAME@owlcis.me</code> 

NOTE: LOCAL_PORT_USED is the number that you used in Database.java's dbUrl
> Example: <code>ssh -L 3307:localhost:3306 NAME@owlcis.me</code> or <code>ssh -L 3306:localhost:3306 NAME@owlcis.me</code>
