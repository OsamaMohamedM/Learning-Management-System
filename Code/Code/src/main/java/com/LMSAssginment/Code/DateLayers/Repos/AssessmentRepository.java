package com.LMSAssginment.Code.DateLayers.Repos;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,Integer>{

}
