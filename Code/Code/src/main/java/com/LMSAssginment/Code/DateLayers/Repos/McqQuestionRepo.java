package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McqQuestionRepo extends JpaRepository<McqQuestion,Integer> {
}
