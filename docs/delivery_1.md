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
UML Class and Component Diagrams
Javier Ortega Mendoza
38880237
Constraints
Design Architecture 
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
	The purpose of L!nkaster is to provide students and educators at Lancaster University Leipzig with an app and website that they can use for all their university-related needs. LUL students are required to use a variety of tools, each specialized for a single task. Timetabler, for example, is a website that is used exclusively for students to view their schedule. Several of these tools, such as Moodle, require students to log in every time, some even requiring two-factor authentication. L!nkaster combines all relevant tools into one app and website so that students can access all their educational information in one place without the hassle of logging in to each one or installing multiple apps. 
Students and instructors can easily communicate with each other through L!nkaster without needing to exchange personal information (account creation simply requires an LUL email address). Instructors can use this to notify students about canceling class, a new assignment being posted, grades being released, and other academic announcements. Furthermore, each module automatically generates a group chat with enrolled students to encourage students to communicate and provides them with a space to ask questions, share notes, and generally form connections. 
To summarize, L!nkaster is a single platform that combines many different tools into one and is specifically customized to LUL. It promotes communication and teamwork between classmates and is overall an efficient alternative to the many different tools LUL currently uses. 
Definitions and Acronyms 
LUL: Lancaster University Leipzig
Requirements and Constraints
Functional Requirements
Marcos 
Elicit all the architecturally significant requirements (ASRs) for L!nkaster app. The ASRs must cover the entire scenario.

Role-Based Access Control (RBAC):
The system shall ensure role-based access for students and teachers.
The information displayed and available actions shall be determined by roles and permissions.
Administrative teachers should be able to create modules and assign module teachers to the specific module.
Push Notifications:
The system should support real-time push notifications for announcements, deliveries, and other alerts to students and teachers. 
The system should track students? timetables and silence notifications automatically during classes.
Notifications and Announcements:
Teachers should be able to schedule announcements for future dates.
The system shall ensure timely delivery of notifications.
The system should implement a category system for announcements & notifications.
The notification categories shall ensure proper notification filtering.
File Uploads:
The system should support file uploads for sharing in announcements and chats. 
The system should ensure that uploaded files are safely stored on the database and are only accessible to users with authorization.
Users should be able to see all the ?downloaded? files in a different space.
Messaging Functionality:
The system shall support messaging functionality between classmates and other students.
The system shall support private messaging between students. 
The system shall ensure public-key encryption for all messages while also supporting safe storage for them.
The system shall support a ?status? functionality to show if the student is occupied or not.
The system shall support a search mechanism to initiate one-on-one chats.
External API Integration:
The system should support integration with external APIs for extra functionality 
Module and Group Joining:
The system shall allow students to join modules or club groups through unique codes.
After a Student joins a module, the system shall automatically update its timetable with its corresponding schedule.
Each module and group will maintain a list with all active students for auditing purposes.
Event Scheduling:
Teachers and Club Students should be able to schedule events.
Students should receive timely notifications about events. 
Timetables:
The system shall support a timetable functionality for the students.
Events shall be categorized on the user?s timetable based on their priority and whether they are mandatory or not.
Reservation System:
The system shall allow students to book study spaces in advance.
Whenever booking, the system shall display up-to-date status on the study spaces reservation status.
Feedback Mechanism:
The system should allow students to provide feedback on modules directly to teachers or administrators, with an anonymous alternative.
Profile Customization:
The system should allow students and teachers to customize their profiles with personal information.
The system shall ensure that personal information is stored safely and respects GDPR?s guidelines.

Non-Functional Requirements
Marcos
Performance:
The system should ensure that notifications are delivered within 1-5 seconds of being sent, even at peak usage.
The system should have low latency for critical operations such as authentication, module joining, and notification delivery (response times under 500ms).
Scalability:
The system shall support itself in its architecture design for future development and the addition of more services.
The system should support high throughput in its notification services, ensuring it can handle sending a large number of push notifications simultaneously without bottlenecks.
The system should be able to handle an extensive amount of active users.
Portability:
The system should support various platforms, including web browsers, iOS, and Android devices, ensuring cross-platform portability.
Reliability:
The system should be fault-tolerant, ensuring that if one component fails, the system continues to operate without losing data or functionality besides the affected service.
Maintainability:
Only the services needed to be maintained will be taken down for a maintenance period.
The system should include automated backup processes and quick data recovery mechanisms to ensure data integrity in the event of an outage or system failure.
Security:
The system should prioritize data encryption (both at rest and in transit) for all sensitive information.
The system shall make use of public-key encryption for higher security.
Availability:
The system should guarantee high availability (99.9% uptime) to ensure access at all times, except during scheduled maintenance periods
Usability:
The system shall uphold its design to Nielsen?s 10 Usability Heuristics for User Interface Design to ensure a user-friendly design.

Constraints
Javier
Specify the relevant constraints such as GDPR compliance specific to data security and privacy
Time: 
The system?s overall development shall respect both the development team & course instructor?s deadlines, affecting depth of development and testing phases.
Regulatory: 
Since the system has academic purposes, it shall adhere to GDPR?s integrity and confidentiality standards to securely store all users information.
GDPR?s generality may complicate development given the data protection prioritization.
The system shall be transparent with users regarding what data will be processed while also minimizing it.
The system shall be GDPR compliant and provide its users with data subject rights.
Hardware:
Storage and computing power may be limited for the first instance of the application.
Integration:
The usage of external APIs for different services might bring up compatibility and reliability challenges.
A proper and fast interaction between the different services should be prioritized for proper functionality, which could be resource intensive.
Encryption:
Even though encryption improves security standards, it also adds layers of authorization, which could lead to delays and longer authorization periods, possibly limiting the up?to-date features.
Portability:
Designing both a handheld & web version of the application might bring up some design challenges throughout development.
Programming Language:
The system?s back-end shall be supported by:
Java JDK 21: for the overall back-end structure, it helps ensure proper segregation between services and their proper interaction. While also, utilizing an Object Oriented design facilitates the management of different features such as user-authentication, role-based access, and more.
Maven: for compilation and dependencies management. Once integrated with Java, it enables the system 
Docker: 
SpringBoot: 
The system?s front-end shall be built mainly in Flutter, and supported by:


Use Case
https://drive.google.com/file/d/1nIoyDVTovPqF6WSYSsHg3KB69YQ22P5V/view?usp=sharing 

System Design and Architecture 
System Design
High-Level Architecture
Layered Architecture
Client-Server Architecture
Event-Driven Architecture
Service-Oriented Architecture
Microservices Architecture
From the architectures described, the following high-level component diagram was outlined, which will be followed further in the report by a developed component diagram:

https://drive.google.com/file/d/1odZTdcW7gJUNxFq5uFWitMnfNHlG_mlq/view?usp=sharing 
Design Patterns and Architecture
UML Class Diagram
Marlene
Refine the created architecture by presenting Design time architecture to be modeled via UML class and component diagrams.

https://lucid.app/lucidchart/e00b1124-b25e-4bc2-a4e7-db0ab8ae0551/edit?viewport_loc=-4112%2C2049%2C3392%2C1995%2CHWEp-vi-RSFO&invitationId=inv_3d0bf8da-719d-4214-9ba5-005e2ee71de5 
Component Diagram
After properl


System Architecture
UML Sequence Diagram
Refine the created architecture by presenting Runtime architecture to be model via sequence and state diagrams.
Marcos

User Management: 
https://drive.google.com/file/d/1iDWba4AtqoJEj5BGBRkQaGF-Q6mDXWD1/view?usp=sharing 

Module management: 
https://drive.google.com/file/d/13jR028Oe_bKQ_YG6zUXWYvQA4v1vjxFn/view?usp=sharing



File Upload & Storage (missing last case, need to watch video of public keys)

https://drive.google.com/file/d/1mjO8DoAzUmWG3DQkH3J0oeo0iUf8mXcT/view?usp=sharing 

Notification and Messaging

https://drive.google.com/file/d/1SEN3NnAW5jGsDiCrB0-DDORNeJTbAGwK/view?usp=sharing 


Timetable
https://drive.google.com/file/d/1Y6PX01lDG-x_wvGn3XbMtzN7dEJZ337I/view?usp=sharing 


Reservation
https://drive.google.com/file/d/1ht1wtrFgYJ9BAlvY007ycZ03V-UZ2Awx/view?usp=sharing 



Feedback
https://drive.google.com/file/d/12b7BkOLyyGgfTJwBSFXP0sDsJ59byM_H/view?usp=sharing 







State Diagram
Case student sends file
https://drive.google.com/file/d/1BGy3Bf_KE-y1f3Dz_fRly5Tb1NvvOjBW/view?usp=sharing 




















Case student joins module
https://drive.google.com/file/d/1dZRJQBuqDdCRmI7gIOIJSXlDr_e6pd5V/view?usp=sharing 















Case student makes reservation
https://drive.google.com/file/d/1hy_fdEXhsdNsRSYsKd9sFQ_CqGcBarLd/view?usp=sharing 



Marcos needs to do 3 states of a task (like send file in chat, join module and library usage)

Features:
Services: Scheduling service, Notification Service and Messaging, private messaging, encryption service, Announcements Service.
Module: Schedules, Subscription to schedule.
Restriction of messages when in class
Support of files such as pdf and images.
Restriction of characters
Profile creation
Mandatory and optional schedules (classes and office hours eg)
Settings
Architecture Style
Javier
Architect the TutorLink app by applying the most suitable architecture style(s) and justify the rationale for the selection of your style. Please note, the app by-default follows a client-server based and layered organization so your selected style must be other than the generic client server and layered architecture.

[Argument FOR microservices], hence the following services were defined:
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

External API Integration Service 
Description: Buenas tryout
Responsibilities:



Appendix
General Sketches of the Software
Note: These sketches are to give a general understanding and overview of what the web version of the application will look like. The finished software will not look exactly the same. 




Link: https://www.canva.com/design/DAGUNfqutIU/EPfQvra8zaUDSQ9BF2qjdA/view?utm_content=DAGUNfqutIU&utm_campaign=share_your_design&utm_medium=link&utm_source=shareyourdesignpanel 
Sources
https://www.perforce.com/blog/alm/how-write-software-requirements-specification-srs-document 
