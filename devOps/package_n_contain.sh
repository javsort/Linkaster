#!/bin/bash

# Stop and remove all containers and networks
docker-compose down

# Build the services again with ./run.sh package
./run.sh package

# Build the Docker images and run the containers
docker-compose up --build
