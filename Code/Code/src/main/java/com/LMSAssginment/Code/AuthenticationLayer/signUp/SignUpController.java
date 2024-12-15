package com.LMSAssginment.Code.AuthenticationLayer.signUp;

import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    @Value("${adminSignUpSecretKey}")
    private String adminSignUpSecretKey;
    @Autowired
    private SignUpService signUpService;

    @PostMapping
    public void signUpNewAdmin(@RequestBody SignUpDto signUpDto) {
        if(!adminSignUpSecretKey.equals(signUpDto.getSignUpKey()) || !signUpDto.getUserType().equals("ADMIN")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Unauthorized: Only Admin can sign up"
            );
        }

        Admin admin = new Admin(
                signUpDto.getName(),
                signUpDto.getPassword(),
                signUpDto.getEmail(),
                signUpDto.getGender(),
                signUpDto.getBirthDate(),
                signUpDto.getUserType()
        );

        signUpService.AddNewUser(admin);
    }
}
