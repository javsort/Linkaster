# Current Status of services
- Service List:
  - CabinetService
  - FeedbackService
  - MessageHandler
  - ModuleManager
  - NotifHandler
  - ReserveService
  - TimetableService
  - UserService

# Current Consistency Verification Method

Verify if services are recognized with `mvn validate` from this folder.

Result should look like this:

```
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for linkaster-services 1.0-SNAPSHOT:
[INFO]
[INFO] linkaster-services ................................. SUCCESS [  0.003 s]
[INFO] userService ........................................ SUCCESS [  0.001 s]
[INFO] moduleManager ...................................... SUCCESS [  0.001 s]
[INFO] cabinetService ..................................... SUCCESS [  0.000 s]
[INFO] notifHandler ....................................... SUCCESS [  0.001 s]
[INFO] timetableService ................................... SUCCESS [  0.000 s]
[INFO] reserveService ..................................... SUCCESS [  0.002 s]
[INFO] feedbackService .................................... SUCCESS [  0.001 s]
[INFO] messageHandler ..................................... SUCCESS [  0.001 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.123 s
[INFO] Finished at: 2024-10-26T18:10:07+02:00
[INFO] ------------------------------------------------------------------------

```

# Managing dependencies
If all the implementation is valid in terms of java (meaning no exceptions or missing behaviour etc), the application should easily retrieve all depenendencies by using:
`.\mvnw clean install` 
- **!** - You can also use `.\mvnw clean install -X` for full de-bugging information.
- **!** - Don't worry if your IDE doesn't recognize the dependencies, it's a common issue with maven projects. Just make sure the project compiles and runs without any issues.

## Dependencies
All generalized dependencies (used throughout the entire project) are all declared under the `linkaster-services/pom.xml` file.
In the generalized dependencies, these are all declared with their current version being used through the entire project.

Now, this does not mean they are already implemented in each service, these still need to be re-declared under the respective `pom.xml` files for each service. **WITHOUT** declaring the version

This is one example, which is the case for most dependencies, but not all:
For the time being, and already tested with `.\mvnw clean install -X`, please use `services/cabinetService/pom.xml` as a reference if you change any pom.xml file.
- Main `pom.xml` file:
```
...

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>dependency.group.id</groupId>
      <artifactId>dependency-artifact-id</artifactId>
      <version>3.0.0</version>                        -> VERSION IS ONLY DECLARED HERE
    </dependency>
    
    ....
  </dependencies>
</dependencyManagement>
...
```

- Any services' `services/<any-service>/pom.xml` file
```
<dependencies>
  <dependency>
    <groupId>dependency.group.id</groupId>
    <artifactId>dependency-artifact-id</artifactId>
  </dependency>
      
  ....
</dependencies>
```