package com.LMSAssginment.Code.DateLayers.Model.Course;

import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable


@Entity
public class LessonAttendance {

    @EmbeddedId
    private LessonAttendanceId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    public LessonAttendanceId getId() {
        return id;
    }

    public void setId(LessonAttendanceId id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
