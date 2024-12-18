package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class NotificationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InstructorCourseRepo courseRepo;

    @Autowired
    private StudentCourseRepo studentCourseRepo;


    private final NotificationRepo notificationRepository;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepository = notificationRepo;
    }




    // Create Notification and Notify Observers
    public String createNotification(String notificationData, int courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<User> students = studentCourseRepo.findAllEnrolledUsers(courseId);
        for (User student : students) {
            Notification notification = new Notification();
            notification.setCourse(course);
            notification.setNotificationContent(notificationData);
            notification.setnotificationStatue(false);
            notification.setUser(student);

            notificationRepository.save(notification);
        }


        return "all enrolled students just recieved that notification";
    }

    public List<Notification> getAllNotifications(int userID) {
        List <Notification> tmp= notificationRepository.findNotificationsByUserId(userID);
        for(Notification c: tmp){
            notificationRepository.changeToRead(c.getId());
            c.setnotificationStatue(false);
        }
        return tmp;
    }

    public Notification getByNotificationID(int notificationID, int userID) {
        Notification tmp= notificationRepository.findById(notificationID).orElse(null);
        if(tmp!=null && tmp.getUser().getId()!=userID) return null;

        notificationRepository.changeToRead(tmp.getId());
        tmp.setnotificationStatue(false);

        return tmp;
    }

    public List<Notification> getUnreadNotification(int userID){
        List <Notification> tmp= notificationRepository.getUnreadNotification(userID);
        for(Notification c: tmp){
            notificationRepository.changeToRead(c.getId());
            c.setnotificationStatue(false);
        }
        return tmp;
    }




}
