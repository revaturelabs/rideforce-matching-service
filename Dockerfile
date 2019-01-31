FROM java:8
COPY target/rideshare-matching-service.jar /tmp/rideshare-matching-service.jar
CMD ["java", "-jar", "/tmp/rideshare-matching-service.jar","--server.servlet.context-path=/matching","&"]
#EXPOSE 9090 #this isn't used yet
