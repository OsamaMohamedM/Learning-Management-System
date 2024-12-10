package com.LMSAssginment.Code.DateLayers.Model.Questions;


import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShortAnswerQuestion.class, name = "short-answer"),
        @JsonSubTypes.Type(value = TrueAndFalseQuestion.class, name = "true-false"),
        @JsonSubTypes.Type(value = McqQuestion.class, name = "mcq"),

})

public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;


    public Question() {

    }

    public Question(int id, String text, Course course) {
        this.id = id;
        this.text = text;
        this.course = course;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public abstract void display();
}
