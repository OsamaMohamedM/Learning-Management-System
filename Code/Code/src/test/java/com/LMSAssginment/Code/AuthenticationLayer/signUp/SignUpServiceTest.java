package com.LMSAssginment.Code.AuthenticationLayer.signUp;

import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SignUpServiceTest {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SignUpService signUpService;

    @Test
    public void TestCreatingNewValidUser() {
        User user = new Admin(
                "Nada",
                "123455",
                "Nada44@gmail.com",
                "FEMALE",
                new Date("2000/01/04"),
                "ADMIN"
        );

        assertTrue(signUpService.AddNewUser(user));

        userRepo.delete(user);
    }

    @Test
    public void TestCreateUserWithNullEmail() {
        User user = new Admin(
                "Nada",
                "123455",
                null,
                "FEMALE",
                new Date("2000/01/04"),
                "ADMIN"
        );

        assertFalse(signUpService.AddNewUser(user));

    }

    @Test
    public void TestCreateUserWithDuplicateEmail() {
        User firstUser = new Admin(
                "Nada",
                "123455",
                "Nada2@gmail.com",
                "FEMALE",
                new Date("2000/01/04"),
                "ADMIN"
        );

        userRepo.save(firstUser);

        User secondUser = new Admin(
                "Nada",
                "123455",
                "Nada2@gmail.com",
                "FEMALE",
                new Date("2000/01/04"),
                "ADMIN"
        );

        assertFalse(signUpService.AddNewUser(secondUser));

        userRepo.delete(firstUser);
    }
}