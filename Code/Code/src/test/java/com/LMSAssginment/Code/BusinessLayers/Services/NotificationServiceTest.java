package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.BusinessLayers.Services.NotificationService;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private InstructorCourseRepo courseRepo;

    @Mock
    private StudentCourseRepo studentCourseRepo;

    @Mock
    private NotificationRepo notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotifications_shouldReturnAndMarkAllNotificationsAsRead() {
        // Arrange
        int userId = 1;

        User user = new Student();
        user.setId(userId);
        userRepo.save(user);
        // Mock Notifications
        Notification notification1 = new Notification();
        notification1.setId(1);
        notification1.setUser(user);
        notification1.setnotificationStatue(false);

        Notification notification2 = new Notification();
        notification2.setId(2);
        notification2.setUser(user);
        notification2.setnotificationStatue(false);

        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifications(userId)).thenReturn(notifications);

        // Act
        List<Notification> result = notificationService.getAllNotifications(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getnotificationStatue());
        assertTrue(result.get(1).getnotificationStatue());

        // Verify interactions
        verify(notificationRepository, times(2)).changeToRead(anyInt());

        // Ensure notifications were marked as read
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(notificationRepository, times(2)).changeToRead(captor.capture());
        List<Integer> capturedIds = captor.getAllValues();
        assertTrue(capturedIds.contains(1));
        assertTrue(capturedIds.contains(2));
    }
}