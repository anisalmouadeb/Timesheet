From openjdk:15
EXPOSE 8082
ADD /target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-1.3-SNAPSHOT.jar docker-service-timesheet.jar
ENTRYPOINT ["java","-jar","docker-service-timesheet.jar"]
