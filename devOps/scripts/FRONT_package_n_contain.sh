#!/bin/bash

#
#  FRONT_package_n_contain.sh
#  Author: Ortega Mendoza, Javier
#  Date: 2024
#  Code Version: 1.0
#  Availability: https://github.com/javsort/Linkaster
#

#  USE this script ONLY to build the front-end and run the front-end containers

# Drop all containers and networks - no matter if w or w/o backend
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-full.yml down
docker-compose -f ../docker-compose-frontonly.yml down

# Build the frontend image and run it
docker-compose -f ../docker-compose-frontonly.yml build --no-cache

# Run the frontend container
docker-compose -f ../docker-compose-frontonly.yml up