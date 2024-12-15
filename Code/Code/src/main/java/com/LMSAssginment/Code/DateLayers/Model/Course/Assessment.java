package com.LMSAssginment.Code.DateLayers.Model.Course;

import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private int totalGrades;
    private Double duration;
    private Date startDate;
    private String type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany
    @JoinTable(
            name = "assessment_question",
            joinColumns = @JoinColumn(name = "assessment_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    public Assessment() {}

    public Assessment( int totalGrades, Double duration, Date startDate, String type, Course course, List<Question> questions) {
        this.totalGrades = totalGrades;
        this.duration = duration;
        this.startDate = startDate;
        this.type = type;
        this.course = course;
        this.questions = questions;
    }
 public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalGrades() {
        return totalGrades;
    }

    public void setTotalGrades(int totalGrades) {
        this.totalGrades = totalGrades;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public  int getNumberOfQuestions()
    {
       return  questions.size();
    }
}
