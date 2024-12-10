package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

@Entity
public abstract class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public abstract boolean validate();

    public abstract void display();
}
