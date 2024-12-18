package com.LMSAssginment.Code.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import com.LMSAssginment.Code.Services.QuestionService;
import com.LMSAssginment.Code.Services.StudentAssessmentResponseService;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{user_id}/{course_id}/{assessment_id}")
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

    @PostMapping("/displayAssessment/submitQuiz")
    public String submitQuiz(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestBody Map<String, Object> studentAnswers){
        String result;
        int count = 1, totalNumberOfGrades = 0, correct = 0;
                /*

        {
        "q1":...
        "q2":...
        }

         */
        AssessmentGrade assessmentGrade;
            for (String str : questionsAnswer){
                String questionNumber = "q" + count;
                if (str.equals(studentAnswers.get(questionNumber))){
                    totalNumberOfGrades += (assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions());
                    correct++;
                }
                count++;
            }
            assessmentGrade = new AssessmentGrade(user_id, assessment_id, course_id, totalNumberOfGrades);
            result = "Your correct questions is: " + correct + ", Your grades is: " + totalNumberOfGrades;
            studentAssessmentResponceService.saveAssessmentGrade(assessmentGrade);

        return result;
    }


//    @PostMapping("/displayAssessment/submitAssignment")
//    public String submitAssignment(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestParam MultipartFile file){
//        String fileName = file.getOriginalFilename();
//        byte[] fileData = file.getBytes();
//        FileAnswer fileAnswer = new FileAnswer(file_name, fileData);
//        String result;
//        AssessmentGrade assessmentGrade;
//        assessmentGrade = new AssessmentGrade(user_id, assessment_id, course_id, 0);
//        result = "Your Assignment has been uploaded succeffuly";
//        List<FileAnswer> fileAnswers = new ArrayList<>();
//        fileAnswers.add(fileAnswer);
//        StudentAssessmentResponse studentAssessmentResponse = new StudentAssessmentResponse(assessment, user_id, course_id, fileAnswers);
//        studentAssessmentResponceService.saveAssignment(studentAssessmentResponse);
//
//        return "result";
//    }

}
