package com.LMSAssginment.Code.DateLayers.Model.Course;

public class CourseDTO {
    private int id;
    private String name;
    private int maxNumberOfStudent;
    private String description;
    private String instructorName;  

    // Constructor, getters, setters
    public CourseDTO(int id, String name, int maxNumberOfStudent, String description, String instructorName) {
        this.id = id;
        this.name = name;
        this.maxNumberOfStudent = maxNumberOfStudent;
        this.description = description;
        this.instructorName = instructorName;
    }

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

    public int getMaxNumberOfStudent() {
        return maxNumberOfStudent;
    }

    public void setMaxNumberOfStudent(int maxNumberOfStudent) {
        this.maxNumberOfStudent = maxNumberOfStudent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorName() {
        return instructorName;
    }

}
