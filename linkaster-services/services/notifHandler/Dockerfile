
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/notifHandler-1.0-SNAPSHOT.jar /app/notifHandler-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8084

# Run the jar file
ENTRYPOINT [ "java", "-jar", "notifHandler-1.0-SNAPSHOT.jar" ]