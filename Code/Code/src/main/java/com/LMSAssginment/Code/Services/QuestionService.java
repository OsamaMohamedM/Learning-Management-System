package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepo questionsRepo;

    public List<Question> addQuestions(List<Question> questions){
        return questionsRepo.saveAll(questions);
    }
}
