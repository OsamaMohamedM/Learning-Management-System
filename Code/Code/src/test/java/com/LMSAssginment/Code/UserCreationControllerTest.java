package com.LMSAssginment.Code;


import com.LMSAssginment.Code.AuthenticationLayer.Security.JwtService;

import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.UserCreationController;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserCreationControllerTest {
    @Autowired
    private MockMvc userCreationMockMvc;

    @MockBean
    private SignUpService signUpService;

    @MockBean
    private JwtService jwtService;


    private String CreateMockUser(String name, String password, String email) throws Exception {
        Student student = new Student(
                name,
                password,
                email,
                "Female",
                new Date("2003/01/09"),
                "STUDENT",
                3.75
        );
        String jsonContent = new ObjectMapper().writeValueAsString(student);
        String jsonWithEntityType = jsonContent.substring(0, jsonContent.length() - 1) +
                ", \"entityType\": \"STUDENT\"}";
        System.out.println(jsonWithEntityType);
        return jsonWithEntityType;
    }


    @Test
    public void TestCreatingNewValidUser() throws Exception {
        String jsonUserContent = CreateMockUser("JUDY", "12345", "JudyK@gmail.com");

        when(signUpService.AddNewUser(any(User.class)))
                .thenReturn(true);

        userCreationMockMvc
                .perform(post("/createUser").contentType(MediaType.APPLICATION_JSON).content(jsonUserContent))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void TestCreatingUserWithNullEmail() throws Exception {
        String jsonUserContent = CreateMockUser("JUDY", "12345", null);


        when(signUpService.AddNewUser(any(User.class)))
                .thenReturn(false);

        userCreationMockMvc
                .perform(post("/createUser").contentType(MediaType.APPLICATION_JSON).content(jsonUserContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void TestCreatingUserWithDuplicateEmail() throws Exception {
        String firstUser = CreateMockUser("JUDY", "12345", "judyjudy@gmail.com");
        String secondUser = CreateMockUser("JUDY", "12345", "judyjudy@gmail.com");
        when(signUpService.AddNewUser(any(User.class)))
                .thenReturn(true);
        userCreationMockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstUser))
                .andExpect(status().isOk());
        when(signUpService.AddNewUser(any(User.class)))
                .thenReturn(false);
        userCreationMockMvc
                .perform(post("/createUser").contentType(MediaType.APPLICATION_JSON).content(secondUser))
                .andExpect(status().isBadRequest());
    }


}






