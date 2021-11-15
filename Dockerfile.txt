FROM openjdk:8
EXPOSE 8082
ADD target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-1.0.war devops-docker-homework.war
ENTRYPOINT ["java", "-jar", "devops-docker-homework.war"]