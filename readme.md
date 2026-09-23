## Brief explanation of relevant technical decisions
It is a Spring Boot 4.1.1 project, compiled with Java 21.

It is a multi-module Maven project.

The REST API handles 404 and 500 errors occurring during mocked API calls.


## Instructions for launching the application
To build and launch the application, you must execute the following commands consecutively from a terminal, while located in the project's root folder:
```
mvn clean package
java -jar boot/target/myProductApp-boot-1.0.0.jar
```

**NOTES:**
Maven must be pre-installed on the machine and configured in the PATH so that it can be executed from any folder.
Java 21 must be pre-installed on the machine and configured in the PATH as the default Java version so that it can be executed from any folder.


## Instructions for running the tests
To obtain the code coverage and mutant test report, you must execute the following commands consecutively from a terminal, while located in the project's root folder:
```
mvn clean verify -Pmutation-testing
mvn org.pitest:pitest-maven:1.30.0:report-aggregate-module
```

The generated report is located in the project's `./target/pit-reports` folder. To view it, open the `index.html` file.
