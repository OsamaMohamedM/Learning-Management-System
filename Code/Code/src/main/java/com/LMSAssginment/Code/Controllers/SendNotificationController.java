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


    // ( students and instructors )
    @PostMapping("/allEnrolled")
    public String createNotificationforALL(@RequestBody String notificationContent, @PathVariable int CourseID) {
        // Save notification and return a response
        return notificationService.createNotificationforALL(notificationContent,CourseID);
    }

    @PostMapping("/Specific")
    public String createNotification(@RequestBody Map<String,Object> ob, @PathVariable int CourseID) {
        // Save notification and return a response
        return notificationService.createNotificationforAlist(ob,CourseID);
    }

    /*

http://localhost:8080/{Course_id}CreateNotifications/SpecificStudents]
(Post) Json: MAP

{

 "notificationContent”: “Quiz Retake 2morrow”,

“Students” : [1, 2 ,3 ,4]

}*/

}
