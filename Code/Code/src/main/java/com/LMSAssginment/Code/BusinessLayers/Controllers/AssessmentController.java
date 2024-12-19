package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.BusinessLayers.Services.AssessmentServices;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/{course_id}")
public class AssessmentController {
    @Autowired
    private AssessmentServices assessmentServices;
    @Autowired
    private QuestionsRepo questionsRepo;


    @PostMapping("/createAssessment")
    public Assessment createAssessment(@PathVariable int course_id, @RequestBody Map<String, Object> assessment) {
        Course course = assessmentServices.getCourseById(course_id);
        return assessmentServices.createAssessment(course, assessment);
    }
}