package com.LMSAssginment.Code.DateLayers.Model.Questions;

public class ShortAnswerQuestion extends Question {
    private String answer;

    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public ShortAnswerQuestion( String text, String answer) {
        super( text, null);
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
