package com.LMSAssginment.Code.BusinessLayers.Services;

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

    @Autowired
    private EmailService emailService;



    public void notifyInstructor(int studentId, Course course) {
        User instructor = userRepo.findById(course.getInstructor().getId()).orElse(null);
        Notification notification = new Notification();
        notification.setnotificationStatue(false);
        notification.setUser(instructor);
        notification.setCourse(course);
        notification.setNotificationContent("Student with ID: " + studentId + " just enrolled in your course: " + course.getName());

        if(instructor!=null){
            notificationRepository.save(notification);

            emailService.sendEmail(instructor.getEmail(), "Enrol" , "Student with ID: " + studentId + " just enrolled in your course: " + course.getName());
        }




    }



    // specifc
    public String createNotificationForAlliStudents(Map<String,Object> ob, int courseId) {
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
            emailService.sendEmail(user.getEmail() , "Attention" , notificationData);
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
            emailService.sendEmail(student.getEmail() , "Attention" , notificationData);
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
