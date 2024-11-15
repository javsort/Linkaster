
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/reserveService-1.0-SNAPSHOT.jar /app/reserveService-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8083

# Run the jar file
ENTRYPOINT [ "java", "-jar", "reserveService-1.0-SNAPSHOT.jar" ]