FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/htg-it-wms-0.1.0.jar app.jar
COPY application-dev.properties .
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","app.jar"]
