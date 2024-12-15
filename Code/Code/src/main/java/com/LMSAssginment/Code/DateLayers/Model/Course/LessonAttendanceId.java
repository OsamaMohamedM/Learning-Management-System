package com.LMSAssginment.Code.DateLayers.Model.Course;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LessonAttendanceId implements Serializable {

    private Long courseId;
    private Long lessonId;
    private Long studentId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonAttendanceId that = (LessonAttendanceId) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(lessonId, that.lessonId) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, lessonId, studentId);
    }
}