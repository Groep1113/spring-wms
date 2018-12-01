FROM openjdk:10-jre-slim as productionstage
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY application.properties .
COPY gradlew .
COPY gradle gradle
RUN ["./gradlew", "build"]
ENTRYPOINT ["./gradlew", "bootRun"]