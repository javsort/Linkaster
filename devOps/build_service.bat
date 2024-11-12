@echo off

setlocal
set MVN_COMMAND=mvnw.cmd

:: Check if service is provided
if "%1"=="" (
    echo Usage: "./build_service.bat serviceName"
    echo serviceName - The name of the service to build
    echo Use this script to build a specific service!
    echo.
    echo Available services:
    echo    logicGateway                - Build the Logic Gateway Service
    echo    userService                 - Build the User Management Service
    echo    moduleManager               - Build the Module Management Service
    echo    cabinetService              - Build the Cabinet Service
    echo    notifHandler                - Build the Notification Service
    echo    timetableService            - Build the Timetable Service
    echo    reserveService              - Build the Reservation Service
    echo    feedbackService             - Build the Feedback Service
    echo    messageHandler              - Build the Message Handler Service
    echo.
    exit /b
)

:: Set the service name based on input
set SERVICE_NAME=

if /i "%1"=="logicGateway" set SERVICE_NAME=/services/logicGateway
if /i "%1"=="userService" set SERVICE_NAME=services/userService
if /i "%1"=="moduleManager" set SERVICE_NAME=services/moduleManager
if /i "%1"=="cabinetService" set SERVICE_NAME=services/cabinetService
if /i "%1"=="notifHandler" set SERVICE_NAME=services/notifHandler
if /i "%1"=="timetableService" set SERVICE_NAME=services/timetableService
if /i "%1"=="reserveService" set SERVICE_NAME=services/reserveService
if /i "%1"=="feedbackService" set SERVICE_NAME=services/feedbackService
if /i "%1"=="messageHandler" set SERVICE_NAME=services/messageHandler

:: If no valid service name was found, display an error & proper usage
if "%SERVICE_NAME%"=="" (
    echo Invalid service name. Please choose a valid service.
    echo run "build_service.bat" to see the list of available services.
    exit /b
)


:: Build the service
echo Building %1...
cd ../linkaster-services
%MVN_COMMAND% -pl %SERVICE_NAME% clean install
endlocal
