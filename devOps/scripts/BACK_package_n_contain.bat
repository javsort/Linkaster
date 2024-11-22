@echo off

:: USE this script ONLY to build the backend and run the backend containers

setlocal

:: Drop all containers and networks
call docker-compose -f ../docker-compose-backend.yml down
call docker-compose -f ../docker-compose-full.yml down
call docker-compose -f ../docker-compose-frontonly.yml down

:: Build the services again with /.run.bat package
call ./run.bat package

:: Build the docker images again and run the containers
call docker-compose -f ../docker-compose-backend.yml up --build