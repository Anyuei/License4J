FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/license-platform-0.0.1-SNAPSHOT.jar /license-platform.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/license-platform.jar"]