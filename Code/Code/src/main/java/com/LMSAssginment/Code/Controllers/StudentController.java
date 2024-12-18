package com.LMSAssginment.Code.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import com.LMSAssginment.Code.Services.CourseService;
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

    @Autowired
    private CourseService courseService;







    @GetMapping("/displayAssessment")
    public List<String> displayAssessment(@PathVariable int assessment_id, @PathVariable int course_id){
        Assessment assessment =  studentAssessmentResponceService.displayAssessment(assessment_id, course_id);
        List<String> result = new ArrayList<>();
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
            }
            else if (q.getQuestionType().equals("tf")){
                result.add("1) True");
                result.add("2) False");
            }
            else{
                result.add("........................................");
            }

            counter++;
        }
        return result;

    }

    @PostMapping("/displayAssessment/submitQuiz")
    public String submitQuiz(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestBody Map<String, String> studentAnswers){
        Assessment assessment =  studentAssessmentResponceService.displayAssessment(assessment_id, course_id);
        List<String> questionsAnswer = studentAssessmentResponceService.getQuestionAnswer(assessment);
        String result;
        int count = 1, totalNumberOfGrades = 0, correct = 0;
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


    @PostMapping("/displayAssessment/submitAssignment")
    public String submitAssignment(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestParam("file") MultipartFile file){
        Course course = courseService.getCourseById(course_id);
        try {
            Assessment assessment =  studentAssessmentResponceService.displayAssessment(assessment_id, course_id);
            studentAssessmentResponceService.saveAssignment(file, course,user_id, assessment_id, course_id, assessment);
        }catch (Exception e){
            System.out.println("Error while saving file " + e.getMessage());
        }
        return "Your Assignment has been uploaded successfully";
    }

}
