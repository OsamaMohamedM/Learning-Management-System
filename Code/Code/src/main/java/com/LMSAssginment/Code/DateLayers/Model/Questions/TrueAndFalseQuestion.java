package com.LMSAssginment.Code.DateLayers.Model.Questions;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
public class TrueAndFalseQuestion extends Question {
    private boolean answer;

    public TrueAndFalseQuestion(){

    }
    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public TrueAndFalseQuestion(Course course, String text, boolean answer, String questionType) {
        super(text, course, questionType);
        this.answer = answer;
    }

    // Getters and Setters
    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
