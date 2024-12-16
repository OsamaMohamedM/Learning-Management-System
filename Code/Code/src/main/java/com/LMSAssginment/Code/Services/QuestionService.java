package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepo questionsRepo;
    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    @Autowired
    private InstructorCourseRepo courseRepo;

//    public Question bgrb(){
//        McqQuestion s = new McqQuestion(null, "elso2al","1","2","3","4","elegaba");
//        return mcqrepo.save(s);
//    }


    public McqQuestion addMcqQuestions(McqQuestion mcqQuestions){
        return mcqQuestionRepo.save(mcqQuestions);
    }

    public ShortAnswerQuestion addShortAnswerQuestion(ShortAnswerQuestion shortAnswerQuestion){
        return shortAnswerQuestionRepo.save(shortAnswerQuestion);
    }

    public TrueAndFalseQuestion addTrueAndFalseQuestion(TrueAndFalseQuestion trueAndFalseQuestion){
        return trueAndFalseQuestionRepo.save(trueAndFalseQuestion);
    }

    public Course getCourseById(int course_id){
        return  courseRepo.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


}
