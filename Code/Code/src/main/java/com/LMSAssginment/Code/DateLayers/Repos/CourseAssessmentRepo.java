package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseAssessmentRepo extends JpaRepository<Assessment,Integer> {


}
