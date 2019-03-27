# FROM openjdk:8-jdk-alpine
# ARG URL
# ENV JDBC_URL=$URL
# ARG USER
# ENV JDBC_USERNAME=$USER
# ARG PASS
# ENV JDBC_PASSWORD=$PASS
# COPY target/rideshare-matching-service.jar /opt/lib/
# ENTRYPOINT ["/usr/bin/java"]
# CMD ["-jar", "/opt/lib/rideshare-matching-service.jar"]
FROM java:8
COPY target/rideshare-matching-service.jar /tmp/rideshare-matching-service.jar
CMD ["java", "-jar", "/tmp/rideshare-matching-service.jar","--server.servlet.context-path=/matching","&"]
#EXPOSE 9090 #this isn't used yet
FROM java:8
COPY target/rideshare-matching-service.jar /tmp/rideshare-matching-service.jar
CMD ["java", "-jar", "/tmp/rideshare-matching-service.jar","--server.servlet.context-path=/matching","&"]
