package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.LessonAttendance;
import com.LMSAssginment.Code.DateLayers.Model.Course.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonAttendanceRepository extends JpaRepository<LessonAttendance, LessonAttendanceId> {
    List<LessonAttendance> findByLessonId(int lessonId);


    List<LessonAttendance> findByCourseIdAndLessonId(int courseId, int lessonId);
}
