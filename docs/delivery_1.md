Software Design Studio II 
Deliverable I
L!nkaster 


Marlene Berenger
Javier Ortega Mendoza
Marcos Gonzalez Fernandez


Assignments 

Group Member 
Student ID
Tasks
Marlene Berenger
38880288
Documented Requirements Specification
UML Class Diagram
Javier Ortega Mendoza
38880237
Constraints
Design Architecture 
Component Diagram
Marcos Gonzalez Fernandez
38883104
Functional and Nonfunctional Requirements
UML Sequence and State Diagrams


Table of Contents 
Introduction
Purpose
Definitions and Acronyms 
Architecturally Significant Requirements
Functional Requirements
Non-Functional Requirements
Constraints 
System Design and Architecture 
System Design
Design Patterns and Architecture
Design Patterns
UML Class Diagram
Component Diagram
System Architecture
UML Sequence Diagram
State Diagram 
Architecture Style
Appendix
Sources

Introduction
Purpose
	The purpose of L!nkaster is to provide students and educators at Lancaster University Leipzig with an app and website that they can use for all their university-related needs. LUL students are required to use a variety of tools, each specialized for a single task. Timetabler, for example, is a website that is used exclusively for students to view their schedule. Several of these tools, such as Moodle, require students to log in every time, some even requiring two-factor authentication. L!nkaster combines all relevant tools into one app and website so that students can access their day-to-day educational information in one place without the hassle of logging in to each one or installing multiple apps. 
Students and instructors can easily communicate with each other through L!nkaster without needing to exchange personal information (account creation simply requires an LUL email address). Instructors can use this to notify students about canceling class, a new assignment being posted, grades being released, and other academic announcements. Furthermore, each module automatically generates a group chat with enrolled students to encourage students to communicate and provides them with a space to ask questions, share notes, and generally form connections. 
To summarize, L!nkaster is a single platform that combines many different tools into one and is specifically customized to LUL. It promotes communication and teamwork between classmates and is overall an efficient alternative to the many different tools LUL currently uses. 
Definitions and Acronyms 
LUL: Lancaster University Leipzig
DB: Database
UI: User Interface
Requirements and Constraints
Functional Requirements
Role-Based Access Control (RBAC):
The system shall ensure role-based access for students and teachers.
The information displayed and available actions shall be determined by roles and permissions.
Administrative teachers should be able to create modules and assign module teachers to the specific module.
Messaging Functionality:
The system shall support a group messaging functionality between classmates.
The system shall support private messaging between students. 
The system shall ensure public-key encryption for all messages while also supporting safe storage for them.
The system shall support a ?status? functionality to show if the student is occupied or not.
The system shall support a search mechanism to initiate one-on-one chats.
Module and Group Joining:
The system shall allow students to join modules or club groups through unique codes.
After a Student joins a module, the system shall automatically update its timetable with its corresponding schedule.
Each module and group will maintain a list with all active students for auditing purposes.
Timetables:
The system shall support a timetable functionality for the students and teachers.
Events shall be categorized on the user?s timetable based on whether they are mandatory or not.
Push Notifications:
The system should support real-time push notifications to students and teachers for announcements, deliveries, and other alerts. 
The system should track students? timetables and silence notifications automatically during mandatory classes and library reservations.
Notifications and Announcements:
Teachers should be able to schedule announcements for future dates.
The system shall ensure timely delivery of notifications.
The system should implement a category system for announcements & notifications.
The notification categories shall ensure proper notification filtering.
File Uploads:
The system should support file uploads for sharing in announcements and chats. 
The system should ensure that uploaded files are safely stored on the database and are only accessible to users with authorization.
Users should be able to see all the ?saved? files in a different space.
Reservation System:
The system shall allow students to book study spaces in advance.
When booking, the system shall display an up-to-date availability status of the study spaces.
Event Scheduling:
Teachers and Club Students should be able to schedule events.
Students should receive timely notifications about events. 
Feedback Mechanism:
The system should allow students to provide feedback on modules directly to teachers or administrators, with an anonymous alternative.
Profile Customization:
The system should allow students and teachers to customize their profiles with personal information.
The system shall ensure that personal information is stored safely and respects the GDPR?s guidelines.
External API Integration:
The system should support integration with external APIs for additional functionality. 
The system should support authentication through an API such as OAuth
The system should support One Drive file uploads through an API.

Non-Functional Requirements
Performance:
The system should ensure that notifications are delivered within 1-5 seconds of being sent, even at peak usage.
The system should have low latency for critical operations such as authentication, module joining, and notification delivery (response times under 500ms).
Scalability:
The system?s architectural design shall support future development and the addition of more services.
The system should support high throughput in its notification services, ensuring it can handle sending a large number of push notifications simultaneously without bottlenecks.
The system should be able to handle an extensive amount of active users.
Portability:
The system should support various platforms, including web browsers, iOS, and Android devices, to ensure cross-platform portability.
Reliability:
The system should be fault-tolerant, ensuring that if one component fails, the system continues to operate without losing data or functionality outside of the affected service.
Maintainability:
Only the services needing to be maintained will be taken down during a maintenance period.
The system should include automated backup processes and quick data recovery mechanisms to ensure data integrity in the event of an outage or system failure.
Security:
The system should prioritize data encryption (both at rest and in transit) for all sensitive information.
The system shall make use of public-key encryption for higher security.
Availability:
The system should guarantee high availability (99.9% uptime) to ensure access at all times, except during scheduled maintenance periods.
Usability:
The system shall uphold its design to Nielsen?s 10 Usability Heuristics for User Interface Design to ensure a user-friendly design.
Constraints
Programming Language:
The system?s back-end shall be supported by:
Java JDK 21: for the overall back-end structure, this helps ensure proper segregation between services and their proper interaction. Furthermore, utilizing an Object Oriented design facilitates the management of different features such as user-authentication, role-based access, and more.
Maven: for compilation and dependencies management. Once integrated with Java, it provides the necessary infrastructure to ease the process of developing an internet-based application. Thanks to the usage of different dependencies and plugins, the team can focus on method implementation and development while avoiding to write boilerplate code and other features are provided.
Docker: for application deployment, service containerization and management. For deploying the application, Docker was chosen to enable the application deployments and handle the overall application?s service. Also, thanks to the benefits of containerization, whenever a service requires to go under a maintenance period, the team only requires to take down the affected service without affecting the rest of the application?s performance.
SpringBoot: for overall development and configuration. Thanks to SpringBoot and the Spring Framework, we are able to ease the application?s configuration process and support our microservices design by allowing us to deploy each service separately or together for testing and deployments. 
The system?s front-end shall be supported by:
Flutter: There are several reasons why we are going to utilize flutter as the front end language for the application. First of all, it has cross platform capability meaning that with a single codebase can be run on multiple platforms. Also, this codebase is good for maintainability of the visual representation and is easy to modify and to update. Finally, with google backing up this language and a growing community of user developers, flutter is a language that will give us scalability. 
Regulatory: 
Since the system has academic purposes, it shall adhere to the GDPR?s integrity and confidentiality standards to securely store all users information.
The GDPR?s generality may complicate development given the data protection prioritization.
The system shall be transparent with users regarding what data will be processed.
The system shall be GDPR compliant and provide its users with data subject rights.
Time: 
The system?s overall development shall respect both the development team & course instructor?s deadlines, affecting depth of development and testing phases.
Hardware:
Storage and computing power may be limited for the first instance of the application.
Integration:
A proper and fast interaction between the different services should be prioritized for proper functionality, which could be resource intensive.
All services should be able to maintain proper interaction protocols for both inter-service requests and client-server requests.
Encryption:
Even though encryption improves security standards, it also adds layers of authorization, which could lead to delays and longer authorization periods, possibly limiting the up-to-date features.
Portability:
Designing both a handheld & web version of the application might bring up some design challenges throughout development.
3rd Party Reliability:
Using 3rd party APIs involves handling call failures and other API related issues from our application?s back-end, which might affect performance.
Use Case
To represent the our functional requirements, the following UML Use Case Diagram was brought up:

Figure 1 - UML Use Case Diagram
https://drive.google.com/file/d/1nIoyDVTovPqF6WSYSsHg3KB69YQ22P5V/view?usp=sharing 

System Design and Architecture 
System Design
3-Tier Architecture: 
Setting up the system?s architecture with the industry standard not only sets the application at a market-ready level, but it also ensures better security for our back-end while also allowing better scalability and manageability. The clear difference between the three layers allows for easier development, as one person can work on each layer separately. 

Client-Server Architecture: 
This architecture enables us to establish a modern framework for the application?s design. Having the standard client-server requests, the separation between our front-end (client?s side) from our back-end (server) allows the application to process all user requests through implemented REST APIs.

Event-Driven Architecture:
Since certain features from our services are expected to support real-time notifications or events, the application must be ready to handle and react to real-time requests from different services such as notifications, and ensure these are delivered in a timely manner.

Service-Oriented Architecture:
Developing our application based on segregated services not only benefits the development process by offering clearly divided services, but it also allows us to re-use their functionality throughout the entire application. Giving a separate space for each service to operate also makes the system more elastic, allowing longer and memory-demanding tasks to be managed by their corresponding services instead of affecting the overall performance. 

Microservices Architecture:
The design choice of developing the back-end with microservices only further enhances the modularity of the 3-layered architecture. Supported on the service-oriented architecture and containerization, it also allows for better resource management while maintaining a communication protocol from client to server while also for servers in between. 

From the architecture choices described, the following high-level component diagram was outlined, which will be followed further in the report by a developed component diagram:

Figure 2 - High-Level Application Design
https://drive.google.com/file/d/1odZTdcW7gJUNxFq5uFWitMnfNHlG_mlq/view?usp=sharing 
Design Patterns and Architecture
Design Patterns
Database Per Service Pattern
	To ensure that the services are loosely coupled, each service will have its own database. This is a common design pattern for microservices that allows databases to be altered with minimal effect on other databases. This pattern can clearly be recognized in the UML class diagram, which shows that each controller has a corresponding repository that the related subclasses communicate with.

Sidecar Pattern
	For classes with related functionalities, we used a sidecar pattern. This is a way to couple classes without them interfering with each other and while remaining loosely coupled. The authorization agent class is an example of this. Here, the authorization agent is the supporting feature (?sidecar?) while the user authenticator is the parent class. 

Role-Based Access Control (RBAC)
	One of L!nkaster?s key features is how the app accurately depicts the different types of people at LUL and their different access levels. In order to properly show that there are teachers and students but that both have subclasses with different access, we used a generic RBAC pattern. This enforces security in our system by ensuring students cannot interfere with modules or commit other authorized activities. 

Proxy 
	The proxy design pattern can be identified at the top of the component diagram (Figure 4) where the logic gateway contains a route forwarder proxy. This proxy allows us to control access to the different classes while using less resources. This pattern is particularly useful for L!nkaster because we have many different processes that do not need to communicate with all other classes. For example, the messaging services and module services are completely separate. Using a proxy helps us only use the resources needed for a specific action. 

UML Class Diagram
	This section is intended to provide the reader with an overview of how the L!nkaster code will be written. The application is written in an object-oriented language to allow us to utilize inheritance and polymorphism to create more scalable and efficient code. 
	Note: for best image quality, please open the drive link in draw.io and use the tabs at the bottom to navigate between the different diagrams. 
Full UML Class Diagram: 

Figure 3 - UML Class Diagram
https://drive.google.com/file/d/1WAS_DAfSp2KZCdGR41Y-7L-D-ovqnu2_/view

Cabinet Service:

Figure 3.1 - Cabinet Service UML Class Diagram

Module Management Service:

Figure 3.2 - Module Manager Service UML Class Diagram

Feedback Service:

Figure 3.3 - Feedback Service UML Class Diagram

User Management Service:

Figure 3.4 - User Management Service

Reservation Service:

Figure 3.5 - Reservation Service UML Class Diagram

Notification and Messaging Service:

Figure 3.6 - Notifications and Messaging Service UML Class Diagram

Timetable and Event Scheduling Service:

Figure 3.7 Timetable Service UML Class Diagram
API Handling:

Figure 3.8 API Handling Service UML Class Diagram
Model Classes:

Figure 3.9 - UML Class Diagram for shared model classes across services

Component Diagram
This section intends to justify the choice for microservices & service-oriented architecture, and give a wider description of the inner services within our modules. Following the core microservices structure of Controller -> Service -> Repository, we further distinguished differences within the services for better resource management and functionality. The following component diagram is intended to display most interactions within the services and show how components are expected to interact:


Figure 4 - Application Component Diagram
https://drive.google.com/file/d/174GHoknSBu4yiTfKNKH852lsSnHDHFba/view?usp=sharing 

System Architecture
UML Sequence Diagram
Refine the created architecture by presenting Runtime architecture to be model via sequence and state diagrams.

User Management Service: 
This is the sequence diagram for the user management service showing the main 3 cases of this service: User registration, user login, and user customization. In the registration case, the user inputs the required information and the system validates the information and creates a key pair for encryption. The login case only checks and confirms the data with the DB. The customization case lets the user modify the screen (add dark mode or add optional information to their profile) and updates the user information in DB.



Figure 5 - User Management Sequence Diagram
https://drive.google.com/file/d/1iDWba4AtqoJEj5BGBRkQaGF-Q6mDXWD1/view?usp=sharing 


Module Management Service: 
This is the sequence diagram of the Module Management Service, and has 4 cases: Creation of module, assignation of teacher, code generation and the student joining the module. The creation of the module is carried out by the administrative teacher; the data is added to the DB and managed by the service. Once the module is created the administrative teacher can add the teacher to the module and then the request is processed and the DB is updated. After the teacher is assigned to a module, the module teacher can create a code (access code of the module). Once the request is processed it updates the DB. Finally, once the code module is created the student can input the code and the business logic processes the request and, if valid, it updates the DB. 


Figure 6 - Module Management Service Sequence Diagram
https://drive.google.com/file/d/13jR028Oe_bKQ_YG6zUXWYvQA4v1vjxFn/view?usp=sharing

Cabinet Service:
This is the sequence diagram of the Cabinet (file and storage) Service, and has 3 cases: File upload, file download and share file. In the file upload case, the user uploads a file to the storage and it saves the data in the DB. In the download case, the user downloads the file from the file storage to the device. In the share file case, the user sends the file with the public key of the user/users and sends the file in the chat. 


Figure 7 - Cabinet Service Sequence Diagram
https://drive.google.com/file/d/1mjO8DoAzUmWG3DQkH3J0oeo0iUf8mXcT/view?usp=sharing 

Notification and Messaging Service: 
For the sequence diagram of the notification and messaging service, there are 5 cases: message send, search user, message receive, announcement received and update in timetable. For message send, the UI requests the public key of the user that the user wants to send a message and then sends the message. For search user, the user inputs the letters of the name and continuously requests that prompt to the DB to retrieve the options. For message receive, once the user sends the message and it is encrypted, the business logic decrypts it for the user. The announcement case is that once the announcement is being posted to a module/club the UI retrieves the public keys of those people, sends it and then is decrypted by the private key of each user to display in the UI. The notification of a new event is when the user is added to a schedule in timetable, updates the DB and sends a notification. 

Figure 8 - Notification and Messaging Service Sequence Diagram
https://drive.google.com/file/d/1SEN3NnAW5jGsDiCrB0-DDORNeJTbAGwK/view?usp=sharing 
Timetable and Event Scheduling Service:
The timetable works exactly the same in the same cases, so when the student is joined to an event it automatically checks the data of that event and updates the timetable. Also the events need to be classified between mandatory or optional representation in the UI.  


Figure 9 - Timetable Service Sequence Diagram
https://drive.google.com/file/d/1Y6PX01lDG-x_wvGn3XbMtzN7dEJZ337I/view?usp=sharing 







Reservation Service:
	The reservation system works when the user selects the slot in library it checks the times of that slot being available in the selected day. Then the user selects the time and confirms the booking, making him the owner of that space. 

Figure 10 - Reservation Service Sequence Diagram
https://drive.google.com/file/d/1ht1wtrFgYJ9BAlvY007ycZ03V-UZ2Awx/view?usp=sharing 

Feedback Service: 
Here we have two cases if the user wants to send it anonymous or public. In both cases the procedure works like in messaging requisition the public key of the person that wants to send feedback. Then it the system de-encrypts the message but if the feedback was send anonymously it will appear ?anonymous? to the other user.

Figure 11 - Feedback Service Sequence Diagram
https://drive.google.com/file/d/12b7BkOLyyGgfTJwBSFXP0sDsJ59byM_H/view?usp=sharing 
State Diagram
Case: student sends file
	The user wants to send a file to another user, so first the user needs to logIn to the application, after the log in it enters to the chet and selects add attachment. It opens the file storage which is managed by the file service, here it can see all the options, selects the preferred file and submits. The next step is managed by the messaging service with the encryption process and once it is sent the app sends a notification to the user. 
Figure 12 - State Diagram for ?Case Student Sends File?
https://drive.google.com/file/d/1BGy3Bf_KE-y1f3Dz_fRly5Tb1NvvOjBW/view?usp=sharing 

Case: student joins module
	The user wants to join a module, so first the user needs to logIn to the application, here there is a process that all the authentication is correct. Then it clicks on join module, it puts the code that the teacher gives and after processing it adds the user to the module. The timetable once is joined then updates the schedule of that student and the it sends the update to the notification service which then notifies the update to the user.

Figure 13 - State Diagram for ?Case Student Joins Module?
https://drive.google.com/file/d/1dZRJQBuqDdCRmI7gIOIJSXlDr_e6pd5V/view?usp=sharing 

Case: student makes reservation
	The user wants to book a space in the library, so first the user needs to logIn to the application. After that, the user selects space and time for the booking, the request is processed by the reservation service and its approved. Then it sends the time and slot to the timetable service which after receiving it, updates the user schedule and sends the confirmation to the notification to generate a notification to the user.
Figure 14 - State Diagram for ?Case Student Makes Reservation?
https://drive.google.com/file/d/1hy_fdEXhsdNsRSYsKd9sFQ_CqGcBarLd/view?usp=sharing 

Architecture Style
Architect the TutorLink app by applying the most suitable architecture style(s) and justify the rationale for the selection of your style. Please note, the app by-default follows a client-server based and layered organization so your selected style must be other than the generic client server and layered architecture.

As mentioned above, for this project?s purpose, we intend to unify multiple platforms the intended end-user currently has to access. And the best way to handle heavy and diverse tasks such as the ones the user requires, is to set them up as services & containers, allowing us to handle each service?s resources more closely, make it easier to notice issues and allow us to have almost minimal downtime besides giving service maintenance. Furthermore, the separation between services allows us to have separate databases, and each service?s structure allows us to have a clear picture of what service can access what information. This only further enhances our project?s adherence with GDPR standards, keeping all sensitive information in a need-to-know access basis, and allows us to minimize access to sensitive data.

Also, supported on the 3-tier web architecture, and the addition of a proxy, the overall security of the business logic layer and database layer is only enhanced thanks to clearly divided layers and access points. Allowing us to know exactly which paths are accessible, what is inside those modules and what information is being requested while still keeping the system safe from possible attacks.

These features, supported on a proper REST implementation, should enable the system to have a centralized access, while still having a separate container for each service. It tightens access rules in between layers while still maintaining a loosely-coupled architecture. Traversing in and out of the layers may require a higher security prioritization, but once within each layer, it is simple to both perform each of the services? tasks while still allowing standardized communication between services. 

After coming up with the architecture choices, the following services were defined:
User Management Service
Description:
The user management service is in charge of handling all user access for both teachers and students, authentication, and role access control.
Responsibilities:
Handle user access & authentication
Compliance towards GDPR?s standards for handling sensitive user information.
Handle RBAC.
Handle all user customization features.

Module Management Service
Description: 
The module management service is in charge of handling all kinds of modules, whether they are classes or clubs, and all their corresponding actions. It must coordinate properly with the timetable service for proper scheduling.
Responsibilities:
Handle all modules along with its characteristics.
Manage module creation and teacher assignment.
Handle join codes for the modules.
Audit the students registered per module.
Update students? schedules along with the timetable service after students join modules.

File Upload & Storage Service
Description:
The File Upload & Storage Service is a modular service designed to handle all files uploaded by users. Whether it?s a message, an announcement, or a document shared within a module,  the service is in charge of the proper and secure upload and storage of all files. It also helps to decouple file-handling from the rest of the services.
Responsibilities:
Manage file uploads for all services & requirements. 
Ensure secure file storage and authenticated access to files.
Track downloaded files for users.

Notification and Messaging Service
Description:
The notification and messaging service is in charge of handling all messages between users and within module spaces, while also being in charge of notifications and their scheduled delivery. It should support real-time communication while also handling encryption, which could become resource intensive. 
Responsibilities:
Messages
Ensure public-key encryption for all messages.
Provide a ?status? functionality for all users.
Coordinate with the Timetable and Event Scheduling service to enqueue messages sent during a ?busy? status (being in class).
Allow user lookup based on name or student Id.
Capacity to scale-up during high user activity.
Notifications
Support push notifications for real-time alerts, announcements, deliveries, schedules and reservations.
Implement a category system for notifications.
Schedule future announcements and ensure their proper delivery.

Timetable and Event Scheduling Service
Description: 
The Timetable and Event Scheduling Service is tasked with handling all students? timetables along with all event scheduling. These will be updated whenever a student enters a new module, reserves a study space, or an event is scheduled for the week, and therefore, the service should focus on handling overlapping events and timetable updates to ensure proper functionality.
Responsibilities:
Manage all timetables for students.
Update automatically whenever a student enters a module and its registry is complete. 
Update automatically whenever an event is published.
Update automatically whenever a student reserves a study space.
Classify elements in the timetable by their nature.
Integrate with the Notification and Messaging Service to notify students about any element in their timetable.

Reservation Service
Description: 
The Reservation Service is a self-contained service where students will be able to reserve study spaces based on real-time information. They will be able to request them in advance, and be notified in case of any changes. Its self-contained nature allows for future expansion.
Responsibilities:
Provide a study space reservation system
Provide real-time updates on available spaces and their booking status
Integrate with Timetable and Event Scheduling Service to update its timetable after a reservation is confirmed.

Feedback Service
Description: 
The Feedback service is in charge of handling feedback from students, to teachers, and administrative teachers. Given its self-contained nature it benefits data segregation within the database to handle all feedback separately. It also allows for future expansion.
Responsibilities:
Provide a feedback system for all students.
Provide the option of anonymity for feedback submission.

External API Handling Service 
Description: 
The External API Handling Service, as its name establishes, is to handle all 3rd party API calls the application will be performing. Not only does this service modularize the API accesses, but it also makes our application more resilient to failure. It enables us to handle all API failure, reliability issues and more concerns within one service, and avoids issues for the rest of the services. 
Responsibilities:
Handle all 3rd party API calls for all tasks.
Handle authorization through the OAuth API.
Handle all file uploads to OneDrive through Microsoft?s API.



Appendix
Initial Sketches of the Application
Note: These sketches are to give a general understanding and overview of what the web version of the application will look like. The finished software will not look exactly the same. 

Appendix 4.1.1 - Module Messaging View Sketch


Appendix 4.1.2 - Private Messaging View Sketch

Appendix 4.1.3 - Timetable View Sketch


Appendix 4.1.4 - Announcements View Sketch
https://www.canva.com/design/DAGUNfqutIU/EPfQvra8zaUDSQ9BF2qjdA/view?utm_content=DAGUNfqutIU&utm_campaign=share_your_design&utm_medium=link&utm_source=shareyourdesignpanel 

Sources
Spring. (n.d.). Spring Boot. VMware Tanzu. https://spring.io/projects/spring-boot 
Katariya, J. (2024, June 14). Web application architecture: Everything you need to know. Moon Technolabs. https://www.moontechnolabs.com/blog/web-application-architecture/  
Confluent. (n.d.). Event-driven architecture (EDA): A complete introduction. https://www.confluent.io/learn/event-driven-architecture/#:~:text=Event%2Ddriven%20architecture%20(EDA),to%20react%20in%20real%20time.  
Terra, J. (2024, July 23). What is Client-Server Architecture? Everything you should know. Simplilearn. https://www.simplilearn.com/what-is-client-server-architecture-article 
Amazon Web Services. (n.d.). What is SOA (Service-Oriented Architecture)?. Amazon Web Services.  https://aws.amazon.com/what-is/service-oriented-architecture/?nc1=h_ls 
Google Cloud. (n.d.). What is Microservices Architecture?. Google Cloud. https://cloud.google.com/learn/what-is-microservices-architecture?hl=en 
AltexSoft (2022) Pros and cons of Flutter App Development, AltexSoft. Available at: https://www.altexsoft.com/blog/pros-and-cons-of-flutter-app-development/ (Accessed: 31 October 2024). 
 RobBagby. (n.d.). Sidecar pattern - Azure Architecture Center. Microsoft Learn. https://learn.microsoft.com/en-us/azure/architecture/patterns/sidecar 
Lane, G. K. (2023, January 17). How to write an SRS Document (Software Requirements Specification Document). Perforce Software. https://www.perforce.com/blog/alm/how-write-software-requirements-specification-srs-document 
What is Role-Based Access Control (RBAC)? Examples, Benefits, and More. (n.d.). Digital Guardian. https://www.digitalguardian.com/blog/what-role-based-access-control-rbac-examples-benefits-and-more 
Refactoring.Guru. (2024, January 1). Proxy. https://refactoring.guru/design-patterns/proxy 
