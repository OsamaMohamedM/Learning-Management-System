package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

@Entity
public class McqAnswer extends Answer {
    private String answer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void display() {
        // Implementation
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
