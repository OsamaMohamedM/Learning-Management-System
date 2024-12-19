//package com.LMSAssginment.Code.AuthenticationLayer.Login;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    private  LoginService loginService;
//
//    @PostMapping
//    public ResponseEntity<String> login(@RequestBody UserLoginDto userDto) {
//        String jwtToken = loginService.login(userDto);
//        return ResponseEntity.ok(jwtToken);
//    }
//}
