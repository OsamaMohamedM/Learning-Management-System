package com.LMSAssginment.Code.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.Services.QuestionService;
import com.LMSAssginment.Code.Services.StudentAssessmentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{student_id}/{course_id}/{assessment_id}")
public class StudentController {
    @Autowired
    private StudentAssessmentResponseService studentAssessmentResponceService;

    Assessment assessment;
    List<String> questionsAnswer = new ArrayList<>();
    List<String> questionsAnswerReason = new ArrayList<>();



    @GetMapping("/displayAssessment")
    public List<String> displayAssessment(@PathVariable int assessment_id, @PathVariable int course_id){
        List<String> result = new ArrayList<>();
        assessment = studentAssessmentResponceService.displayAssessment(assessment_id, course_id);
        result.add(assessment.getType().toString() + "#" + assessment.getId());
        result.add("Start date " + assessment.getStartDate().toString());
        result.add("Duration " + assessment.getDuration().toString());
        result.add("Total grades " + assessment.getTotalGrades());
        int grades = assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions();
        result.add("Total number of question is " + assessment.getTotalGrades() + " so each question will have about " + grades + " grade and bones = " + (assessment.getTotalGrades() - (grades * assessment.getTotalNumberOfQuestions())) + " grade");
        int counter = 1;
        for (Question q : assessment.getQuestions()){
            result.add(counter + ") " + q.getText());
            if (q.getQuestionType().equals("mcq")){
                McqQuestion mcqQuestion = studentAssessmentResponceService.getMcqQuestionbyQuestionId(q.getId());
                result.add("a) " + mcqQuestion.getOptionA());
                result.add("b) " + mcqQuestion.getOptionB());
                result.add("c) " + mcqQuestion.getOptionC());
                result.add("d) " + mcqQuestion.getOptionD());
                questionsAnswer.add(mcqQuestion.getAnswer().toString().charAt(0) + "");
                questionsAnswerReason.add(mcqQuestion.getAnswer().toString());
            }
            else if (q.getQuestionType().equals("tf")){
                TrueAndFalseQuestion trueAndFalseQuestion = studentAssessmentResponceService.getTrueAndFalseQuestionbyQuestionId(q.getId());
                questionsAnswer.add(trueAndFalseQuestion.getAnswer() ? "True" : "False");
                questionsAnswerReason.add(trueAndFalseQuestion.getAnswer() ? "True" : "False");
                result.add("1) True");
                result.add("2) False");
            }
            else{
                ShortAnswerQuestion shortAnswerQuestion = studentAssessmentResponceService.getShortAnswerQuestionbyQuestionId(q.getId());
                questionsAnswer.add(shortAnswerQuestion.getAnswer());
                questionsAnswerReason.add(shortAnswerQuestion.getAnswer());
                result.add("........................................");
            }

            counter++;
        }
        return result;

    }

    @PostMapping("/displayAssessment/submit")
    public String submit(@PathVariable int student_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestBody Map<String, Object> studentAnswers){
        int count = 1, totalNumberOfGrades = 0;
        if (assessment.getType().equals("quiz")){
            String questionNumber = "q" + count;
            for (String str : questionsAnswer){
                if (str.equals(studentAnswers.get(questionNumber)))
                    totalNumberOfGrades += (assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions());
                count++;
            }
        }
        else
    }

}
