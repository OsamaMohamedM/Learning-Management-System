package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Repos.QuestionsRepo;
import com.LMSAssginment.Code.BusinessLayers.Services.AssessmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{course_id}")
public class AssessmentController {
    @Autowired
    private AssessmentServices assessmentServices;
    @Autowired
    private QuestionsRepo questionsRepo;


    @PostMapping("/createAssessment")
    public String createAssessment(@PathVariable int course_id, @RequestBody Map<String, Object> assessment) {
        Course course = assessmentServices.getCourseById(course_id);
        //  int totalGrades, Double duration, Date startDate, String type,
        //   course, List<Question> questions , int totalNumberOfQuestions
        // he will give us a list of question ids , so I will turn each to a question object
        Boolean random = (Boolean) assessment.get("random");
        int numberOfQuestion =  (int) assessment.get("totalNumberOfQuestions");
        List<Question> pass = new ArrayList<>();
        if (!random){
            List<Integer> tmp = (List<Integer>) assessment.get("questions");
            for (int id : tmp) {
                Question current = assessmentServices.getQuestionById(id);
                pass.add(current);
            }
        }
        else{
            int mcqQuestion = numberOfQuestion / 2;
            int shortAnswerQuestion = numberOfQuestion / 4;
            int trueAndFalseQuestion = numberOfQuestion - (mcqQuestion + shortAnswerQuestion);

           List<Question> mcqQuestions = assessmentServices.getRandomQuestions("mcq", mcqQuestion);
            pass.addAll(mcqQuestions);
            List<Question> trueAndFalse = assessmentServices.getRandomQuestions("sa", trueAndFalseQuestion);
            pass.addAll(trueAndFalse);
            List<Question> shortAnswer = assessmentServices.getRandomQuestions("tf", shortAnswerQuestion);
            pass.addAll(shortAnswer);

        }
        try {
            String sth = (String) assessment.get("startDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date passs = sdf.parse(sth);
            System.out.println("Parsed Date: " + passs);

            Assessment assessment1 = new Assessment(
                    (int) assessment.get("totalGrades"),
                    (double) assessment.get("duration"),
                    passs,
                    (String) assessment.get("type"),
                    course,
                    pass,
                    numberOfQuestion
            );
            assessmentServices.createAssessment(assessment1);
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Error casting the value to String: " + e.getMessage());
        }


        return "hello world";
    }
}
