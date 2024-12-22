package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
import com.LMSAssginment.Code.DateLayers.Model.Course.LessonAttendance;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class attendTest {
    @Autowired
    private LessonAttendanceRepository lessonAttendanceRepository;
    @Autowired
    private InstructorCourseRepo courseRepository;
    @Autowired
    private LessonRepo lessonRepository;
    @Autowired
    private UserRepo studentRepository;
    @Autowired
    private StudentCourseRepo studentCourseRepo;
    @Autowired
    private attendService attendService;
    @BeforeEach
    void setUp() {
        lessonAttendanceRepository = mock(LessonAttendanceRepository.class);
        courseRepository = mock(InstructorCourseRepo.class);
        lessonRepository = mock(LessonRepo.class);
        studentRepository = mock(UserRepo.class);
        studentCourseRepo = mock(StudentCourseRepo.class);
        attendService = new attendService(
                lessonAttendanceRepository,
                courseRepository,
                lessonRepository,
                studentRepository,
                studentCourseRepo
        );
    }

    @Test
    void testMarkAttendanceSuccess() {
        int courseId = 1;
        int lessonId = 1;
        int studentId = 1;
        int studentOtp = 1234;

        Course mockCourse = new Course();
        mockCourse.setId(courseId);

        Lesson mockLesson = new Lesson();
        mockLesson.setId(lessonId);
        mockLesson.setOtp(studentOtp);

        Student mockStudent = new Student();
        mockStudent.setId(studentId);

        // Create StudentCourse object and set its fields explicitly
        StudentCourse mockStudentCourse = new StudentCourse();
        mockStudentCourse.setCourse(mockCourse);
        mockStudentCourse.setStudent(mockStudent);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(mockLesson));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        when(studentCourseRepo.findByStudentId((long) studentId))
                .thenReturn(List.of(mockStudentCourse));

        Map<String, Integer> response = attendService.markAttendance(courseId, lessonId, studentId, studentOtp);

        assertEquals(lessonId, response.get("lessonId"));
        assertEquals(studentId, response.get("studentId"));
        verify(lessonAttendanceRepository, times(1)).save(any(LessonAttendance.class));
    }
    @Test
    void testNotCorrestOTD() {
        int courseId = 1;
        int lessonId = 1;
        int studentId = 1;
        int studentOtp = 1234;

        Course mockCourse = new Course();
        mockCourse.setId(courseId);

        Lesson mockLesson = new Lesson();
        mockLesson.setId(lessonId);
        mockLesson.setOtp(4321);

        Student mockStudent = new Student();
        mockStudent.setId(studentId);

        // Create StudentCourse object and set its fields explicitly
        StudentCourse mockStudentCourse = new StudentCourse();
        mockStudentCourse.setCourse(mockCourse);
        mockStudentCourse.setStudent(mockStudent);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(mockLesson));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        when(studentCourseRepo.findByStudentId((long) studentId))
                .thenReturn(List.of(mockStudentCourse));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                attendService.markAttendance(courseId, lessonId, studentId, studentOtp));
        assertEquals("Invalid OTP", exception.getMessage());
    }
    @Test
    void testNotEnrolled(){
        int courseId = 1;
        int lessonId = 1;
        int studentId = 1;
        int studentId2 = 2;
        int studentOtp = 1234;

        Course mockCourse = new Course();
        mockCourse.setId(courseId);

        Lesson mockLesson = new Lesson();
        mockLesson.setId(lessonId);
        mockLesson.setOtp(studentOtp);

        Student mockStudent = new Student();
        mockStudent.setId(studentId);
        Student mockStudent2 = new Student();
        mockStudent2.setId(studentId2);

        // Create StudentCourse object and set its fields explicitly
        StudentCourse mockStudentCourse = new StudentCourse();
        mockStudentCourse.setCourse(mockCourse);
        mockStudentCourse.setStudent(mockStudent);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(mockLesson));
        when(studentRepository.findById(studentId2)).thenReturn(Optional.of(mockStudent2));
        when(studentCourseRepo.findByStudentId((long) studentId2))
                .thenReturn(List.of());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                attendService.markAttendance(courseId, lessonId, studentId2, studentOtp));
        assertEquals("Student is not enrolled in the course", exception.getMessage());

    }

    @Test
        void testMarkAttendanceInvalidCourse() {
            int courseId = 1;
            int lessonId = 1;
            int studentId = 1;
            int studentOtp = 1234;

            when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    attendService.markAttendance(courseId, lessonId, studentId, studentOtp));

            assertEquals("Invalid course, lesson, or student ID", exception.getMessage());
        }

}
