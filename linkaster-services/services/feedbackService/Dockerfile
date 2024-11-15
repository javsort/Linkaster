
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/feedbackService-1.0-SNAPSHOT.jar /app/feedbackService-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8087

# Run the jar file
ENTRYPOINT [ "java", "-jar", "feedbackService-1.0-SNAPSHOT.jar" ]