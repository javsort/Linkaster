
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/messageHandler-1.0-SNAPSHOT.jar /app/messageHandler-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8086

# Run the jar file
ENTRYPOINT [ "java", "-jar", "messageHandler-1.0-SNAPSHOT.jar" ]