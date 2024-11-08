@echo off

setlocal
set MVN_COMMAND=mvn

:: Check if a module name is provided
if "%1"=="" (
    echo Usage: build-services.bat [service-name]
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
%MVN_COMMAND% -pl %MODULE_NAME% clean install
endlocal
