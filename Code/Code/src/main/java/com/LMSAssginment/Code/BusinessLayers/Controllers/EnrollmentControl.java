package com.LMSAssginment.Code.BusinessLayers.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentDTO;
import com.LMSAssginment.Code.BusinessLayers.Services.EnrollService;

@RestController
public class EnrollmentControl {

    @Autowired
    private final EnrollService enrollService;
    EnrollmentControl(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    // enroll a student into a course ?
    @PostMapping("/enroll/{courseId}/{studentId}")
    public void createCourse(@PathVariable int courseId, @PathVariable int studentId) {
        try{
            enrollService.enroll(courseId, studentId);
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }
    // view all enrolled students ?
    @GetMapping("/enroll/view/{courseId}")
    public List<StudentDTO> viewAllCourse(@PathVariable int courseId) {
        try{
            return enrollService.viewAll(courseId);
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
    // drop a student from a course ?
    @DeleteMapping("/drop/{courseId}/{studentId}")
    public void dropCourse(@PathVariable int courseId, @PathVariable int studentId) {
        try{
            enrollService.drop(courseId, studentId);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
