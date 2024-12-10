package com.LMSAssginment.Code.DateLayers.Model.Questions;

public class TrueAndFalseQuestion extends Question {
    private boolean answer;

    @Override
    public void display() {
        // Implementation for displaying the question
    }

    // Constructors, getters, and setters
    public TrueAndFalseQuestion(int id, String text, boolean answer) {
        super(id, text, null); // Null here as it will be set by the parent (Assessment)
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
