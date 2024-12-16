package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrueAndFalseQuestionRepo  extends JpaRepository<TrueAndFalseQuestion, Integer> {
}
