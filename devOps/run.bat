@echo off

setlocal
set MVN_COMMAND=mvnw.cmd

if "%1"=="" (
    echo Usage: "./run.bat [command]"
    echo Use this script for [command] the project!
    echo Available commands:
    echo    build      - Clean install and build the project
    echo    buildWlogs - Clean install and build with full logging
    echo    package    - Package the project
    echo    validate   - Validate the project
    echo    test       - Run all tests
    echo    clean      - Clean the project
    echo    help       - Show usage
    exit /b
)

if "%1"=="build" (
    cd ../linkaster-services
    call mvnw.cmd clean install

) else if "%1"=="buildWlogs" (
    cd ../linkaster-services
    call mvnw.cmd clean install -X

) else if "%1"=="package" (
    cd ../linkaster-services
    call mvnw.cmd clean package

) else if "%1"=="validate" (
    cd ../linkaster-services
    call mvnw.cmd validate

) else if "%1"=="test" (
    cd ../linkaster-services
    call mvnw.cmd test

) else if "%1"=="clean" (
    cd ../linkaster-services
    call mvnw.cmd clean

) else if "%1"=="help" (
    echo Usage: "./run.bat [command]"
    echo Use this script for [command] the project!
    echo    build      - Clean install and build the project
    echo    buildWlogs - Clean install and build with full logging
    echo    package    - Package the project
    echo    validate   - Validate the project
    echo    test       - Run all tests
    echo    clean      - Clean the project
    echo    help       - Show usage
) else (
    echo Invalid command
    echo Type "./run.bat help" for usage.

)
