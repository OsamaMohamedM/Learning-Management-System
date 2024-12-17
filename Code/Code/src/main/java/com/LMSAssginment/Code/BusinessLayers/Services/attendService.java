package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
import com.LMSAssginment.Code.DateLayers.Model.Course.LessonAttendance;
import com.LMSAssginment.Code.DateLayers.Model.Course.LessonAttendanceId;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class attendService {

    private final LessonAttendanceRepository lessonAttendanceRepository;
    private final InstructorCourseRepo courseRepository;
    private final LessonRepo lessonRepository;
    private final UserRepo studentRepository;
    private final StudentCourseRepo StudentCourseRepo;

    public attendService(
            LessonAttendanceRepository lessonAttendanceRepository,
            InstructorCourseRepo courseRepository,
            LessonRepo lessonRepository,
            UserRepo studentRepository,
            StudentCourseRepo StudentCourseRepo
    ) {
        this.lessonAttendanceRepository = lessonAttendanceRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.StudentCourseRepo = StudentCourseRepo;
    }

    public Map<String, Integer> markAttendance(int courseId, int lessonId, int studentId, int studentOtp) {
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        Optional<User> student = studentRepository.findById(studentId);

        if (course.isEmpty() || lesson.isEmpty() || student.isEmpty()) {
            throw new IllegalArgumentException("Invalid course, lesson, or student ID");
        }

        boolean isStudentEnrolled = StudentCourseRepo.findByStudentId((long) studentId)
                .stream()
                .anyMatch(sc -> sc.getCourse().getId() == courseId);

        if (!isStudentEnrolled) {
            throw new IllegalArgumentException("Student is not enrolled in the course");
        }


        if (lesson.get().getOtp() != studentOtp) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        LessonAttendanceId attendanceId = new LessonAttendanceId();
        attendanceId.setLessonId(lessonId);
        attendanceId.setCourseId(courseId);
        attendanceId.setStudentId(studentId);
        LessonAttendance attendance = new LessonAttendance();
        attendance.setId(attendanceId);
        attendance.setCourse(course.get());
        attendance.setLesson(lesson.get());
        attendance.setStudent((Student) student.get());

        lessonAttendanceRepository.save(attendance);

        // Return only lessonId and studentId
        Map<String, Integer> response = new HashMap<>();
        response.put("lessonId", lessonId);
        response.put("studentId", studentId);
        return response;
    }

    public List<Student> getStudentsByLessonId(int lessonId) {
        // Retrieve all attendance records for the given lessonId
        List<LessonAttendance> attendanceList = lessonAttendanceRepository.findByLessonId(lessonId);

        // Extract the list of students who attended the lesson
        List<Student> students = new ArrayList<>();
        for (LessonAttendance attendance : attendanceList) {
            students.add(attendance.getStudent());
        }

        return students;
    }

}
