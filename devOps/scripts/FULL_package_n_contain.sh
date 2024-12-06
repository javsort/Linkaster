#!/bin/bash

#
#  FULL_package_n_contain.sh
#  Author: Ortega Mendoza, Javier
#  Date: 2024
#  Code Version: 1.0
#  Availability: https://github.com/javsort/Linkaster
#

# WARNING!!! This script builds the ENTIRE app, takes a while to run and requires a lot of resources.

# Ensure necessary scripts are executable
chmod +x ./run.sh

# Stop and remove all containers and networks
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-full.yml down
docker-compose -f ../docker-compose-frontonly.yml down

# Build the backend with /.run.bat package
./run.sh package

# Build the docker images again and run the containers
docker-compose -f ../docker-compose-full.yml build --no-cache

# Run the containers
docker-compose -f ../docker-compose-full.yml up