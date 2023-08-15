FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/license-application-0.0.1-SNAPSHOT.jar /license-application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/license-application.jar"]