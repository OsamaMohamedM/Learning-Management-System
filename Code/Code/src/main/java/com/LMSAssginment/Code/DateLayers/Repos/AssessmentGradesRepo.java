package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGradeCompositeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT a FROM AssessmentGrade a WHERE a.studentID = :student_id and a.courseId = :course_id and a.assessmentId = :assessment_id")
    AssessmentGrade getAssessmentGrade(@Param("student_id") int student_id, @Param("course_id") int course_id, @Param("assessment_id") int assessment_id);

    @Modifying
    @Transactional
    @Query("UPDATE AssessmentGrade a SET a.grade = :grade, a.feedBack = :feed_back WHERE a.studentID = :student_id and a.courseId = :course_id and a.assessmentId = :assessment_id")
    int updateAssessmentGrade(@Param("student_id") int student_id, @Param("course_id") int course_id, @Param("assessment_id") int assessment_id, @Param("grade") int grade, @Param("feed_back") String feed_back);


}