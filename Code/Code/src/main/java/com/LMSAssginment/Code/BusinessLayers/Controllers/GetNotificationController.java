package com.LMSAssginment.Code.Controllers;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import com.LMSAssginment.Code.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{userID}/Notifications")
public class GetNotificationController {

    @Autowired
    private final NotificationService notificationService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InstructorCourseRepo courseRepo;


    public GetNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    //  // :D
    @GetMapping("/unread")
    public List<Notification> getUnreadNotification (@PathVariable int userID){
        return notificationService.getUnreadNotification(userID);
    }

    // :D
    @GetMapping("/{notificationID}")  // specific
    // display all that one notification and set it to read
    public Notification getNotificationID(@PathVariable int userID,@PathVariable int notificationID) {
        return notificationService.getByNotificationID(notificationID,userID);

    }

    // :D
    @GetMapping("/all") // all
    // display all notificaitons and set them to read
    public List<Notification> getAllNotifications(@PathVariable int userID) {
        return notificationService.getAllNotifications(userID);

    }
}


