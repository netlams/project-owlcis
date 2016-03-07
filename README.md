<h1>How to deploy to server:</h1>
1.) Delete old api folder on server
2.) Move NetBeanProjects/owlcisservice/target/owlcisservice-1.0/ to /opt/tomcat/webapps/
3.) Rename the new folder on server
4.) browser test

<h1>How to run database connections</h1>
Tunnel in using some command-line interface: terminal, SSH Secure Shell, or PuTTy. <code>ssh -L 3306:localhost:3306 NAME@owlcis.me</code>
