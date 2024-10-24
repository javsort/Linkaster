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
Finished
Needs proof-reading
In progress
Not started
Introduction
Purpose
Intended Audience
Product Scope
Definitions and Acronyms 
Overall Description
User Classes and Characteristics 
Assumptions and Dependencies
System Features and Requirements
Functional Requirements
Non-Functional Requirements
Interface Details
Constraints 
Architecture 
Architecture Style
Design-Time Architecture
UML Class Diagram
Component Diagram
Runtime Architecture
UML Sequence Diagram
State Diagram 
Appendix
Sources

Introduction
Purpose
	The purpose of L!nkaster is to provide students and educators at Lancaster University Leipzig with an app and website that they can use for all their university-related needs. LUL students are required to use a variety of tools, each specialized for a single task. Timetabler, for example, is a website that is used exclusively for students to view their schedule. Several of these tools, such as Moodle, require students to log in every time, some even requiring two-factor authentication. L!nkaster combines all relevant tools into one app and website so that students can access all their educational information in one place without the hassle of logging in to each one or installing multiple apps. 
Students and instructors can easily communicate with each other through L!nkaster without needing to exchange personal information (account creation simply requires an LUL email address). Instructors can use this to notify students about canceling class, a new assignment being posted, grades being released, and other academic announcements. Furthermore, each module automatically generates a group chat with enrolled students to encourage students to communicate and provides them with a space to ask questions, share notes, and generally form connections. 
To summarize, L!nkaster is a single platform that combines many different tools into one and is specifically customized to LUL. It promotes communication and teamwork between classmates and is overall an efficient alternative to the many different tools LUL currently uses. 
Intended Audience
This document informs the reader about L!nkaster by describing its uses and users. It also goes into depth about the system?s requirements, defines the classes it will use, and describes the architecture with which the software will be built. The reader should be familiar with software engineering concepts such as the different types of software architecture, common programming languages, and should have a good understanding of how software is built. The reader should also know how to read UML class and sequence diagrams, component diagrams, and state diagrams. The reader is encouraged to independently research any terms they may not be familiar with. The document should be read in the numerical order of its sections (1.1, then 1.2, and so on).
Product Scope
	L!nkaster is intended for students, educators, and other staff of LUL, though different tools are made with more specific audiences in mind. For instance, the announcement feature is intended for educators to use while the group chats are made for inter-student communication. The different types of users (teacher, student, class representative student, etc) can be assigned to anyone within LUL. This means that regular students can assume administrative roles by, for instance, becoming the leader of a club. 
L!nkaster should be used mainly for academic purposes when communicating with instructors or classmates about module-related topics, planning their academic workload, and viewing their class schedule. However, other uses are also supported. The private chats feature allows students to chat with anyone without a filter that might deter them from casual communication. Additionally, modules are typically courses but can also be clubs or other extracurricular activities, meaning that all academic features can also be used for extracurriculars. 
Definitions and Acronyms 
LUL: Lancaster University Leipzig


Overall Description
User Classes and Characteristics 
Students
Student: 
	Students are the largest amount of expected users. They have limited functionalities when it comes to publishing announcements and notifications, but they are capable of consulting their timetable, receiving notifications, and joining classes.

Class Representative Student:
	Class Representative Students are the ones that the students of the program nominate. They can make announcements to the students of the program regarding the problems or news in it.

Social Committee Student:
	Social Committee Students are part of the social committee of the university. They have the ability to create social-committee related announcements to all the students in order to notify the events or promotion of new clubs to them. 

Club Student:
	Club Students are created by the administrative teacher. They can make club-related announcements to the students part of the club.
	
Teachers
Module Teacher:
The module teacher is a user type assigned by the administrative teacher. This type of teacher is capable of creating a group within the module (for group assignments), uploading files for the modules, and can make module-related announcements.

Administrative Teacher:
	The Administrative Teacher involves any faculty member in an administrative position at the university. They are one of the user types with the highest priorities. Since they are able to create modules and assign other Teachers/Club Students to it, they are also capable of sending generalized announcements and reminders, while also being capable of seeing the information in their created modules.
Assumptions and Dependencies
<List any assumed factors (as opposed to known facts) that could affect the requirements stated in the SRS. These could include third-party or commercial components that you plan to use, issues around the development or operating environment, or constraints. The project could be affected if these assumptions are incorrect, are not shared, or change. Also identify any dependencies the project has on external factors, such as software components that you intend to reuse from another project, unless they are already documented elsewhere (for example, in the vision and scope document or the project plan).>

System Features and Requirements
System Features and Functional Requirements
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

Interface Details

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


Architecture 
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

External API Integration 
Description: Buenas tryout
Responsibilities:

Design-Time Architecture
UML Class Diagram
Marlene
Refine the created architecture by presenting Design time architecture to be modeled via UML class and component diagrams.
Component Diagram
Marlene
Runtime Architecture
UML Sequence Diagram
Refine the created architecture by presenting Runtime architecture to be model via sequence and state diagrams.
Marcos
State Diagram 
Marcos

Features:
Services: Scheduling service, Notification Service and Messaging, private messaging, encryption service, Announcements Service.
Module: Schedules, Subscription to schedule.
Restriction of messages when in class
Support of files such as pdf and images.
Restriction of characters
Profile creation
Mandatory and optional schedules (classes and office hours eg)
Settings


Appendix
General Sketches of the Software
Note: These sketches are to give a general understanding and overview of what the web version of the application will look like. The finished software will not look exactly the same. 




Link: https://www.canva.com/design/DAGUNfqutIU/EPfQvra8zaUDSQ9BF2qjdA/view?utm_content=DAGUNfqutIU&utm_campaign=share_your_design&utm_medium=link&utm_source=shareyourdesignpanel 
Sources
https://www.perforce.com/blog/alm/how-write-software-requirements-specification-srs-document 