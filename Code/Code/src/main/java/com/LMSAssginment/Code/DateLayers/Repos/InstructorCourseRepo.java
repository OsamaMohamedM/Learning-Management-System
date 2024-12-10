package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorCourseRepo extends JpaRepository<Course,Integer> {

    List<Course>findCoursesByInstructor_Id(int id);

    @Query("delete from StudentCourse sc where sc.course.id = :courseId and sc.student.id = :studentId")
    void deleteStudent(@Param("studentId") int studentId, @Param("courseId") int courseId);

}
