FROM openjdk:8
VOLUME /tmp
ADD build/libs/htg-it-wms-0.1.0.jar app.jar
COPY wait-for-it.sh .
COPY application-dev.properties .
RUN ["chmod", "+x", "./wait-for-it.sh"]
EXPOSE 9000
ENTRYPOINT ["./wait-for-it.sh", "database:3306", "--", "java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","app.jar"]
