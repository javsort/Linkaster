#!/bin/bash

# THIS SCRIPT DOES NOT RE-BUILD THE PROJECT, IT ONLY RE-BUILDS THE CONTAINERS

# Stop and remove all containers and networks
docker-compose down

# Build the Docker images and run the containers
docker-compose up --build
