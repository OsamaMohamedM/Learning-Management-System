package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AssessmentServicesTest {

    @Autowired
    private AssessmentServices assessmentServices;



    @Autowired
    private SignUpService signUpService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private StudentAssessmentRepo studentAssessmentRepo;


    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    private Instructor instructor;
    private Course course;

    List<Integer> listt=new ArrayList<>();

    @BeforeEach
    void setUp() {
        deleteQuestionFromDatabase();
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

        // 12 questions in the database kds
        for (int i = 1; i <= 4; i++) {
            McqQuestion mcqQuestion = new McqQuestion(null, "MCQ Question "+i, "Option A", "Option B", "Option C", "Option D", "Option A","mcq");
            mcqQuestionRepo.save(mcqQuestion);
            listt.add(mcqQuestion.getId());
        }

        for (int i = 1; i <= 4; i++) {
            ShortAnswerQuestion saQuestion = new ShortAnswerQuestion(null, "SA Question " + i, "Answer " + i,"sa");
            shortAnswerQuestionRepo.save(saQuestion);
            listt.add(saQuestion.getId());
        }

        for (int i = 1; i <= 4; i++) {
            TrueAndFalseQuestion tfQuestion = new TrueAndFalseQuestion(null, "TF Question " + i, i % 2 == 0,"tf");
            trueAndFalseQuestionRepo.save(tfQuestion);
            listt.add(tfQuestion.getId());
        }
    }


    @Test
    void getRandomQuestions() {
        List<Question> ALL=assessmentServices.getRandomQuestions("mcq",3);
        ALL.addAll(assessmentServices.getRandomQuestions("sa",2));
        ALL.addAll(assessmentServices.getRandomQuestions("tf",2));
        int mcqcnt=0,sacnt=0,tfcnt=0;
        for(Question question: ALL){
            assertNotNull(questionsRepo.findById(question.getId()).orElse(null));
            if(question.getQuestionType()=="mcq") mcqcnt++;
            else if(question.getQuestionType()=="sa") sacnt++;
            else tfcnt++;
        }
//        assertNotNull(questionsRepo.findById(40404).orElse(null));

        assertEquals(mcqcnt,3);
        assertEquals(tfcnt,2);
        assertEquals(sacnt,2);
    }

    @Test
    void createAssessment() throws Exception {
        Map<String, Object> assessmentData = new HashMap<>();
        assessmentData.put("random", false);
        assessmentData.put("totalNumberOfQuestions", 3);
        assessmentData.put("questions", listt); // will take only the first 3 ( I edited the assessment Services )
        assessmentData.put("startDate", "2024-12-31");
        assessmentData.put("totalGrades", 100);
        assessmentData.put("duration", 1.5);
        assessmentData.put("type", "quiz");

        Assessment tmp=assessmentServices.createAssessment(course,assessmentData);

        List<Assessment> assessments=assessmentRepository.findAll();
        assertEquals(1,assessments.size());
        assertEquals(tmp,assessments.get(0));

    }

    void deleteQuestionFromDatabase(){
        studentAssessmentRepo.deleteAll();
        assessmentRepository.deleteAll();
        questionsRepo.deleteAll();
        mcqQuestionRepo.deleteAll();
        shortAnswerQuestionRepo.deleteAll();
        trueAndFalseQuestionRepo.deleteAll();
    }

}
