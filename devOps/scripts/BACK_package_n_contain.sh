#!/bin/bash

# USE this script ONLY to build the backend and run the backend containers

# Ensure necessary scripts are executable
chmod +x ./run.sh

# Stop and remove all containers and networks
docker-compose -f ../docker-compose-backend.yml down
docker-compose -f ../docker-compose-frontonly.yml down
docker-compose -f ../docker-compose-full.yml down

# Build the services again with ./run.sh package
./run.sh package

# Build the Docker images and run the containers
docker-compose -f ../docker-compose-backend.yml up --build
