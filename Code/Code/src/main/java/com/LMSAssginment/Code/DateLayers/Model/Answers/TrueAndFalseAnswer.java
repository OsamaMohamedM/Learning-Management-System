package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

@Entity
public class TrueAndFalseAnswer extends Answer {
    private boolean answer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public void display() {
        System.out.println("True/False Answer: " + (answer ? "True" : "False"));
    }

    @Override
    public boolean validate() {
        return true;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
