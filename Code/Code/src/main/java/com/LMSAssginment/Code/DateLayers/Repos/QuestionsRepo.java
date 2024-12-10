package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepo extends JpaRepository<Question,Integer>{

}
