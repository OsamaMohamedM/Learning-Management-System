package com.LMSAssginment.Code;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.BusinessLayers.Services.CourseService;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;

//@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourseManagement {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorCourseRepo courseRepository;

    @Autowired
    private UserRepo instructorCourseRepo;

    @Autowired
    private SignUpService signUpService;

    private Instructor instructor;
    private Course course;


    @BeforeEach
    public void setup() {
        Instructor instructor = new Instructor();
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
    }

    @Test
    public void testAddCourse() {
        instructor = (Instructor) instructorCourseRepo.findByEmail("123@gmail.com").get();
        int instructorId = instructor.getId();
        courseService.addCourse(course, instructorId);
        Course addedCourseOpt = courseRepository.findById(course.getId()).get();
        assertNotEquals(addedCourseOpt, null);
    }


    @Test
    public void testNotRepetedCourse() {
        instructor = (Instructor) instructorCourseRepo.findByEmail("123@gmail.com").get();
        int instructorId = instructor.getId();
        courseService.addCourse(course, instructorId);
        Course course2 = new Course();
        course2.setName("TCourse");
        course2.setMaxNumberOfStudent(100);
        course2.setDescription("This is a test course");
        course2.setInstructor(instructor);

        courseService.addCourse(course2, instructorId);

        assertTrue(courseRepository.findById(course2.getId()).isEmpty());
    }

    @Test
    public void testUpdateCourseWithInvalidData() {
        instructor = (Instructor) instructorCourseRepo.findByEmail("123@gmail.com").get();
        int instructorId = instructor.getId();
        courseService.addCourse(course, instructorId);
        course.setMaxNumberOfStudent(0);
        courseService.updateCourse(course);
        Course updatedCourse = courseRepository.findById(course.getId()).get();
        assertTrue(updatedCourse.getMaxNumberOfStudent() == 100);
    } 

    @Test
    public void testDeleteCourse() {
        instructor = (Instructor) instructorCourseRepo.findByEmail("123@gmail.com").get();
        int instructorId = instructor.getId();
        courseService.addCourse(course, instructorId);
        assertTrue(courseRepository.findById(course.getId()).isPresent());
        courseService.deleteCourse(course.getId());
        assertFalse(courseRepository.findById(course.getId()).isPresent());
    }

    @AfterEach
    public void cleanup() {
        // Delete the course if it exists
        if (course != null) {
            courseRepository.deleteById(course.getId());
        }
        // Delete the instructor if it exists
        if (instructor != null) {
            instructorCourseRepo.deleteById(instructor.getId());
        }
    }
}