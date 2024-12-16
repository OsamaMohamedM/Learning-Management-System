//package com.LMSAssginment.Code.AuthenticationLayer.signUp;
//
//import com.LMSAssginment.Code.DateLayers.Model.User;
//import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SignUpService {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    public void AddNewUser(User user) {
//
//        if(userRepo.findByEmail(user.getEmail()).isPresent()) {
//            throw new RuntimeException("The provided email is already in use.");
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        String encodedPassword = encoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        userRepo.save(user);
//    }
//}
