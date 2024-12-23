package com.LMSAssginment.Code.BusinessLayers.Services;//package com.LMSAssginment.Code.BusinessLayers.Services;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Ensures each test method runs in a transaction and is rolled back after execution
    /*
    * Database Rollback: Since you are using @Transactional,
    *  the transaction is rolled back at the end of the test.
    *  This is why the database is not affected permanently.
    *  If you want to verify that the data is actually saved (during the test execution),
    *  you could manually commit the transaction,
 */
class SendNotificationServiceTest {

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


// Clear all notifications before each test

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
            notificationRepository.deleteAll();
        }


    }

    @Test
    void notifyInstructor() {
        //(this is just a sample, no need to save it for this test)
        int studentId = 1;

        notificationService.notifyInstructor(studentId, course);

        List<Notification> notifications = notificationRepository.findNotificationsByUserId(instructor.getId());

        // Assert that the notification was saved in the database
        assertNotNull(notifications);
        assertEquals(1, notifications.size());
        assertFalse(notifications.getFirst().getnotificationStatue());
        assertEquals("Student with ID: 1 just enrolled in your course: TCourse", notifications.getFirst().getNotificationContent());
        assertEquals(instructor.getId(), notifications.getFirst().getUser().getId());

    }

    @Test
    void createNotificationforAlist() {
        int courseId = course.getId();
        String notificationContent = "Test notification";

        String result = notificationService.createNotificationForAlliStudents(Map.of(
                "Students", new ArrayList<>(List.of(404,students.get(0), students.get(3), students.get(6))),
                "notificationContent", notificationContent
        ), courseId);
        // 4 students , but only the 3 in the array will get a notification

        List<Notification> notifications = new ArrayList<>();
        for(int id : students)notifications.addAll(notificationRepository.findNotificationsByUserId(id));

        assertEquals("done. but only enrolled students got that notification", result);
        assertEquals(3, notifications.size(), "The size of the notifications should be 10");
        int k=0;
        for(int i=0 ;i<=6;i+=3,k++){
            assertEquals(notifications.get(k).getNotificationContent(),"Test notification");
            assertFalse(notifications.get(k).getnotificationStatue());  // read=0 in the beginning
        }
    }

    @Test
    void createNotificationforAll() {
        String result=notificationService.createNotificationforALL("Test notification",course.getId());

        assertEquals("all enrolled students just recieved that notification", result);
        List<Notification> notifications = new ArrayList<>();
        for(int id : students)notifications.addAll(notificationRepository.findNotificationsByUserId(id));

        assertEquals(10, notifications.size(), "The size of the notifications should be 10");

        for(int i=0;i<10;i++){
            assertEquals(notifications.get(i).getNotificationContent(),"Test notification");
            assertFalse(notifications.get(i).getnotificationStatue());  // read=0 in the beginning
        }
    }




}
