package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Repos.McqQuestionRepo;
import com.LMSAssginment.Code.DateLayers.Repos.ShortAnswerQuestionRepo;
import com.LMSAssginment.Code.DateLayers.Repos.TrueAndFalseQuestionRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;
    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;
    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;
    @Autowired
    private QuestionService questionService;

    private Instructor instructor;
    private Course course;




    @BeforeEach
    public void setup() {
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
    }

    @Test
    void addMcqQuestions() {
        McqQuestion mcq= new McqQuestion(course,"1+1=?",
                "3","4","5","2","2","mcq");
        questionService.addMcqQuestions(mcq);


        assertEquals(mcq.getId(), mcqQuestionRepo.findMcqQuestionbyQuestionId(mcq.getId()).getId());

    }

    @Test
    void addShortAnswerQuestion() {
        ShortAnswerQuestion sa= new ShortAnswerQuestion(course,"1+1=?","2","sa");
        questionService.addShortAnswerQuestion(sa);


        assertEquals(sa.getId(), shortAnswerQuestionRepo.findShortAnswerQuestionbyQuestionId(sa.getId()).getId());
    }

    @Test
    void addTrueAndFalseQuestion() {
        TrueAndFalseQuestion TF= new TrueAndFalseQuestion(course,"YesorNO??!ANSWERMEEE",
               true ,"tf");
        questionService.addTrueAndFalseQuestion(TF);



        assertEquals(TF.getId(), trueAndFalseQuestionRepo.findTrueAndFalseQuestionbyQuestionId(TF.getId()).getId());

    }
}