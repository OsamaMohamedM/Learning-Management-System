package com.LMSAssginment.Code.DateLayers.Model.Admin;

import com.LMSAssginment.Code.DateLayers.Model.User;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class Admin extends User {


    public Admin() {

    }

    public Admin(String name, String password, String email, String gender, Date birthDate, String userType) {
        super(name, password, email, gender, birthDate, userType);
    }


}
