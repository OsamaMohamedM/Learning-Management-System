package com.LMSAssginment.Code.BusinessLayers.Controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Course.CourseDTO;
import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
import com.LMSAssginment.Code.BusinessLayers.Services.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/course")
public class CourseControl {
    @Autowired
    private final CourseService courseService;
    CourseControl(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create/{instructorId}")
    public void createCourse(@RequestBody Course courseRequest, @PathVariable int instructorId) {
        try{
            courseService.addCourse(courseRequest, instructorId);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
    }
    
    @DeleteMapping("delete/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        try{
            courseService.deleteCourse(courseId);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @PostMapping("/update")
    public void updateCourse(@RequestBody Course course) {
        try{
            courseService.updateCourse(course);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    @GetMapping("/view")
    public List<CourseDTO> viewAllCourse() {
        try{
            return courseService.viewAll();
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/addLesson")
    public void addLesson(@RequestBody Lesson lesson) {
        try{
            courseService.addLesson(lesson);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
