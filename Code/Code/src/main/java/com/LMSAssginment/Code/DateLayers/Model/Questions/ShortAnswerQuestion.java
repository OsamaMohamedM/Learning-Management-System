package com.LMSAssginment.Code.DateLayers.Model.Questions;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class ShortAnswerQuestion extends Question {
    private String answer;

    public ShortAnswerQuestion(){

    }

    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public ShortAnswerQuestion(Course course, String text, String answer) {
        super( text, course);
        this.answer = answer;
    }

    // Getters and Setters
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
