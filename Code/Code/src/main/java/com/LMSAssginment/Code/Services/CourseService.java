package com.LMSAssginment.Code.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private InstructorCourseRepo courseRepo;

    public Course getCourseById(int id){
        return courseRepo.findById(id).orElse(null);
    }
}
