package com.LMSAssginment.Code.BusinessLayers.Controllers;
import com.LMSAssginment.Code.BusinessLayers.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/{course_id}/createNotification")

public class SendNotificationController {

    @Autowired
    private final NotificationService notificationService;



    public SendNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    // ( students and instructors )
    @PostMapping("/allEnrolled")
    public String createNotificationforALL(@RequestBody String notificationContent, @PathVariable int courseId) {
        // Save notification and return a response
        return notificationService.createNotificationforALL(notificationContent,courseId);
    }

    @PostMapping("/specific")
    public String createNotification(@RequestBody Map<String,Object> ob, @PathVariable int courseId) {
        // Save notification and return a response
        return notificationService.createNotificationForAlliStudents(ob,courseId);
    }

    /*

http://localhost:8080/{Course_id}CreateNotifications/SpecificStudents]
(Post) Json: MAP

{

 "notificationContent”: “Quiz Retake 2morrow”,

“Students” : [1, 2 ,3 ,4]

}*/

}
