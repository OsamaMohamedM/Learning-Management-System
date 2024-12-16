package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortAnswerQuestionRepo  extends JpaRepository<ShortAnswerQuestion,Integer> {
}
