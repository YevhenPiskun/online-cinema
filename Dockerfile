FROM openjdk:18-ea-11-jdk-alpine3.14
ARG JAR_FILE=target/online-cinema-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]