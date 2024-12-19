package com.LMSAssginment.Code.AuthenticationLayer.signUp;

import java.util.Date;

public class SignUpDto {
    private final String Password;
    private final String name;
    private final String email;
    private final String gender;
    private final String userType;
    private final Date birthDate;
    private final String signUpKey;

    public SignUpDto(String password, String name, String email, String gender, String signUpKey, String userType, Date birthDate) {
        Password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.userType = userType;
        this.signUpKey = signUpKey;
        this.birthDate = birthDate;

    }


    public String getSignUpKey() {
        return signUpKey;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getUserType() {
        return userType;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
