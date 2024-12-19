package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.BusinessLayers.Services.attendService;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/attend")
public class attendController {

    private final attendService lessonAttendanceService;

    public attendController(attendService lessonAttendanceService) {
        this.lessonAttendanceService = lessonAttendanceService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Integer>> markAttendance(
            @RequestParam int courseId,
            @RequestParam int lessonId,
            @RequestParam int studentId,
            @RequestParam int studentOtp
    ) {
        try {
            Map<String, Integer> response = lessonAttendanceService.markAttendance(courseId, lessonId, studentId, studentOtp);
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/students/{lessonId}")
    public List<Student> getStudentsByLessonId(@PathVariable int lessonId) {
        return lessonAttendanceService.getStudentsByLessonId(lessonId);
    }


}

