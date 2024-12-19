package com.LMSAssginment.Code.DateLayers.Model;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;

public class AssessmentWithGradeDTO {

    private AssessmentGrade assessmentGrade;
    private Assessment assessment;

    public AssessmentWithGradeDTO(AssessmentGrade assessmentGrade, Assessment assessment) {
        this.assessmentGrade = assessmentGrade;
        this.assessment = assessment;
    }

    public AssessmentGrade getAssessmentGrade() {
        return assessmentGrade;
    }

    public Assessment getAssessment() {
        return assessment;
    }
}
