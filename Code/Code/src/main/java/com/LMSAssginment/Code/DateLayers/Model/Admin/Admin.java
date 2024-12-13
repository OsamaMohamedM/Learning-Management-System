package com.LMSAssginment.Code.DateLayers.Model.Admin;

import com.LMSAssginment.Code.DateLayers.Model.User;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class Admin extends User {


 public Admin() {
       
    }

    public Admin(int id, String name, String email, String gender, Date birthDate) {
        super(id, name, email, gender, birthDate); 
    }
}
