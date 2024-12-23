package com.LMSAssginment.Code.BusinessLayers.Services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import com.LMSAssginment.Code.DateLayers.Model.Notification;
import com.LMSAssginment.Code.DateLayers.Repos.NotificationRepo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestEnrollment {

    @Autowired
    private NotificationRepo notificationRepo;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @Autowired
    private EnrollService enrollService;

    @Autowired
    private UserRepo studentRepo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepo instructorCourseRepo;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private StudentCourseRepo repository;

    @Autowired
    private InstructorCourseRepo courseRepository;

    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeAll
    public void setup() {
        student = new Student();
        student.setName("student");
        student.setUserType("STUDENT");
        student.setPassword("password");
        student.setEmail("s5@" +
                "gmail.com");
        signUpService.AddNewUser(student);
        instructor = new Instructor();
        instructor.setName("TA");
        instructor.setUserType("INSTRUCTOR");
        instructor.setPassword("password");
        instructor.setEmail("i1@gmail.com");
        signUpService.AddNewUser(instructor);
        course = new Course();
        course.setName("EnrollCourse");
        course.setMaxNumberOfStudent(100);
        course.setDescription("This is a test course");
        course.setInstructor(instructor);

        instructor = (Instructor) instructorCourseRepo.findByEmail("i1@gmail.com").get();
        int instructorId = instructor.getId();
        courseService.addCourse(course, instructorId);
        course = (Course) courseRepository.getCoursesByInstructorId(instructorId);
    }

    @Test
    public void testEnroll() {
        student = (Student) studentRepo.findByEmail("s5@gmail.com").get();
        int studentId = student.getId();
        int courseId = course.getId();

        enrollService.enroll(courseId, studentId);

        List<StudentCourse> studentCourses = repository.findAll();

        boolean found = false;
        for (StudentCourse sc : studentCourses) {
            if (sc.getStudent().getId() == studentId && sc.getCourse().getId() == courseId) {
                found = true;
                break;
            }
        }
        assertTrue(courseRepository.findById(course.getId()).get()!=null);
        assertNotNull(courseRepository.findById(course.getId()).get());
        assertTrue(courseRepository.findById(course.getId()).get().getInstructor().getId() == instructor.getId());
        assertTrue(found);
    }

    @Test
    public void testEnrollWithInvalidStuudentId() {
        student = (Student) studentRepo.findByEmail("s5@gmail.com").get();
        int studentId = student.getId();
        int courseId = course.getId();
        enrollService.enroll(courseId, 0);
        List<StudentCourse> studentCourses = repository.findAll();

        boolean found = false;
        for (StudentCourse sc : studentCourses) {
            if (sc.getStudent().getId() == studentId && sc.getCourse().getId() == courseId) {
                found = true;
                break;
            }
        }

        assertFalse(found);
    }

    @Test
    public void testEnrollWithInvalidCourseId() {
        student = (Student) studentRepo.findByEmail("s5@gmail.com").get();
        int studentId = student.getId();
        int courseId = course.getId();
        enrollService.enroll(0, studentId);
        List<StudentCourse> studentCourses = repository.findAll();

        boolean found = false;
        for (StudentCourse sc : studentCourses) {
            if (sc.getStudent().getId() == studentId && sc.getCourse().getId() == courseId) {
                found = true;
                break;
            }
        }

        assertFalse(found);
    }

    @Test
    public void testDrop() {
        student = (Student) studentRepo.findByEmail("s5@gmail.com").get();
        int studentId = student.getId();
        int courseId = course.getId();
        enrollService.enroll(courseId, studentId);
        enrollService.drop(courseId, studentId);
        List<StudentCourse> studentCourses = repository.findAll();
        boolean found = false;
        for (StudentCourse sc : studentCourses) {
            if (sc.getStudent().getId() == studentId && sc.getCourse().getId() == courseId) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @AfterAll
    public void cleanup() {
        notificationRepo.deleteAll();
        studentRepo.delete(student);
        instructorCourseRepo.deleteById(instructor.getId());
        courseService.deleteCourse(course.getId());
        repository.deleteAll();
    }

}