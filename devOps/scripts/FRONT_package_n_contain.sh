@echo off

#  USE this script ONLY to build the backend and run the backend containers

setlocal

:: Drop all containers and networks - no matter if w or w/o backend
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-full.yml down
docker-compose -f ../docker-compose-frontonly.yml down

:: Build the frontend image and run it
docker-compose -f ../docker-compose-frontonly.yml build --no-cache

:: Run the frontend container
docker-compose -f ../docker-compose-frontonly.yml up