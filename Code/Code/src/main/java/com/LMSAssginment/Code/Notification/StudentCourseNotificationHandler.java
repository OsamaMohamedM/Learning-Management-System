package com.LMSAssginment.Code.Notification;

import com.LMSAssginment.Code.BusinessLayers.Services.InstructorService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseNotificationHandler implements INotificationHandler{

    private List<User> enrolledStudents = new ArrayList<>();

    private final NotificationRepo notificationRepository;

    public StudentCourseNotificationHandler(NotificationRepo notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void Notify(NotificationInfo info) {

        for(User student: enrolledStudents) {
            Notification notification = new Notification();
            notification.setCourse(info.getCourse());
            notification.setNotificationContent(info.getNotificationContent());
            notification.setnotificationStatue(false);
            notification.setUser(student);

            notificationRepository.save(notification);
        }
    }

    @Override
    public void AddSubscriber(User user) {
        enrolledStudents.add(user);
    }

    @Override
    public void RemoveSubscriber(User user) {
        enrolledStudents.remove(user);
    }

}
