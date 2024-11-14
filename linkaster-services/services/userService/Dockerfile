
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/userService-1.0-SNAPSHOT.jar /app/userService-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8081

# Run the jar file
ENTRYPOINT [ "java", "-jar", "userService-1.0-SNAPSHOT.jar" ]