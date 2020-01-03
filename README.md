vaadintooltipinmenuitem
=======================

This demo was derived from a Vaadin Template for Testbench to:

Demonstrate the problem in [Vaadin GitHub issue #11212 - The Tooltip of a MenuItem inside a Window is not displayed](https://github.com/vaadin/framework/issues/11212)


Workflow
========

To run the integration-test demonstrating the problem run "mvn verify"

To compile the entire project, run "mvn install".

To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"
