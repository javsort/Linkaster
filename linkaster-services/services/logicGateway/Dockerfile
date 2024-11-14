
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file 
COPY target/logicGateway-1.0-SNAPSHOT.jar /app/logicGateway-0.0.1-SNAPSHOT.jar

# Expose port:
EXPOSE 8080

# Run the jar file
ENTRYPOINT [ "java", "-jar", "logicGateway-0.0.1-SNAPSHOT.jar" ]