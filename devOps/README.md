# DevOps Readme
Welcome to devOps folder! Here is where everything is built, contained and run.

There are **3** different ways to build the system, each has its own `./bat` & `./sh` files to run it, along with some other helping scripts for the backend.


Readme written by: Javier Ortega Mendoza - [javsort](https://github.com/javsort)

> ⚠️ **ATTENTION!**
> All scripts should be run from the `/scripts` folder.


## The 3 different setups:
### 1. Full Build
- This builds practically the entire project. BE CAREFUL since it takes a long time + resources to build & run.
- Docker compose file: [docker-compose-full.yml](./docker-compose-full.yml)
- Scripts to build it, package it & run it:
    - `FRONT_package_n_contain.bat`
    - `FRONT_package_n_contain.sh`

### 2. Back-end Build Only
- This builds the entire back-end, it can get resource intensive, but it will not get anything front-end related.
- Docker compose file: [docker-compose-backend.yml](./docker-compose-backend.yml)
- Scripts to build it, package it & run it:
    - `BACK_package_n_contain.bat`
    - `BACK_package_n_contain.sh`

### 3. Front-end Build Only
- This builds the entire front-end. It's **lengthy**, since it installs Flutter on a linux distribution to then compile & build the front end to set it up in a *ngnix* server. It will not build anything back-end related.
- Docker compose file: [docker-compose-frontonly.yml](./docker-compose-frontonly.yml)
- Scripts to build it, package it & run it:
    - `FRONT_package_n_contain.bat`
    - `FRONT_package_n_contain.sh`

## Helper Scripts
- **`run.bat [command]`** & **`run.sh [command]`**:
    - This scripts runs maven commands on the entire back-end, so for example, you would run:
    ```
    ./run.bat build                                 -> builds the entire project
    ```
    - If you don't know which command you want to use, call help or run the script with no arguments to get help on usage:
    ```
    ./run.bat

    Usage: "./run.bat command"
    Use this script for command the project!
    Available commands:

        build      - Clean install and build the project
        buildWlogs - Clean install and build with full logging
        package    - Package the project
        validate   - Validate the project
        test       - Run all tests
        clean      - Clean the project
        help       - Show usage
    ```

- **`build_service.bat [service]`** & **`build_service.sh [service]`**:
    - This scripts builds the desired service added as an argument, so for example, you would run:
    ```
    ./build_service.bat userService                 -> ONLY builds userService
    ```
    - If you don't know which service to build, then call it without arguments, where the script will provide the available services to build:
    ```
    ./build_service.bat

    Usage: "./build_service.bat serviceName"
    serviceName - The name of the service to build
    Use this script to build a specific service!

    Available services:
        logicGateway                - Build the Logic Gateway Service
        userService                 - Build the User Management Service
        moduleManager               - Build the Module Management Service
        cabinetService              - Build the Cabinet Service
        notifHandler                - Build the Notification Service
        timetableService            - Build the Timetable Service
        reserveService              - Build the Reservation Service
        feedbackService             - Build the Feedback Service
        messageHandler              - Build the Message Handler Service
    ```

- **`rebuild_containers_backend.bat`** & **`rebuild_containers_backend.sh`**:
    - These scripts **DO NOT** re-build the .jar application files. Instead, they take down the existing containers, and re-build the back-end with the last .jar files that were built on the repo.
    - Simply call it as:
    ```
    ./rebuild_containers_backend.bat
    ```

## Other Files & Folders
### - The [`/sql`](./sql/) folder:
The initial database files for the back-end are set up here. **PLEASE DON'T TOUCH**.

### - The [`.env`](./.env) file:
In this .env file, the environment variables shared across services are set up. **PLEASE DON'T TOUCH**.