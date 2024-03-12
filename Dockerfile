FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/AppMonitoring-1.0.0-Release.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
