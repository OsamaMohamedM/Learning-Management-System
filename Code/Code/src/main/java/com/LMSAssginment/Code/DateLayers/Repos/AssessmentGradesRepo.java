package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGradeCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentGradesRepo extends JpaRepository<AssessmentGrade, AssessmentGradeCompositeId> {

    @Query("SELECT a FROM AssessmentGrade a Join Assessment assess on " +
            "a.assessmentId = :assessmentId and assess.type = :assessmentType")
    List<AssessmentGrade> findAllGradesForSpecificAssessment(
            @Param("assessmentId") int assessmentId,
            @Param("assessmentType") String assessmentType
    );


    @Query("SELECT  ag FROM AssessmentGrade ag " +
            "JOIN Assessment a ON a.id = ag.assessmentId " +
            "WHERE ag.courseId = :courseId AND ag.studentID = :studentId  AND a.type = :AssessmentType")
    List<AssessmentGrade> findAllGradesForStudentInAssessments(
            @Param("studentId") int studentId,
            @Param("courseId") int courseId,
            @Param("AssessmentType") String AssessmentType
    );

}