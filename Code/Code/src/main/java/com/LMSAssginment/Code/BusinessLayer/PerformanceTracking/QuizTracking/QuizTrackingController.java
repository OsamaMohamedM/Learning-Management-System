package com.LMSAssginment.Code.BusinessLayer.PerformanceTracking.QuizTracking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performanceTracking/quiz")
public class QuizTrackingController {

    @GetMapping
    void GetQuizGradesForStudent() {

    }

}
