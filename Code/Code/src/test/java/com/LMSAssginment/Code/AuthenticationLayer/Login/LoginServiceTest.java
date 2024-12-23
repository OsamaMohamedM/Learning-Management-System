package com.LMSAssginment.Code.AuthenticationLayer.Login;


import com.LMSAssginment.Code.AuthenticationLayer.signUp.UserCreationController;
import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserCreationController userCreationController;

    private static User dummyUser;

    @BeforeEach
    public void AddDummyUser() {

        dummyUser = new Admin(
                "Nada",
                "123455",
                "nadaMoh.eee@gmail.com",
                "FEMALE",
                new Date("2000/01/04"),
                "ADMIN"
        );

        userCreationController.CreateUserAccount(dummyUser);
    }

    @Test
    public void TestValidCredentials() {
        UserLoginDto loginDto = new UserLoginDto(
                "nadaMoh.eee@gmail.com",
                "123455"

        );

        String token = loginService.login(loginDto);
        assertNotNull(token, "A token should be generated for valid credentials.");
    }

    @Test
    public void TestInValidCredentials() {
        // invalid password
        UserLoginDto invalidLoginDto = new UserLoginDto(
                "nadaMoh.eee@gmail.com",
                "12355"

        );

        assertThrows(BadCredentialsException.class, () -> {
            loginService.login(invalidLoginDto);
        });
    }

    @AfterEach
    public void RemoveDummyUser() {
        userRepo.delete(dummyUser);
    }
}

