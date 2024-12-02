package com.linkaster.notifHandler.control;

import com.linkaster.notifHandler.service.NotificationPusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/* 
 * This is the controller for notifications. 
 * Use this controller when you want a notification pushed or delayed. 
 * You must first establish whether the user is available or not. 
 * You can do this by calling isAvailable(userID) in the timetable repository       -- TO BE IMPLEMENTED!
 */
@RestController
public class NotificationController {
    private final NotificationPusherService notificationPusherService;

    @Autowired
    public NotificationController(NotificationPusherService notificationPusherService) {
        this.notificationPusherService = notificationPusherService;
    }

    //@Override
    //public 


    //endpoints:



    //push notification endpoint

    //delay notification endpoint 
}
//Handle incoming requests, contains endpoints 
//requests are :
// takes "send notification" request
// has "push notification" and "delay notification" endpoints
    //check if user is available:
    //public boolean isAvailable(int receiverID) {
    //   return true;
    //}