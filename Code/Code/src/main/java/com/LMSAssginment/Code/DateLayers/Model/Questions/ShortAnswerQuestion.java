package com.LMSAssginment.Code.DateLayers.Model.Questions;

public class ShortAnswerQuestion extends Question {
    private String answer;

    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public ShortAnswerQuestion(int id, String text, String answer) {
        super(id, text, null); // Null here as it will be set by the parent (Assessment)
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
