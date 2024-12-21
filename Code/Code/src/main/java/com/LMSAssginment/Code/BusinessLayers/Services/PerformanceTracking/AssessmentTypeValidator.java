package com.LMSAssginment.Code.BusinessLayers.Services.PerformanceTracking;

import java.util.HashSet;
import java.util.Set;

public class AssessmentTypeValidator {
    private static final Set<String> assessmentTypes = new HashSet<String>();

    static {
        assessmentTypes.add("quiz");
        assessmentTypes.add("assignment");
    }

    public static boolean IsValidAssessmentType(String assessmentType) {
        return assessmentTypes.contains(assessmentType);
    }

}
