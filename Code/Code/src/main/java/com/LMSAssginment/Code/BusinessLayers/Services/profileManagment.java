package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.Admin.Admin;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.User;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class profileManagment {

    @Autowired
    private UserRepo userRepo;

    public Optional <User> viewUserProfile(int userId){
        return userRepo.findById(userId);
    }

    public Student updateStudentProfile(int userId, Student updatedStudentData) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (existingUser instanceof Student) {
                Student existingStudent = (Student) existingUser;
                if(updatedStudentData.getName() != null)
                    existingStudent.setName(updatedStudentData.getName());
                if(updatedStudentData.getEmail() != null)
                    existingStudent.setEmail(updatedStudentData.getEmail());
                if(updatedStudentData.getGender() != null)
                    existingStudent.setGender(updatedStudentData.getGender());
                if(updatedStudentData.getBirthDate() != null)
                    existingStudent.setBirthDate(updatedStudentData.getBirthDate());
                if(updatedStudentData.getPassword() != null)
                    existingStudent.setPassword(updatedStudentData.getPassword());

                return userRepo.save(existingStudent);
            } else {
                throw new RuntimeException("User is not a Student");
            }
        }
        throw new RuntimeException("User not found");
    }

    public Admin updateAdminProfile(int userId, Admin updatedAdminData) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (existingUser instanceof Admin) {
                Admin existingAdmin = (Admin) existingUser;
                if(updatedAdminData.getName() != null)
                    existingAdmin.setName(updatedAdminData.getName());
                if(updatedAdminData.getEmail() != null)
                    existingAdmin.setEmail(updatedAdminData.getEmail());
                if(updatedAdminData.getGender() != null)
                    existingAdmin.setGender(updatedAdminData.getGender());
                if(updatedAdminData.getBirthDate() != null)
                    existingAdmin.setBirthDate(updatedAdminData.getBirthDate());
                if(updatedAdminData.getPassword() != null)
                    existingAdmin.setPassword(updatedAdminData.getPassword());
                return userRepo.save(existingAdmin);
            } else {
                throw new RuntimeException("User is not an Admin");
            }
        }
        throw new RuntimeException("User not found");
    }

    public Instructor updateInstructorProfile(int userId, Instructor updatedInstructorData) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (existingUser instanceof Instructor) {
                Instructor existingInstructor = (Instructor) existingUser;
                if(updatedInstructorData.getName() != null)
                    existingInstructor.setName(updatedInstructorData.getName());
                if(updatedInstructorData.getEmail() != null)
                    existingInstructor.setEmail(updatedInstructorData.getEmail());
                if(updatedInstructorData.getGender() != null)
                    existingInstructor.setGender(updatedInstructorData.getGender());
                if(updatedInstructorData.getBirthDate() != null)
                    updatedInstructorData.setBirthDate(updatedInstructorData.getBirthDate());
                if(existingInstructor.getPassword() != null)
                    existingInstructor.setPassword(updatedInstructorData.getPassword());
                return userRepo.save(existingInstructor);
            } else {
                throw new RuntimeException("User is not an Admin");
            }
        }
        throw new RuntimeException("User not found");
    }


}
