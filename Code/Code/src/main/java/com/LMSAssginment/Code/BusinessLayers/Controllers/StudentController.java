package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.BusinessLayers.Services.CourseService;
import com.LMSAssginment.Code.BusinessLayers.Services.StudentAssessmentResponseService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return studentAssessmentResponceService.showAssessment(assessment_id, course_id);
    }

    @PostMapping("/displayAssessment/submitQuiz")
    public String submitQuiz(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestBody Map<String, String> studentAnswers){
        return studentAssessmentResponceService.submitQuiz(user_id, assessment_id, course_id, studentAnswers);
    }


    @PostMapping("/displayAssessment/submitAssignment")
    public String submitAssignment(@PathVariable int user_id, @PathVariable int assessment_id, @PathVariable int course_id, @RequestParam("file") MultipartFile file){
        Course course = courseService.getCourseById(course_id);
        try {
            Assessment assessment =  studentAssessmentResponceService.getAssessment(assessment_id, course_id);
            studentAssessmentResponceService.saveAssignment(file, course,user_id, assessment_id, course_id, assessment);
        }catch (Exception e){
            System.out.println("Error while saving file " + e.getMessage());
        }
        return "Your Assignment has been uploaded successfully";
    }


    @Transactional
    @PostMapping("displayAssessment/giveFeedback")
    public AssessmentGrade giveFeedback(@PathVariable int assessment_id, @PathVariable int course_id, @RequestBody Map<String, String> instructorFeedback){
        return studentAssessmentResponceService.giveManualFeedback(assessment_id, course_id, instructorFeedback);
    }

}
