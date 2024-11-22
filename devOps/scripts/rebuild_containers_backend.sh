#!/bin/bash

# THIS SCRIPT DOES NOT RE-BUILD THE PROJECT, IT ONLY RE-BUILDS THE CONTAINERS

# Stop and remove all containers and networks
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-full.yml down
docker-compose -f ../docker-compose-frontonly.yml down

# Build the Docker images and run the containers
docker-compose -f ../docker-compose-backend.yml up --build
