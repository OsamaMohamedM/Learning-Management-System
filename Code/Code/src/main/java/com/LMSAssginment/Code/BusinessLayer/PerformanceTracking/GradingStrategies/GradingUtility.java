package com.LMSAssginment.Code.BusinessLayer.PerformanceTracking.GradingStrategies;


import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;

import java.util.List;

public class GradingUtility {

    public static double GetGradesAverage(List<AssessmentGrade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (AssessmentGrade grade : grades) {
            sum += grade.getGrade();
        }

        return (double) sum / grades.size();
    }

    public static double FindMaxGrade(List<AssessmentGrade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (AssessmentGrade grade : grades) {
            if (grade.getGrade() > max) {
                max = grade.getGrade();
            }
        }

        return max;
    }


    public static double FindMinGrade(List<AssessmentGrade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (AssessmentGrade grade : grades) {
            if (grade.getGrade() < min) {
                min = grade.getGrade();
            }
        }

        return min;
    }
}
