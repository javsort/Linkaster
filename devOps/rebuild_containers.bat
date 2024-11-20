:: THIS SCRIPT DOES NOT RE-BUILD THE PROJECT, IT ONLY RE-BUILDS THE CONTAINERS

@echo off

setlocal

:: Drop all containers and networks
call docker-compose down

:: Build the docker images again and run the containers
call docker-compose up --build