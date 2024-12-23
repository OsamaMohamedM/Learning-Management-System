package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorCourseRepo extends JpaRepository<Course,Integer> {

    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    Course getCoursesByInstructorId(@Param("instructorId") int instructorId);


    @Query("delete from StudentCourse sc where sc.course.id = :courseId and sc.student.id = :studentId")
    void deleteStudent(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND EXISTS (SELECT c FROM Course c WHERE c.instructor.id = :userId AND c.id = :courseId)")
    User getUserIfInstructorOfCourse(@Param("userId") int userId, @Param("courseId") int courseId);




}
