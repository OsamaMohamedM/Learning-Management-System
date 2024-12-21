package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import com.LMSAssginment.Code.Notification.INotificationHandler;
import com.LMSAssginment.Code.Notification.InstructorNotificationHandler;
import com.LMSAssginment.Code.Notification.NotificationInfo;
import com.LMSAssginment.Code.Notification.StudentCourseNotificationHandler;
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


    private final NotificationRepo notificationRepository;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepository = notificationRepo;
    }

    

    public void notifyInstructor(int studentId, Course course) {
        User instructor = userRepo.findById(course.getInstructor().getId()).orElse(null);
        Student student = (Student)userRepo.findById(studentId).orElse(null);
        NotificationInfo info = new NotificationInfo();
        info.setStudent(student);
        info.setCourse(course);

        INotificationHandler notificationHandler =
                new InstructorNotificationHandler(notificationRepository);
        notificationHandler.AddSubscriber(instructor);

        notificationHandler.Notify(info);
    }

    // specifc
    public String createNotificationforAlist(Map<String,Object> ob, int courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Integer> student_ids= (List<Integer>) ob.get("Students");
        String notificationData= (String) ob.get("notificationContent");

        // Fill the notification content object
        NotificationInfo notification = new NotificationInfo();
        notification.setCourse(course);
        notification.setNotificationContent(notificationData);

        INotificationHandler notificationHandler =
                new StudentCourseNotificationHandler(notificationRepository);
        for(Integer id: student_ids){
           User user = studentCourseRepo.findSpecificEnrolledUser(courseId,id);
           if(user == null)
                 continue;
           notificationHandler.AddSubscriber(user);
        }
        notificationHandler.Notify(notification);

        return "done. but only enrolled students got that notification";
    }
    public String createNotificationforALL(String notificationData, int courseId) {

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        NotificationInfo notification = new NotificationInfo();
        notification.setCourse(course);
        notification.setNotificationContent(notificationData);

        INotificationHandler notificationHandler =
                new StudentCourseNotificationHandler(notificationRepository);

        List<User> students = studentCourseRepo.findAllEnrolledUsers(courseId);
        for (User student : students) {
            notificationHandler.AddSubscriber(student);
        }
        notificationHandler.Notify(notification);

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
