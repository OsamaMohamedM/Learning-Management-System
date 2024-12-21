package com.LMSAssginment.Code.GradesTrackingStrategy;

import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NormalStatisticsStrategy implements StatisticsGenerationStrategy {

    @Override
    public Map<String, Object> GenerateStatistics(List<AssessmentGrade> grade) {
        Map<String, Object> quizStatitics = new HashMap<>();
        quizStatitics.put("Average of Grades: ", GradingUtility.GetGradesAverage(grade));
        quizStatitics.put("Min Grade: ", GradingUtility.FindMinGrade(grade));
        quizStatitics.put("Max Grade: ", GradingUtility.FindMaxGrade(grade));
        quizStatitics.put("All Grades: ", grade);

        return quizStatitics;
    }
}
