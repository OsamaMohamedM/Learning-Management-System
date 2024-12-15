package com.LMSAssginment.Code.AuthenticationLayer.signUp;


import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/createUser")
public class UserController {

     @Autowired
     private SignUpService signUpService;
     @Autowired
     private UserRepo userRepo;

    @PostMapping ("/student")
    public ResponseEntity<String> CreateStudentAccount(@RequestBody Student student) {
        try {

            signUpService.AddNewUser(student);

            return new ResponseEntity<>("User created successfully", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/instructor")
    public ResponseEntity<String> CreateInstructorAccount(@RequestBody Instructor instructor) {
        try {

            signUpService.AddNewUser(instructor);
            return new ResponseEntity<>("User created successfully", HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping ("/admin")
    public ResponseEntity<String> CreateAdminAccount(@RequestBody Admin admin) {
        try {
            signUpService.AddNewUser(admin);
            return new ResponseEntity<>("User created successfully", HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
