package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseProgressRepo extends JpaRepository<Assessment,Integer>{

    List<Assessment>getAllByCourse_Id(int id);

    @Query("select ag.grade from AssessmentGrade ag where ag.courseId = :id")
    List<Double> getAssessmentGradeByCourseId(@Param("id") int id);

}
