package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import com.LMSAssginment.Code.DateLayers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Long> {
        List<StudentCourse> findByStudentId(Long studentId);
        @Query("SELECT c FROM Course c")
        List<Course> findAllCourses();



        // kick some a$$ with subqueries :D
        @Query(value = "SELECT u FROM User u WHERE u.id IN (SELECT sc.student.id FROM StudentCourse sc WHERE sc.course.id = :course_id)")
        List<User> findAllEnrolledUsers(@Param("course_id") Integer course_id);

        @Query(value = "SELECT u FROM User u WHERE u.id = :user_ID AND u.id IN (SELECT sc.student.id FROM StudentCourse sc WHERE sc.course.id = :course_id)")
        User findSpecificEnrolledUser(@Param("course_id") Integer course_id, @Param("user_ID") Integer user_ID);



}
