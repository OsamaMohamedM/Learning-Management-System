package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Course.LessonAttendance;
import com.LMSAssginment.Code.DateLayers.Model.Course.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonAttendanceRepository extends JpaRepository<LessonAttendance, LessonAttendanceId> {


}
