package com.LMSAssginment.Code.AuthenticationLayer.signUp;

import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    private UserRepo userRepo;

    public boolean AddNewUser(User user) {
        if(user.getEmail() == null || userRepo.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // Encrypt the password before storing it
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return true;
    }
}
