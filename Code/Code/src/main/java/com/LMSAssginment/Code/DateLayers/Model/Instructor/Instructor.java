package com.LMSAssginment.Code.DateLayers.Model.Instructor;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Instructor.java
@Entity
public class Instructor extends User {
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Instructor() {
    }

    public Instructor(int id, String name, String email, String gender, Date birthDate) {
        super(id, name, email, gender, birthDate);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
