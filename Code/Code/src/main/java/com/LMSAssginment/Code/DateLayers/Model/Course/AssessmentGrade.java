package com.LMSAssginment.Code.DateLayers.Model.Course;

import com.LMSAssginment.Code.Services.AssessmentGradeKey;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AssessmentGrades")
@IdClass(AssessmentGradeCompositeId.class)
public class AssessmentGrade {
    @Id
    @Column(name = "student_id", nullable = false)
    private int studentID;
    @Id
    @Column(name = "assessment_id", nullable = false)
    private int assessmentId;
    @Id
    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "grade")
    private int grade;
    @Column(name = "feedBack")
    private String feedBack;

    public AssessmentGrade(){

    }

    public AssessmentGrade(int studentID, int assessmentId, int courseId, int grade){
        this.studentID = studentID;
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.grade = grade;
    }


    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
