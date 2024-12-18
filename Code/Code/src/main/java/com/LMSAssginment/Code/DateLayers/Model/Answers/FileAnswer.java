package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

import java.io.File;

@Entity
public class FileAnswer extends Answer {
    private File answer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    public FileAnswer() {

    }

    public FileAnswer(File fileAnswer){
        this.answer = fileAnswer;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void display() {

    }

    public File getAnswer() {
        return answer;
    }

    public void setAnswer(File answer) {
        this.answer = answer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
