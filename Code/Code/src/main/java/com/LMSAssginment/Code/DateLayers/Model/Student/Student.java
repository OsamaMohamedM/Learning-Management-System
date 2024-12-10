package com.LMSAssginment.Code.DateLayers.Model.Student;

import com.LMSAssginment.Code.DateLayers.Model.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Student extends User {
    private double gpa;

    public Student(int id, String name, String email, String gender, Date birthDate, double gpa) {
        super(id, name, email, gender, birthDate);
        this.gpa = gpa;
    }

    public Student() {
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentCourse> studentCourses;

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }
}
