package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentAssessmentResponseServiceTest {

    @Autowired
    private StudentAssessmentResponseService studentAssessmentResponseService;

    @Autowired
    private StudentAssessmentRepo studentAssessmentRepo;

    @Autowired
    private AssessmentGraderepo assessmentGraderepo;

    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    AssessmentRepository assessmentRepository;


    @Autowired
    EnrollService enrollService;

    private Course course;
    private Instructor instructor;
    private Student student;

    private Assessment assessment;

    @BeforeEach
    public void setup() {
        // Setting up a sample course and assessment

        instructor = new Instructor();
        instructor.setName("TA");
        instructor.setUserType("Instructor");
        instructor.setPassword("password");
        instructor.setEmail("123@gmail.com");
        signUpService.AddNewUser(instructor);




        course = new Course();
        course.setName("Sample Course");
        course.setMaxNumberOfStudent(50);
        course.setDescription("A course for testing purposes.");
        course.setLessons(new ArrayList<>());
        course.setInstructor(instructor);
        courseService.addCourse(course,instructor.getId());


        McqQuestion mcqQuestion = new McqQuestion(course, "What is 2+2?", "1", "2", "3", "4", "4", "mcq");
        mcqQuestionRepo.save(mcqQuestion);

        assessment = new Assessment();
        assessment.setCourse(course);
        assessment.setType("quiz");
        assessment.setStartDate(new Date());
        assessment.setDuration(1.5);
        assessment.setTotalGrades(100);
        assessment.setTotalNumberOfQuestions(4);

        List<Question> questions = new ArrayList<>();
        questions.add(mcqQuestion);
        assessment.setQuestions(questions);

        assessmentRepository.save(assessment);

        String name = "Student1";
        String password = "password";
        String email = "student"+"@example.com";
        String userType = "Student";
        double gpa = 4;
         student = new Student(name, password, email, null, null, userType, gpa);
        signUpService.AddNewUser(student);
        enrollService.enroll(course.getId(),student.getId()); // the enroll process automatically notifies



    }

    @Test
    void saveAssignment() throws Exception {
        String testFileName = "testFile.txt";
        byte[] testFileContent = "Test content".getBytes(StandardCharsets.UTF_8);
        MultipartFile multipartFile = new MockMultipartFile(testFileName, testFileName, "text/plain", testFileContent);

        studentAssessmentResponseService.saveAssignment(multipartFile, course, 1, 1, 1, assessment);

        assertEquals(1, studentAssessmentRepo.findAll().size());
        assertEquals(1, assessmentGraderepo.findAll().size());
    }

    @Test
    void showAssessment() {
        // Calling the method to test
        List<String> result = studentAssessmentResponseService.showAssessment(assessment.getId(), course.getId());

        // Expected output for assertions
        List<String> expected = new ArrayList<>();
        expected.add("quiz#" + assessment.getId());
        expected.add("Start date " + assessment.getStartDate().toString());
        expected.add("Duration " + assessment.getDuration());
        expected.add("Total grades " + assessment.getTotalGrades());
        int grades = assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions();
        expected.add("Total number of question is " + assessment.getTotalGrades() +
                " so each question will have about " + grades +
                " grade and bones = " + (assessment.getTotalGrades() - (grades * assessment.getTotalNumberOfQuestions())) + " grade");
        expected.add("1) What is 2+2?");
        expected.add("a) 1");
        expected.add("b) 2");
        expected.add("c) 3");
        expected.add("d) 4");

        assertEquals(expected.size(), result.size(), "The number of elements in the result should match the expected size.");
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i), "Mismatch at index " + i);
        }
    }


    @Test
    void submitQuiz() {
        Map<String, String> studentAnswers = new HashMap<>();
        studentAnswers.put("q1", "4");

        String result = studentAssessmentResponseService.submitQuiz(1, assessment.getId(), course.getId(), studentAnswers);

        assertTrue(result.contains("Your correct questions is: 1"));
        assertTrue(result.contains("Your grades is: 25"));
    }

    @Test
    // kml enta IDK what to do hena and really tired :(((
    void giveManualFeedback() {
        Map<String, String> instructorFeedback = new HashMap<>();
        Integer tmp= student.getId();
        instructorFeedback.put("student_id", tmp.toString());
        instructorFeedback.put("grade", "85");
        instructorFeedback.put("feed_back", "Good work!");

        AssessmentGrade updatedGrade = studentAssessmentResponseService.giveManualFeedback(assessment.getId(), course.getId(), instructorFeedback);

        assertEquals(85, updatedGrade.getGrade());
        assertEquals("Good work!", updatedGrade.getFeedback());
    }

}
