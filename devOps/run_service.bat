@echo off

setlocal
set MVN_COMMAND=mvnw.cmd

:: Check if service is provided
if "%1"=="" (
    echo Usage: "./run_service.bat serviceName"
    echo serviceName - The name of the service to build
    echo Use this script to run a specific service!
    echo.
    echo Available services:
    echo    logicGateway                - Run the Logic Gateway Service
    echo    userService                 - Run the User Management Service
    echo    moduleManager               - Run the Module Management Service
    echo    cabinetService              - Run the Cabinet Service
    echo    notifHandler                - Run the Notification Service
    echo    timetableService            - Run the Timetable Service
    echo    reserveService              - Run the Reservation Service
    echo    feedbackService             - Run the Feedback Service
    echo    messageHandler              - Run the Message Handler Service
    echo.
    exit /b
)

:: Set the service name based on input
set SERVICE_NAME=

if /i "%1"=="logicGateway" set SERVICE_NAME=logicGateway
if /i "%1"=="userService" set SERVICE_NAME=userService
if /i "%1"=="moduleManager" set SERVICE_NAME=moduleManager
if /i "%1"=="cabinetService" set SERVICE_NAME=cabinetService
if /i "%1"=="notifHandler" set SERVICE_NAME=notifHandler
if /i "%1"=="timetableService" set SERVICE_NAME=timetableService
if /i "%1"=="reserveService" set SERVICE_NAME=reserveService
if /i "%1"=="feedbackService" set SERVICE_NAME=feedbackService
if /i "%1"=="messageHandler" set SERVICE_NAME=messageHandler

:: If no valid service name was found, display an error & proper usage
if "%SERVICE_NAME%"=="" (
    echo Invalid service name. Please choose a valid service.
    echo run "run_service.bat" to see the list of available services.
    exit /b
)

:: Run the service .jar 
echo Running %1...
cd ../linkaster-services
java -jar services/%SERVICE_NAME%/target/%SERVICE_NAME%-1.0-SNAPSHOT.jar

endlocal
