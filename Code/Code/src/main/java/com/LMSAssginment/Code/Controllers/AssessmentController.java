package com.LMSAssginment.Code.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.Services.AssessmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentController {
    @Autowired
    private AssessmentServices assessmentServices;


    @PostMapping("/createAssessment")
    public Assessment createAssessment(@RequestBody Assessment assessment){
        return assessmentServices.createAssessment(assessment);
    }
}
