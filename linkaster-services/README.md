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