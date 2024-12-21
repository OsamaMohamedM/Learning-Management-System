package com.LMSAssginment.Code.Notification;

import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;

public class InstructorNotificationHandler implements INotificationHandler{

    private Instructor instructor;
    private final NotificationRepo notificationRepository;

    public InstructorNotificationHandler(NotificationRepo notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void Notify(NotificationInfo info) {
        Notification notification = new Notification();
        notification.setnotificationStatue(false);
        notification.setUser(instructor);
        notification.setCourse(info.getCourse());
        notification.setNotificationContent("Student with ID: "
                + info.getStudent().getId() + " just enrolled in your course: "
                + info.getCourse().getName());

        notificationRepository.save(notification);
    }

    @Override
    public void AddSubscriber(User user) {
        if (user instanceof Instructor) {
            instructor = (Instructor) user;
        }
    }

    @Override
    public void RemoveSubscriber(User user) {
       instructor = null;
    }
}
