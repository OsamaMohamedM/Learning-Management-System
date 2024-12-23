package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.BusinessLayers.Services.EmailService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private NotificationRepo notificationRepository;
    private EmailService emailService;



    public void notifyInstructor(int studentId, Course course) {
        User instructor = userRepo.findById(course.getInstructor().getId()).orElse(null);
        Notification notification = new Notification();
        notification.setnotificationStatue(false);
        notification.setUser(instructor);
        notification.setCourse(course);
        notification.setNotificationContent("Student with ID: " + studentId + " just enrolled in your course: " + course.getName());

        if(instructor!=null)
        emailService.sendEmail(instructor.getEmail() , "Enrrol" , "Student with ID: " + studentId + " just enrolled in your course: " + course.getName());
        notificationRepository.save(notification);

    }



    // specifc
    public String createNotificationforAlist(Map<String,Object> ob, int courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Integer> student_ids=(List<Integer>) ob.get("Students");

        String notificationData= (String) ob.get("notificationContent");
        for(Integer id: student_ids){
           User user=  studentCourseRepo.findSpecificEnrolledUser(courseId,id);
           if(user==null) continue;
            Notification notification = new Notification();
            notification.setCourse(course);
            notification.setNotificationContent(notificationData);
            notification.setnotificationStatue(false);
            notification.setUser(user);
            notificationRepository.save(notification);
        }

        return "done. but only enrolled students got that notification";
    }



    public String createNotificationforALL(String notificationData, int courseId) {
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
            c.setnotificationStatue(true);
        }
        return tmp;
    }

    public Notification getByNotificationID(int notificationID, int userID) {
        Notification tmp= notificationRepository.findById(notificationID).orElse(null);
        if(tmp!=null && tmp.getUser().getId()!=userID) return null;

        notificationRepository.changeToRead(tmp.getId());
        tmp.setnotificationStatue(true);

        return tmp;
    }

    public List<Notification> getUnreadNotification(int userID){
        List <Notification> tmp= notificationRepository.getUnreadNotification(userID);
        for(Notification c: tmp){
            notificationRepository.changeToRead(c.getId());
            c.setnotificationStatue(true);
        }
        return tmp;
    }




}
