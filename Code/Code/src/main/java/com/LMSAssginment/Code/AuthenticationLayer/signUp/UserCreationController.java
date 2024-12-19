package com.LMSAssginment.Code.AuthenticationLayer.signUp;

import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/createUser")
public class  UserCreationController {

     @Autowired
     private SignUpService signUpService;

    @PostMapping
    public ResponseEntity<String> CreateUserAccount(@RequestBody User user) {
        try {
            signUpService.AddNewUser(user);
            return ResponseEntity.ok("User Created Successfully");

        } catch (RuntimeException e) {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
