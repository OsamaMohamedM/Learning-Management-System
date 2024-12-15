package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.AssessmentWithGradeDTO;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGradeCompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentGradesRepo extends JpaRepository<AssessmentGrade, AssessmentGradeCompositeId> {



    @Query("SELECT  AssessmentWithGradeDTO(ag, a) FROM AssessmentGrade ag " +
            "JOIN Assessment a ON a.id = ag.assessmentId " +
            "WHERE a.id = :assessmentId")
    List<AssessmentWithGradeDTO> findAllGradesForSpecificAssessment(
            @Param("assessmentId") int assessmentId
    );


    @Query("SELECT  AssessmentWithGradeDTO(ag, a) FROM AssessmentGrade ag " +
            "JOIN Assessment a ON a.id = ag.assessmentId " +
            "WHERE ag.courseId = :courseId AND ag.studentID = :studentId  AND a.type = :AssessmentType")
    List<AssessmentWithGradeDTO> findAllGradesForStudentInCourse(
            @Param("studentId") int studentId,
            @Param("courseId") int courseId,
            @Param("AssessmentType") String AssessmentType
    );
}


