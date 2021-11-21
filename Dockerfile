FROM openjdk:18-ea-11-jdk-alpine3.14
RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz
  && tar xzvf docker-17.04.0-ce.tgz
  && mv docker/docker /usr/local/bin
  && rm -r docker docker-17.04.0-ce.tgz
ARG JAR_FILE=target/online-cinema-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]