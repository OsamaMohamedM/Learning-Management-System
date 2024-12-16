package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentServices {
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private InstructorCourseRepo courseRepo;
    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    public Question getQuestionById(int question_id){
        return  questionsRepo.findById(question_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course getCourseById(int course_id){
        return  courseRepo.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    public Assessment createAssessment(Assessment assessment){
        return assessmentRepository.save(assessment);
    }


    public List<Question> getRandomQuestions(String questionType, int num){
        return questionsRepo.getRandomQuestions(questionType, num);

    }



}
