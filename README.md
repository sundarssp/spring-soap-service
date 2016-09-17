# spring-soap-service

This repository has a basic SOAP web service using Spring Boot. It is similar to the getting started example available in the Sprint guide. It also has multiple branches each adds more functionality like logging, ws security to the basic service. Checkout each branch and try testing with SOAP UI.  

**Commands**

* mvn compile
* mvn spring-boot:run

**Testing**

1. Create a new SOAP UI Project 
2. Add the wsdl http://localhost:8080/ws/population.wsdl
3. Create a sample request and submit

**log-payload branch**

1. Added payload logger for logging the incoming and outgoing message
2. Added application.properties with appropriate log level

**usernametoken branch**

1. Added security interceptor
2. Added username token validator
3. Added password call back handler

__For testing__

1. Add outgoing WS Security configuration in SOAP UI
2. Add username header
3. Set the username and password to "user1" and "secret"

**usernametoken-ts branch**

This branch requires both username and timestamp token in the same order. Spring doesn't require any additional code for validating the timestamp. Adding "Timestamp" in the ValidationActions is enough.

1. Added "Timestamp" token

__For testing__

1. Add outgoing WS Security configuration in SOAP UI
2. Add username header
3. Add timestamp header
4. Set the username and password to "user1" and "secret"
