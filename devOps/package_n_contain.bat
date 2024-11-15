@echo off

setlocal

set MVN_COMMAND=mvnw.cmd

call docker-compose down

call ./run.bat package

call docker-compose up --build