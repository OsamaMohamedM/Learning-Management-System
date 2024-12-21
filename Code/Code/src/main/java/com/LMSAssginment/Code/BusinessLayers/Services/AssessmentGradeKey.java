package com.LMSAssginment.Code.Services;

import java.io.Serializable;
import java.util.Objects;

public class AssessmentGradeKey implements Serializable {
    private int studentID;
    private int assessmentId;
    private int courseId;

    public AssessmentGradeKey() {}

    public AssessmentGradeKey(int studentID, int assessmentId, int courseId) {
        this.studentID = studentID;
        this.assessmentId = assessmentId;
        this.courseId = courseId;
    }

    // Getters, Setters, equals(), and hashCode()
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentGradeKey that = (AssessmentGradeKey) o;
        return studentID == that.studentID && assessmentId == that.assessmentId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, assessmentId, courseId);
    }
}