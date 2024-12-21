package com.LMSAssginment.Code.BusinessLayers.Controllers;

import com.LMSAssginment.Code.DateLayers.Model.UserLoginDto;
import com.LMSAssginment.Code.BusinessLayers.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserLoginDto userDto) {
        String jwtToken = loginService.login(userDto);
        return ResponseEntity.ok(jwtToken);
    }
}
