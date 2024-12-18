package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrueAndFalseQuestionRepo  extends JpaRepository<TrueAndFalseQuestion, Integer> {

    @Query(value = "SELECT a FROM TrueAndFalseAnswer a WHERE a.id = :q_id")
    TrueAndFalseQuestion findTrueAndFalseQuestionbyQuestionId(@Param("q_id") int q_id);
}
