package com.LMSAssginment.Code.DateLayers.Model.Questions;

public class TrueAndFalseQuestion extends Question {
    private boolean answer;

    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public TrueAndFalseQuestion(int id, String text, boolean answer) {
        super(text, null);
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
