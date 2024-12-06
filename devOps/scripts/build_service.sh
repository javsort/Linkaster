#!/bin/bash

#
#  build_service.sh
#  Author: Ortega Mendoza, Javier
#  Date: 2024
#  Code Version: 1.0
#  Availability: https://github.com/javsort/Linkaster
#

# Set the Maven command
MVN_COMMAND="./mvnw"

# Check if service is provided
if [ -z "$1" ]; then
    echo "Usage: ./build_service.sh serviceName"
    echo "serviceName - The name of the service to build"
    echo "Use this script to build a specific service!"
    echo
    echo "Available services:"
    echo "    logicGateway                - Build the Logic Gateway Service"
    echo "    userService                 - Build the User Management Service"
    echo "    moduleManager               - Build the Module Management Service"
    echo "    cabinetService              - Build the Cabinet Service"
    echo "    notifHandler                - Build the Notification Service"
    echo "    timetableService            - Build the Timetable Service"
    echo "    reserveService              - Build the Reservation Service"
    echo "    feedbackService             - Build the Feedback Service"
    echo "    messageHandler              - Build the Message Handler Service"
    echo
    exit 1
fi

# Set the service name based on input
SERVICE_NAME=""
case "$1" in
    logicGateway) SERVICE_NAME="services/logicGateway" ;;
    userService) SERVICE_NAME="services/userService" ;;
    moduleManager) SERVICE_NAME="services/moduleManager" ;;
    cabinetService) SERVICE_NAME="services/cabinetService" ;;
    notifHandler) SERVICE_NAME="services/notifHandler" ;;
    timetableService) SERVICE_NAME="services/timetableService" ;;
    reserveService) SERVICE_NAME="services/reserveService" ;;
    feedbackService) SERVICE_NAME="services/feedbackService" ;;
    messageHandler) SERVICE_NAME="services/messageHandler" ;;
    *) 
        echo "Invalid service name. Please choose a valid service."
        echo "Run ./build_service.sh to see the list of available services."
        exit 1
        ;;
esac

# Build the service
echo "Building $1..."
cd ../../linkaster-services || exit 1
$MVN_COMMAND -pl "$SERVICE_NAME" clean install
