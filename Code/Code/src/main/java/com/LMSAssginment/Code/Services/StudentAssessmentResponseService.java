package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAssessmentResponseService {
    @Autowired
    StudentAssessmentRepo studentAssessmentRepo;
    @Autowired
    AssessmentGraderepo assessmentGraderepo;


    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    public Assessment displayAssessment(int assessmentId, int courseId) {
        return studentAssessmentRepo.findAssessmentById(assessmentId, courseId);
    }

    public McqQuestion getMcqQuestionbyQuestionId(int id){
        return mcqQuestionRepo.findMcqQuestionbyQuestionId(id);
    }

    public ShortAnswerQuestion getShortAnswerQuestionbyQuestionId(int id){
        return shortAnswerQuestionRepo.findShortAnswerQuestionbyQuestionId(id);
    }

    public TrueAndFalseQuestion getTrueAndFalseQuestionbyQuestionId(int id){
        return trueAndFalseQuestionRepo.findTrueAndFalseQuestionbyQuestionId(id);
    }

    public AssessmentGrade saveAssessmentGrade(AssessmentGrade assessmentGrade){
        return assessmentGraderepo.save(assessmentGrade);
    }

    public StudentAssessmentResponse saveAssignment(StudentAssessmentResponse response){
        return studentAssessmentRepo.save(response);
    }
}
