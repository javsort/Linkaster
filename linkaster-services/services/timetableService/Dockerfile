
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/timetableService-1.0-SNAPSHOT.jar /app/timetableService-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8082

# Run the jar file
ENTRYPOINT [ "java", "-jar", "timetableService-1.0-SNAPSHOT.jar" ]