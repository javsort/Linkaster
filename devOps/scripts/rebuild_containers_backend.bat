:: THIS SCRIPT DOES NOT RE-BUILD THE PROJECT, IT ONLY RE-BUILDS THE CONTAINERS

::
::  rebuild_containers_backend.bat
::  Author: Ortega Mendoza, Javier
::  Date: 2024
::  Code Version: 1.0
::  Availability: https://github.com/javsort/Linkaster
::

@echo off

setlocal

:: Drop all containers and networks
call docker-compose -f ../docker-compose-backend.yml down
call docker-compose -f ../docker-compose-full.yml down
call docker-compose -f ../docker-compose-frontonly.yml down

:: Build the docker images again and run the containers
call docker-compose -f ../docker-compose-backend.yml up --build