# Home Improvement - Profile-Service
This project ist part of the HomeImprovement application and provides a backend service to get information for a specific user, based on a JSON Web Token (JWT).

## How to configure the project
To run the project, you need to install the following technologies:
- Java
- Maven
- Application Server (Payara Micro is recommended)

After the sucessfull installation of these technologies, you can clone this project and open it in your preferred IDE (e.g. Apache NetBeans).

## How to run the project
To run the project you need an application server. For this purpose, Payara Micro is recommended. Before you are able to deploy the application, make sure to create a .war file with the command `mvn package`. This package then can be deployed to the application server. In the case that the this project and the payara micro jar file are in the same folder you can deploy the application with the following command: `java -jar payara-micro-yourversion.jar --deploy path/to/app.war`. Make sure that the path and the payara micro jar file are written correctly.
