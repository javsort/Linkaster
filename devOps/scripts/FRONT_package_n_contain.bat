@echo off

:: USE this script ONLY to build the backend and run the backend containers

setlocal

:: Drop all containers and networks - no matter if w or w/o backend
call docker-compose -f ../docker-compose-backend.yml down
call docker-compose -f ../docker-compose-full.yml down
call docker-compose -f ../docker-compose-frontonly.yml down

:: Step 1: Build the frontend with no cache
call docker-compose -f ../docker-compose-frontonly.yml build --no-cache

:: Build the frontend image and run it
call docker-compose -f ../docker-compose-frontonly.yml up