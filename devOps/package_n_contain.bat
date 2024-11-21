@echo off

setlocal

:: Drop all containers and networks
call docker-compose down

:: Build the services again with /.run.bat package
call ./run.bat package

:: Build the docker images again and run the containers
call docker-compose up --build