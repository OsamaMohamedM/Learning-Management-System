package com.LMSAssginment.Code.Services;

import ch.qos.logback.core.util.StringUtil;
import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    public McqQuestion getMcqQuestionbyQuestionId(int id) {
        return mcqQuestionRepo.findMcqQuestionbyQuestionId(id);
    }

    public ShortAnswerQuestion getShortAnswerQuestionbyQuestionId(int id) {
        return shortAnswerQuestionRepo.findShortAnswerQuestionbyQuestionId(id);
    }

    public TrueAndFalseQuestion getTrueAndFalseQuestionbyQuestionId(int id) {
        return trueAndFalseQuestionRepo.findTrueAndFalseQuestionbyQuestionId(id);
    }

    public AssessmentGrade saveAssessmentGrade(AssessmentGrade assessmentGrade) {
        return assessmentGraderepo.save(assessmentGrade);
    }

    public void saveAssignment(MultipartFile multipartFile, Course course, int user_id, int assessment_id, int course_id, Assessment assessment) throws Exception {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        FileAnswer fileAnswer = new FileAnswer(fileName, multipartFile.getContentType(), multipartFile.getBytes(), course);
        AssessmentGrade assessmentGrade = new AssessmentGrade(user_id, assessment_id, course_id, 0);
        List<FileAnswer> fileAnswers = new ArrayList<>();
        fileAnswers.add(fileAnswer);
        StudentAssessmentResponse studentAssessmentResponse = new StudentAssessmentResponse(assessment, user_id, course_id, fileAnswers);
        studentAssessmentRepo.save(studentAssessmentResponse);
        assessmentGraderepo.save(assessmentGrade);

    }

    public List<String> getQuestionAnswer(Assessment assessment){
        List<String> questionsAnswer = new ArrayList<>();
        for (Question q : assessment.getQuestions()){
            if (q.getQuestionType().equals("mcq")){
                McqQuestion mcqQuestion = getMcqQuestionbyQuestionId(q.getId());
                questionsAnswer.add(mcqQuestion.getAnswer().toString().charAt(0) + "");
            }
            else if (q.getQuestionType().equals("tf")){
                TrueAndFalseQuestion trueAndFalseQuestion = getTrueAndFalseQuestionbyQuestionId(q.getId());
                questionsAnswer.add(trueAndFalseQuestion.getAnswer() ? "true" : "false");
            }
            else{
                ShortAnswerQuestion shortAnswerQuestion = getShortAnswerQuestionbyQuestionId(q.getId());
                questionsAnswer.add(shortAnswerQuestion.getAnswer());
            }
       }
        return questionsAnswer;
    }
}
