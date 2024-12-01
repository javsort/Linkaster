@echo off

:: WARNING!!! This script builds the ENTIRE app, takes a while to run and requires a lot of resources.

setlocal

:: Drop all containers and networks - no matter if w or w/o backend
call docker-compose -f ../docker-compose-backend.yml down
call docker-compose -f ../docker-compose-full.yml down
call docker-compose -f ../docker-compose-frontonly.yml down


:: Build the backend with /.run.bat package
call ./run.bat package

:: Build the docker images again and run the containers
call docker-compose -f ../docker-compose-full.yml build --no-cache

:: Step 1: Build the frontend with no cache
call docker-compose -f ../docker-compose-full.yml up
