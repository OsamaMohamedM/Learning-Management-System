package com.LMSAssginment.Code.DateLayers.Model.Course;

import jakarta.persistence.Embeddable;

import java.io.Serializable;



@Embeddable
public class AssessmentGradeCompositeId implements Serializable {
    private int studentID;
    private int assessmentId;
    private int courseId;

    public AssessmentGradeCompositeId() {

    }

    public AssessmentGradeCompositeId(int studentID, int assessmentId, int courseId) {
        this.studentID = studentID;
        this.assessmentId = assessmentId;
        this.courseId = courseId;
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
}
