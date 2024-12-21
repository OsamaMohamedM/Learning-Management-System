package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShortAnswerQuestionRepo  extends JpaRepository<ShortAnswerQuestion,Integer> {

    @Query(value = "SELECT a FROM ShortAnswerQuestion a WHERE a.id = :q_id")
    ShortAnswerQuestion findShortAnswerQuestionbyQuestionId(@Param("q_id") int q_id);
}
