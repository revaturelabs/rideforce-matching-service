FROM openjdk:8-jdk-alpine
COPY target/rideshare-matching-service.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/rideshare-matching-service.jar"]
#EXPOSE 9090 #this isn't used yet
