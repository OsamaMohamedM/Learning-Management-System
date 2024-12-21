package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Ensures each test method runs in a transaction and is rolled back after execution
class GetNotificationServiceTest {

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private NotificationRepo notificationRepository;


    @Autowired
    private CourseService courseService;


    @Autowired
    private SignUpService signUpService;

    @Autowired
    private EnrollService enrollService;


    private Instructor instructor;
    private Course course;
    List<Integer> students=new ArrayList<>();

    @BeforeEach
    public void setup() {
        students.clear();
        instructor = new Instructor();
        instructor.setName("TA");
        instructor.setUserType("Instructor");
        instructor.setPassword("password");
        instructor.setEmail("123@gmail.com");
        signUpService.AddNewUser(instructor);



        course = new Course();
        course.setName("TCourse");
        course.setMaxNumberOfStudent(100);
        course.setDescription("This is a test course");
        course.setInstructor(instructor);
        course.setLessons(new ArrayList<>());
        courseService.addCourse(course,instructor.getId());

        for (int i = 0; i < 10; i++) {
            String name = "Student" + i;
            String password = "password" + i;
            String email = "student" + i + "@example.com";
            String userType = "Student";
            double gpa = 4;
            Student student = new Student(name, password, email, null, null, userType, gpa);
            signUpService.AddNewUser(student);
            students.add(student.getId());
            enrollService.enroll(course.getId(),student.getId()); // the enroll process automatically notifies the students , so I remove that
        }


    }

    @Test
    void getAllNotifications() {
        // when I enrolled 10 students , the instructor should get 10 Notifications with students that enrolled
        List<Notification> notifications=notificationService.getAllNotifications(instructor.getId());

        assertEquals(10, notifications.size(), "The size of the notifications should be 10");

        for(int i=0;i<10;i++){
            assertEquals(notifications.get(i).getNotificationContent(),"Student with ID: " +students.get(i)+" just enrolled in your course: TCourse");
            assertTrue(notifications.get(i).getnotificationStatue());  // if I get it with the service , then it becomes read , true
        }

    }

    @Test
    void getByNotificationID() {
        List<Notification> notifications=notificationRepository.findNotificationsByUserId(instructor.getId());
        int randomNotID=notifications.get(3).getId();
        // only the instructor could access that
        // let's try getting that notification once by the instructor and once by a random student
        Notification byStudent=notificationService.getByNotificationID(randomNotID,students.get(2));
        Notification byInstructor=notificationService.getByNotificationID(randomNotID,instructor.getId());

        assertNull(byStudent);
        assertNotNull(byInstructor);
        assertTrue(byInstructor.getnotificationStatue()); // its read !
    }

    @Test
    void getUnreadNotification() {
        List<Notification> notifications=notificationRepository.findNotificationsByUserId(instructor.getId());
        // let's make 2 of these notifications to read in the database
        notificationRepository.changeToRead(notifications.get(0).getId());
        notificationRepository.changeToRead(notifications.get(1).getId());

        // now let's get 8 ones b2a
        List<Notification> newNotifications=notificationService.getUnreadNotification(instructor.getId());
        assertEquals(8,newNotifications.size());
    }
}