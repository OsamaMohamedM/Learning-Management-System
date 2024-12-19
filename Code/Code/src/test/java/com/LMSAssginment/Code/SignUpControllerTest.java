package com.LMSAssginment.Code;


import com.LMSAssginment.Code.AuthenticationLayer.Security.JwtService;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpController;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpDto;
import com.LMSAssginment.Code.AuthenticationLayer.signUp.SignUpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SignUpController.class)
@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {
    @Autowired
    private MockMvc signUpMock;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SignUpService signUpService;

    @Value("${adminSignUpSecretKey}")
    private String adminSignUpSecretKey;

    @Test

    public void ValidAdminSignUp() throws Exception {
        SignUpDto newAdmin = new SignUpDto(
                "122344",
                "hala",
                "hala@gmail.com",
                "FEMALE",
                adminSignUpSecretKey,
                "ADMIN",
                new Date("2000/01/04")
        );

        signUpMock.perform(
                        post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newAdmin)))
                .andExpect(status().isOk());
    }

    @Test
    public void InValidUserTypeSignUp() throws Exception {
        SignUpDto newAdmin = new SignUpDto(
                "122344",
                "hala",
                "hala@gmail.com",
                "FEMALE",
                adminSignUpSecretKey,
                "INSTRUCTOR",
                new Date("2000/01/04")
        );

        signUpMock.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAdmin)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void InValidAdminSignUpKey() throws Exception {
        SignUpDto newAdmin = new SignUpDto(
                "122344",
                "hala",
                "hala@gmail.com",
                "FEMALE",
                "hfjfjahfjahjfsahjfhaj",
                "ADMIN",
                new Date("2000/01/04")
        );

        signUpMock.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAdmin)))
                .andExpect(status().isUnauthorized());
    }



}