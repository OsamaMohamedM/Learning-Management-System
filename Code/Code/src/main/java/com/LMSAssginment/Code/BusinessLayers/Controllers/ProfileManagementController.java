package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.BusinessLayers.Services.profileManagment;
import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/profile")
public class ProfileManagementController {

    @Autowired
    private profileManagment profileManagement;

    @GetMapping("/{id}")
    public ResponseEntity<User> viewUserProfile(@PathVariable int id) {
        Optional<User> user = profileManagement.viewUserProfile(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new EntityNotFoundException("user not found");
        }
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Student> updateStudentProfile(@PathVariable int id, @RequestBody Student updatedStudentData) {
        try {
            Student updatedStudent = profileManagement.updateStudentProfile(id, updatedStudentData);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<Admin> updateAdminProfile(@PathVariable int id, @RequestBody Admin updatedAdminData) {
        try {
            Admin updatedAdmin = profileManagement.updateAdminProfile(id, updatedAdminData);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/updateInstructor/{id}")
    public ResponseEntity<Instructor> updateInstructorProfile(@PathVariable int id, @RequestBody Instructor updatedInstructorData) {
        try {
            Instructor updatedInstructor = profileManagement.updateInstructorProfile(id, updatedInstructorData);
            return ResponseEntity.ok(updatedInstructor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
