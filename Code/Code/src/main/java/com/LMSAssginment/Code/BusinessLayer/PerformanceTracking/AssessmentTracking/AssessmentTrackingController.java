package com.LMSAssginment.Code.BusinessLayer.PerformanceTracking.AssessmentTracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performanceTracking/assessment")
public class AssessmentTrackingController {

    @Autowired
    private AssessmentTrackingService assessmentTrackingService;

    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping("/{assessmentType}/{assessmentId}")
    public ResponseEntity<?> GetStatisticsForSpecificAssessment(@PathVariable("assessmentType") String assessmentType, @PathVariable("assessmentId") int assessmentId) {
        try{
            var response = assessmentTrackingService.GetAssessmentStatitics(assessmentType, assessmentId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/{assessmentType}/{courseId}/{studentId}")
    public ResponseEntity<?> GetStudentAssessmentInfo(
            @PathVariable("assessmentType") String assessmentType,
            @PathVariable("courseId") int courseId,
            @PathVariable("studentId") int studentId) {

        try {
            var response = assessmentTrackingService.GetStudentGradesInCourseAssessment(assessmentType, courseId, studentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
