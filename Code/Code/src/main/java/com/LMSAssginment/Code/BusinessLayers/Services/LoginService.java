package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.UserLoginDto;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import com.LMSAssginment.Code.Security.JwtService;
import com.LMSAssginment.Code.Security.SecuredUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private  AuthenticationManager authenticationManager;
    private  UserRepo userRepo;
    private JwtService jwtService;

    public LoginService(AuthenticationManager authenticationManager, UserRepo userRepo, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }
    public String login(UserLoginDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );

        User user = userRepo.findByEmail(userDto.getEmail()).orElseThrow(() -> new RuntimeException("Invalid userName or password"));

        String jwtToken = jwtService.generateToken(new SecuredUser(user));
        return jwtToken;
    }
}
