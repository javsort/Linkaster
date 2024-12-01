#!/bin/bash

# WARNING!!! This script builds the ENTIRE app, takes a while to run and requires a lot of resources.

# Stop and remove all containers and networks
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-full.yml down
docker-compose -f ../docker-compose-frontonly.yml down


:: Build the backend with /.run.bat package
./run.bat package

:: Build the docker images again and run the containers
docker-compose -f ../docker-compose-full.yml build --no-cache

:: Run the containers
docker-compose -f ../docker-compose-full.yml up