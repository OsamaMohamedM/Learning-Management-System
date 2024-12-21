
package com.LMSAssginment.Code.BusinessLayers.Controllers;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.LMSAssginment.Code.BusinessLayers.Services.CourseService;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Course.CourseDTO;
import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;

import java.util.List;

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
        try {
            courseService.addCourse(courseRequest, instructorId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @DeleteMapping("delete/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @PostMapping("/update")
    public void updateCourse(@RequestBody Course course) {
        try {
            courseService.updateCourse(course);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @GetMapping("/view")
    public List<CourseDTO> viewAllCourse() {
        try {
            return courseService.viewAll();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/addLesson")
    public void addLesson(@RequestBody Lesson lesson) {
        try {
            courseService.addLesson(lesson);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @PostMapping("/lesson/{lessonId}/uploadMedia")
    public ResponseEntity<String> uploadMediaToLesson(
            @PathVariable int lessonId,
            @RequestParam("file") MultipartFile file) {
        try {
            courseService.uploadMedia(lessonId, file);
            return ResponseEntity.ok("Media uploaded successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file.");
        }
    }


    @GetMapping("/{lessonId}/media")
    public ResponseEntity<byte[]> getLessonMedia(@PathVariable int lessonId) {
        try {
            byte[] mediaContent = courseService.getLessonMedia(lessonId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(mediaContent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

//package com.LMSAssginment.Code.BusinessLayers.Controllers;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
//import com.LMSAssginment.Code.DateLayers.Model.Course.CourseDTO;
//import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
//import com.LMSAssginment.Code.BusinessLayers.Services.CourseService;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@RestController
//@RequestMapping("/course")
//public class CourseControl {
//    @Autowired
//    private final CourseService courseService;
//    CourseControl(CourseService courseService) {
//        this.courseService = courseService;
//    }
//
//    @PostMapping("/create/{instructorId}")
//    public void createCourse(@RequestBody Course courseRequest, @PathVariable int instructorId) {
//        try{
//            courseService.addCourse(courseRequest, instructorId);
//        }
//        catch(Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    @DeleteMapping("delete/{courseId}")
//    public void deleteCourse(@PathVariable int courseId) {
//        try{
//            courseService.deleteCourse(courseId);
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    @PostMapping("/update")
//    public void updateCourse(@RequestBody Course course) {
//        try{
//            courseService.updateCourse(course);
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    @GetMapping("/view")
//    public List<CourseDTO> viewAllCourse() {
//        try{
//            return courseService.viewAll();
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }
//
//    @PostMapping("/addLesson")
//    public void addLesson(@RequestBody Lesson lesson) {
//        try{
//            courseService.addLesson(lesson);
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
//
//}
//

