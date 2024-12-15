package com.LMSAssginment.Code.DateLayers.Model.Student;

import java.util.Date;

public class StudentDTO {

    private int id;
    private String name;
    private String email;
    private String gender;
    private Date birthDate;
    private double gpa;

    // Constructor
    public StudentDTO(int id, String name, String email, String gender, Date birthDate, double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.gpa = gpa;
    }

    // Default Constructor
    public StudentDTO() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
