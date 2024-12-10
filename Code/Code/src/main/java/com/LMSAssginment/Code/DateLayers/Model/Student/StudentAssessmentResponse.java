package com.LMSAssginment.Code.DateLayers.Model.Student;

import java.util.List;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import jakarta.persistence.*;

@Entity
@Table(name = "StudentAssessmentResponse")
public class StudentAssessmentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @JoinColumn(name = "student_id", nullable = false)
    private int studentId;
    @JoinColumn(name = "course_id", nullable = false)
    private int courseId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "response_id")
    private List<FileAnswer> studentAnswer;

    public StudentAssessmentResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<FileAnswer> getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(List<FileAnswer> studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
