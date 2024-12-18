package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Integer> {
        List<StudentCourse> findByStudentId(int studentId);
        @Query("SELECT c FROM Course c")
        List<Course> findAllCourses();

}
