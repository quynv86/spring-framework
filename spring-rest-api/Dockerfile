FROM openjdk:8-jdk-alpine
ARG JAR_FILE="target/spring-rest-api-1.0-SNAPSHOT.jar"
RUN mkdir app
COPY ${JAR_FILE} app/application.jar
ENTRYPOINT ["java","-jar","app/application.jar"]