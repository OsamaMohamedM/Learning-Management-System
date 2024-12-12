package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    @Modifying
    @Transactional
    @Query("update Course c set c.name = :#{#course.name}, c.description = :#{#course.description}, c.maxNumberOfStudent = :#{#course.maxNumberOfStudent} where c.id = :#{#course.id}")
    void updateCourse(@Param("course") Course course);

}
