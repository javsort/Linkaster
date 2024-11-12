@echo off
if "%1"=="" (
    echo Usage: run.bat [command]
    echo Commands:
    echo    build      - Clean and build the project
    echo    buildWlogs - Clean and build the project with full logs
    echo    package    - Package the project
    echo    validate   - Validate the project
    echo    test       - Run all tests
    echo    clean      - Clean the project
    echo    install    - Install all modules locally - clean install
    echo    help       - Show usage
    exit /b
)

if "%1"=="build" (
    call mvnw.cmd clean install
) else if "%1"=="buildWlogs" (
    call mvnw.cmd clean install -X
) else if "%1"=="package" (
    call mvnw.cmd clean package
) else if "%1"=="validate" (
    call mvnw.cmd validate
) else if "%1"=="test" (
    call mvnw.cmd test
) else if "%1"=="clean" (
    call mvnw.cmd clean
) else if "%1"=="install" (
    call mvnw.cmd clean install
) else if "%1"=="help" (
    echo Usage: run.bat [command]
    echo Commands:
    echo    build      - Clean and build the project
    echo    buildWlogs - Clean and build the project with full logs
    echo    package    - Package the project
    echo    validate   - Validate the project
    echo    test       - Run all tests
    echo    validate   - Validate the project
    echo    clean      - Clean the project
    echo    install    - Install all modules locally - clean install
    echo    help       - Show usage
) else (
    echo Invalid command
    echo Type "run.bat help" for usage.
)
