# Linkaster
Linkaster is a microservices-based application developed as part of the Software Design Studio Project II.

Readme written by: Javier Ortega Mendoza - [javsort](https://github.com/javsort)

### Credits - Application developed by:
- Javier Ortega Mendoza ([javsort](https://github.com/javsort))
- Marcos Gonzalez Fernandez ([MarkzUni](https://github.com/MarkzUni))
- Marlène Berenger ([marlene-ber](https://github.com/marlene-ber))

## Initial Notes
In case of any doubt, feel free to contact the repo owner, [javsort](https://github.com/javsort), either via GitHub or via email @: jortegamendoza26@gmail.com.

## Application Deployment Guide
This application is expected to run completely dockerized. Therefore, the only platforms / dependencies required to build the project and deploy it are the following:

- **Java Development Kit (JDK) - [v.21](https://www.oracle.com/de/java/technologies/downloads/#java21)**
- **Docker Desktop - [v.4.35.1](https://docs.docker.com/get-started/get-docker/)** 
- **Apache Maven - [v.3.9.9](https://maven.apache.org/download.cgi#Installation)**

### Necessary ports
- The application requires specific ports in your system to be opened for the containers to work. So please ensure the following ports are free for usage:
    - **3306** -> For mySQL container
    - **8080 - 8086** -> For each microservice
    - **8077** -> For the front-end

> ⚠️ **ATTENTION!**
> Please make sure you have these ports accessible, dependencies installed, & included as **environment variables** to be able to run the project.

## Deployment instrucctions
After ensuring all requirements described above are installed, this application can be deployed in 3 different manners. All automated through `.bat` & `.sh` files located under the [`/devops/scripts`](./devOps/scripts/) folder.
- **Full application** (back-end + front-end):
    - This is the full version of the application, to deploy, run either:
        - [`./FULL_package_n_contain.bat`](./devOps/scripts/FULL_package_n_contain.bat)
        - [`./FULL_package_n_contain.sh`](./devOps/scripts/FULL_package_n_contain.sh)
    - After running the script, be sure to read the **Deployment Process** further down this file.

> ⚠️ **ATTENTION!**
> *FULL* deployment involves a lengthy build. For further details of why, please scroll down to the `What is in this container? -> Front-end Flutter Container` section further down this file.

- Exclusively **Front-end**:
    - This deployment exclusively builds the front-end, meaning it will construct the flutter-web container as it is described in the `What is in this container?` section down this file.
    - To deploy it, run either:
        - [`./FRONT_package_n_contain.bat`](./devOps/scripts/FRONT_package_n_contain.bat)
        - [`./FRONT_package_n_contain.sh`](./devOps/scripts/FRONT_package_n_contain.sh)

- Exclusively **Back-end**:
    - This deployment exclusively builds the back-end. Meaning it will only construct all the microservices in the application + database instance to properly test functionality without the lenghty process required to run the full deployment. This way we can test new changes without worrying about front-end integration & fully focus on functionality.
    - To deploy this version, run either:
        - [`./BACK_package_n_contain.bat`](./devOps/scripts/BACK_package_n_contain.bat)
        - [`./BACK_package_n_contain.sh`](./devOps/scripts/BACK_package_n_contain.sh)


## Deployment Process
- After running either deployment version, the logs from all containers will start to come in. The MySQL container is generally the container that takes the longest to establish itself and start accepting traffic. So, don't be surprised if any of the following containers stop running before `Linkaster-SQL` is fully accessible:
    - Message Handler
    - Timetable Service
    - User Service
    - Module Manager
    - Feedback Service

The MySQL container will be ready whenever it displays a log similar to the following:
```
... [System] [MY-011323] [Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock
... [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.40'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL
```
Once logs similar to this are printed, it is simple enough to click on the play button for the dropped containers in the Docker Daemon UI to start them again, and properly establish their database connection.

Once all containers are running, access the front-end via [localhost:8077](http://localhost:8077/). This should already display the Linkaster Login Page.

To access with an existing user, the following test accounts were generated during development to test functionality:
- Test ADMIN account:
    - Email: `admin@example.com`
    - Password: `admin_password`
- Test STUDENT account 1:
    - Email: `user1@lancaster.ac.uk`
    - Password: `user1_password`
- Test STUDENT account 2:
    - Email: `user2@lancaster.ac.uk`
    - Password: `user2_password`
- Test TEACHER account:
    - Email: `teacher@example.com`
    - Pasword: `teacher_password`
> There are more test accounts available, all described in the [sql injection file](./devOps/sql/002_users.sql) at: `./devOps/sql/002_users.sql` 

### Shared Variables in the deployment
For proper functionality, all services share the same environment variables. All described under the [`.env`](./devOps/.env) file located under the `/devOps` folder.
This file is added in all the services' docker compose inclusions, and it overrides all variables declared for each service `main/resources/application.yml` variable file. These are for enabling proper logging, adding the addresses for inter-service communication, database access credentials, jwt secret & more.

## What is in this container?
In this section, it will be described what is running in every available container in this application to easily map the source code and the application's instance. It will be split up in 3 sections:

### Java Microservices Containers:
Java containers refers to all the service applications running in the deployment. Each are named according to their microservice name, which is a separate Java Application with its respective Dockerfile. These are all located under [`/linkaster-services`](/linkaster-services/), and they are the following, each with a small description to describe its tasks:
- **`Logic Gateway`** -> refers to the [logicGateway](./linkaster-services/services/logicGateway/) microservice located at: `linkaster-services/services/logicGateway`
    - This microservice is the only Java microservice which doesn't require a database connection.
    - It handles all entrances to the back-end and works as the first layer of authentication.
- **`User Service`** -> refers to the [userService](./linkaster-services/services/userService/) microservice located at: `linkaster-services/services/userService`
    - User Service handles all user-related tasks. Also being the only one handling login/registration with an open end-point, which still has to be accessed through the logicGateway.
- **`Module manager`** -> refers to the [moduleManager](./linkaster-services/services/moduleManager/) microservice located at: `linkaster-services/services/moduleManager`
    - Module Manager handles all tasks related to modules, as they handle most of the groups within our application's funtionality.
- **`Timetable Service`** -> refers to the [timetableService](./linkaster-services/services/timetableService/) microservice located at: `linkaster-services/services/timetableService`
    - Timetable Service is in charge of managing each of the student's timetables. These are generated upon registration and they are updated from upcoming events & deliverables.
- **`Message Handler`** -> refers to the [messageHandler](./linkaster-services/services/messageHandler/) microservice located at: `linkaster-services/services/messageHandler`
    - Message Handler is in charge of authorizing connections to webSockets and establish the proper messaging interaction, storage & encryption process.
- **`Feedback Service`** -> refers to the [feedbackService](./linkaster-services/services/feedbackService/) microservice located at: `linkaster-services/services/feedbackService`
    - Feedback Service is in charge of handling & storing all feedback sent to a module.

### MySQL Container:
The mySQL container, `Linkaster-SQL`, is this application's database instance, which is provided based on the official mySQL 8.0 image built for docker.
- This image is in charge of handling all database entries for our microservices.
- This image is also loading existing volumes with sample data to the application to display functionality. These sql injection files are defined at the: [`/devops/sql`](./devOps/sql/) folder, and each add the following:
    - `001_roles.sql` -> Only static database in the entire system. Holds all roles definitions for overall functionality in the application.
        - Loaded & exclusively accessible for: `User Service`
    - `002_users.sql` -> Holds sample users to facilitate testing.
        - Loaded & exclusively accessible for: `User Service`
    - `003_modules.sql` -> Holds sample modules to facilitate testing.
        - Loaded & exclusively accessible for: `Module Manager`
    - `004_events.sql` -> Holds sample events & timetables to facilitate testing.
        - Loaded & exclusively accessible for: `Timetable Service`
    - `005_announcements.sql` -> Holds sample announcements to facilitate testing.
        - Loaded & exclusively accessible for: `Module Manager`
    - `006_chats.sql` -> Holds sample chat instances & messages to facilitate testing.
        - Loaded & exclusively accessible for: `Message Handler`

### Front-end Flutter Container:
The Front-end Flutter Container, `flutter-web` stands out from the rest of the containers in its functionality. While the others are either just running the compiled code or using an official image, the flutter web's [dockerfile](./linkaster_application/Dockerfile) is performing the following setup steps:
1. Run a linux container
2. Copy the flutter files into the container
3. Download the official linux flutter git repository
4. Once flutter is installed into the container, build the web-application
5. Deploy the built application on an `nginx` server, also using its official image for docker & make it accessible to the back-end.

> ⚠️ **ATTENTION!**
> 
> This set of steps makes the front-end deployment lengthier than the rest, and therefore, extends the time taken to build the full application. Even though it's a lengthy process, it completely externalizes the need to have flutter installed in your system and reduce the required software installed to run the application, all thanks to the containerization process.

To access the front-end source code files, please go to the [`linkaster_application`](./linkaster_application/) folder.

## Requirements for development
For development, further requirements are needed to be able to run the application locally. 

### Testing Full Integration
To test while developing the full integration, it is necessary to follow the above described deployment process. Either that, or run both of the procedures described in the following sections and run the front & back-end separately:

### Front-end development requirements
- The front-end of this application was fully developed via Flutter. Since it is deployed in containers, it is not required to have flutter installed when deploying, but it is necessary for running outside the container.
- This application is using the [latest](https://docs.flutter.dev/get-started/install) Flutter version..
- Be sure to follow the official installation guide & have flutter in your environment variables.
- It should be enough to either deploy the application in development mode, or build the web version through the following commands on console:
```
Build web version:
    flutter build web --release

Run in development mode:
    flutter run -d chrome
```

### Back-end development requirements
- For development in the back-end, having docker, maven & JDK 21 installed should be enough to work in the project as a developer, but it is encouraged to have the following:
    - MySQL Workbench
        - Facilitates DB access and allows direct connection to the DB container with the application.
    - Postman 
        - Facilitates endpoint testing. As long as the requests match with the methods described at each services' API interface under `<service name>/src/main/java/com/linkaster/<service name>/controller/API<controller class>.java`
- Extra notes for working with the back-end:
    - The application uses JWT tokens as a key aspect of the application. And with the full deployment, the tokenization process and holding the token is fully handled thanks to the front-end back-end integration. 
    - But whenever testing in the back-end, you will always have to perform either a `login` or a `registration` to retrieve a token and access the rest of the endpoints, which besides a few exceptions, all require authorization via the JWT token created upon authorization.
    - For example, when testing via *postman*, it is sufficient to perform a login with the following credentials:
```
Endpoint: http://localhost:8080/api/auth/admin/login
Method: POST
Body: (using raw & JSON as the option)
{
  "userEmail": "admin@example.com",
  "password": "admin_password"
}
```

- This will return a String with the token, looking something similar to this:

```
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwicm9sZSI6IkFETUlOIiwidXNlckVtYWlsIjoiYWRtaW5AZXhhbXBsZS5jb20iLCJpZCI6IjEiLCJpYXQiOjE3MzM0OTA0NTQsImV4cCI6MTczMzQ5MDgxNH0.baouyPLjzVm5Z8Ck9xj4lHkrmkzANy9jR8lRzOqB4wk"
}
```

- From here, it is enough to copy the token **WITHOUT** the quotation marks `" "`, and for every other request, besides satisfying the required body or endpoint setup, you will have to paste the token in the text box available under `Authorization -> Auth Type -> Bearer Token` which will satisfy the Token as a header for the request.


> ⚠️ **ATTENTION!**
> Development for back-end is still encouraged to do within containers. To avoid the lenghty build process introduced by the front-end integration, please utilize the [back-end only script](./devOps/scripts/BACK_package_n_contain.bat), `BACK_package_n_contain.bat/.sh`, which builds faster. As it was described in the beggining of this file.

### Deprecated Services / Expected for future updates
There are other microservices that still remain on the development pipleline. Thanks to the microservices architecture, we are able to either include or not include them in the final build. But they are still expected / can be added for a future instance of the application. These involve the following:
- `Cabinet Service` -> this [microservice](./linkaster-services/services/cabinetService/) would allow users to upload files for both modules & chat interaction. Also prioritizing proper file encryption & storage.
- `Notifications Handler` -> this [microservice](./linkaster-services/services/notifHandler/) would allow users to receive notifications in real-time. Either from messages or module updates.
- `Reserve Service` -> this [microservice](./linkaster-services/services/reserveService/) would allow users to reserve study spaces for studying, either at the library or classroom. It would also update the user's timetable with the reservation information.

For the time being, these services are currently commented out of the [`parent pom.xml`](./linkaster-services/pom.xml) file, but can still be included in a future update.

## Helper scripts
Finally, as a last section for this README, there's a set of helper scripts to help YOU and the deployment scripts run the application.
Also under `devOps/scripts`, we have the following scripts both in `.bat` & `.sh` format (for all Windows, Mac & linux users) to facilitate certain maven / docker commands & processes.
- **`run.bat [command]`** & **`run.sh [command]`**:
    - This scripts runs maven commands on the entire back-end, so for example, you would run:
    ```
    ./run.bat build                                 -> builds the entire project
    ```
    - If you don't know which command you want to use, call `./run.bat help` or run the script with no arguments to get help on usage:
    ```
    ./run.bat

    Usage: "./run.bat command"
    Use this script to run any of these maven commands on the project!

    Available commands:
    build      - Clean install and build the project
    buildWlogs - Clean install and build with full logging
    package    - Package the project
    validate   - Validate the project
    test       - Run all tests
    testWlogs  - Run all tests with full logging
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