package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface StudentAssessmentRepo extends JpaRepository<StudentAssessmentResponse, Integer> {

    @Query("SELECT a FROM Assessment a WHERE a.id = :id and a.course.id = :course_id")
    Assessment findAssessmentById(@Param("id") int id, @Param("course_id") int course_id);

}
