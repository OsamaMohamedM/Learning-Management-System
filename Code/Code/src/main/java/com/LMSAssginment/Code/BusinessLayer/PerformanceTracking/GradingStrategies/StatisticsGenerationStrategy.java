package com.LMSAssginment.Code.BusinessLayer.PerformanceTracking.GradingStrategies;

import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;

import java.util.List;
import java.util.Map;

public interface StatisticsGenerationStrategy {

    Map<String, Object> GenerateStatistics(List<AssessmentGrade> grades);
}