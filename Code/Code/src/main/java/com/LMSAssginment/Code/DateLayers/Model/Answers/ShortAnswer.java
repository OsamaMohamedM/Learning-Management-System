package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

@Entity
public class ShortAnswer extends Answer {
    private String answer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public ShortAnswer(String answer, Course course) {
        this.answer = answer;
        this.course = course;
    }

    public ShortAnswer() {
    }

    @Override
    public void display() {
        System.out.println("Short Answer: " + answer);
    }

    @Override
    public boolean validate() {
        return answer != null && !answer.trim().isEmpty();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
