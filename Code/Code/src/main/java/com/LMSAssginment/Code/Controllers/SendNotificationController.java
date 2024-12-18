package com.LMSAssginment.Code.Controllers;
import com.LMSAssginment.Code.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/{CourseID}/CreateNotification")

public class SendNotificationController {

    @Autowired
    private final NotificationService notificationService;

    public SendNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    // :D
    @PostMapping("/allEnrolled")
    public String createNotification(@RequestBody String notificationContent, @PathVariable int CourseID) {
        // Save notification and return a response
        return notificationService.createNotification(notificationContent,CourseID);

    }
}
