package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Long> {
        List<StudentCourse> findByStudentId(Long studentId);
        @Query("SELECT c FROM Course c")
        List<Course> findAllCourses();

}
