@echo off

setlocal
set MVN_COMMAND=mvn

:: Check if a module name is provided
if "%1"=="" (
    echo Usage: run_services.bat [service-name]
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

:: Set the module name based on the input argument
set MODULE_NAME=

if /i "%1"=="logicGateway" set MODULE_NAME=services/logicGateway
if /i "%1"=="userService" set MODULE_NAME=services/userService
if /i "%1"=="moduleManager" set MODULE_NAME=services/moduleManager
if /i "%1"=="cabinetService" set MODULE_NAME=services/cabinetService
if /i "%1"=="notifHandler" set MODULE_NAME=services/notifHandler
if /i "%1"=="timetableService" set MODULE_NAME=services/timetableService
if /i "%1"=="reserveService" set MODULE_NAME=services/reserveService
if /i "%1"=="feedbackService" set MODULE_NAME=services/feedbackService
if /i "%1"=="messageHandler" set MODULE_NAME=services/messageHandler

:: If no valid module name was found, display an error
if "%MODULE_NAME%"=="" (
    echo Invalid service name. Please choose a valid service.
    exit /b
)

:: Run tests for the specified module
java -jar /%MODULE_NAME%/target/%MODULE_NAME%-1.0-SNAPSHOT.jar

endlocal
