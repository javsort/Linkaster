#
#  Title: timetableService/Dockerfile
#  Author: Ortega Mendoza, Javier
#  Date: 2024
#  Code Version: 1.0
#  Availability: https://github.com/javsort/Linkaster
#

FROM openjdk:21-jdk-slim

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/timetableService-1.0-SNAPSHOT.jar /app/timetableService-1.0-SNAPSHOT.jar

# Expose port:
EXPOSE 8082

# Run the jar file
ENTRYPOINT [ "java", "-jar", "timetableService-1.0-SNAPSHOT.jar" ]