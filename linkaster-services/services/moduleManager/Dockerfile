
FROM openjdk:21-jdk-slim

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/moduleManager-1.0-SNAPSHOT.jar /app/moduleManager-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8085

# Run the jar file
ENTRYPOINT [ "java", "-jar", "moduleManager-1.0-SNAPSHOT.jar" ]