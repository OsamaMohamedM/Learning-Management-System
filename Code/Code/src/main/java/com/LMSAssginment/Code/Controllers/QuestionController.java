package com.LMSAssginment.Code.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.Services.QuestionService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("{course_id}/createQuestions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

//    @PostMapping("/createQuestions")
//    public String addMcqQuestions(@RequestBody String mcqQuestions){
////        return questionService.addMcqQuestions(mcqQuestions);
//        return "cfvfv";
//    }

    @PostMapping("/mcq")
    public Course addMcqQuestions(@PathVariable int course_id, @RequestBody List<Map<String, Object>> mcqQuestions){
        Course course = questionService.getCourseById(course_id);
        for (Map<String, Object> ob : mcqQuestions){
            McqQuestion mcqQuestion = new McqQuestion(course,
                    (String) ob.get("text"),
                    (String) ob.get("optionA"),
                    (String) ob.get("optionB"),
                    (String) ob.get("optionC"),
                    (String) ob.get("optionD"),
                    (String) ob.get("answer"));
            questionService.addMcqQuestions(mcqQuestion);
        }
        return  course;
    }

    @PostMapping("/sa")
    public Course addShortAnswerQuestions(@PathVariable int course_id, @RequestBody List<Map<String, Object>> shortAnswerQuestions){
        Course course = questionService.getCourseById(course_id);
        for (Map<String, Object> ob : shortAnswerQuestions){
           ShortAnswerQuestion shortAnswerQuestion = new ShortAnswerQuestion(course,
                   (String) ob.get("text"),
                    (String) ob.get("answer"));
            questionService.addShortAnswerQuestion(shortAnswerQuestion);
        }
        return  course;
    }

    @PostMapping("/tf")
    public Course addTrueAndFalseQuestion (@PathVariable int course_id, @RequestBody List<Map<String, Object>> trueAndFalseQuestions){
        Course course = questionService.getCourseById(course_id);
        for (Map<String, Object> ob : trueAndFalseQuestions){
            TrueAndFalseQuestion trueAndFalseQuestion = new TrueAndFalseQuestion(course,
                    (String) ob.get("text"),
                    (Boolean) ob.get("answer"));
            questionService.addTrueAndFalseQuestion(trueAndFalseQuestion);
        }
        return  course;
    }

//    public Question addQuestion(@RequestBody Question){
//        return
//    }
}
