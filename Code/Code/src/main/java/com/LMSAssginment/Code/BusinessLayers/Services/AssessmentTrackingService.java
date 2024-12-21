package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.GradesTrackingStrategy.AssessmentTypeValidator;
import com.LMSAssginment.Code.GradesTrackingStrategy.StatisticsGenerationStrategy;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.AssessmentGradesRepo;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AssessmentTrackingService {
    @Autowired
    private AssessmentGradesRepo assessmentGradesRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private InstructorCourseRepo instructorCourseRepo;

    @Autowired
    private StatisticsGenerationStrategy statisticsGenerationStrategy;


    public Map<String, Object> GetAssessmentStatitics(String assessmentType, int assessmentId) throws Exception {

        if(!AssessmentTypeValidator.IsValidAssessmentType(assessmentType)) {
            throw new Exception("Invalid Assessment Type");
        }

        List<AssessmentGrade> allQuizGrades =
                assessmentGradesRepo.findAllGradesForSpecificAssessment(assessmentId, assessmentType);
        return statisticsGenerationStrategy.GenerateStatistics(allQuizGrades);
    }

    public Map<String, Object> GetStudentGradesInCourseAssessment(String assessmentType, int courseId, int studentId) throws Exception {
        Optional<User> student = userRepo.findById(studentId);
        Optional<Course> course = instructorCourseRepo.findById(courseId);
        if(student.isEmpty()) {
            throw new Exception("STUDENT IS NOT FOUND");
        }
        if(course.isEmpty()) {
            throw new Exception("COURSE IS NOT FOUND");
        }
        if(!AssessmentTypeValidator.IsValidAssessmentType(assessmentType.toLowerCase())) {
            throw new Exception("Invalid Assessment Type");
        }
        List<AssessmentGrade> allStudentGrades =
                assessmentGradesRepo.findAllGradesForStudentInAssessments(studentId, courseId, assessmentType);
        return statisticsGenerationStrategy.GenerateStatistics(allStudentGrades);
    }

}
