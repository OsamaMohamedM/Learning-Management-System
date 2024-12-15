package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Repos.AssessmentRepository;
import com.LMSAssginment.Code.DateLayers.Repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServices {
    @Autowired
    private AssessmentRepository assessmentRepository;


    public Assessment createAssessment(Assessment assessment){
        return assessmentRepository.save(assessment);
    }

}
