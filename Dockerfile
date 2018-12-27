FROM openjdk:8
#COPY build/libs/htg-it-wms-0.1.0.jar app.jar
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
COPY gradle.properties .
COPY wait-for-it.sh .
COPY application-dev.properties .
RUN ["./gradlew", "build"]
RUN ["chmod", "+x", "./wait-for-it.sh"]
EXPOSE 9000
ENTRYPOINT ./wait-for-it.sh $DATABASE_HOST:$DATABASE_PORT -t 30 -- java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=dev -jar build/libs/htg-it-wms-0.1.0.jar
