package com.linkaster.userService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.ProfileInfoDTO;
import com.linkaster.userService.model.StudentUser;
import com.linkaster.userService.model.User;

import com.linkaster.userService.repository.StudentRepository;
import com.linkaster.userService.repository.TeacherRepository;
import com.linkaster.userService.repository.UserRepository;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;



// Service to customize user's profile
// To be implemented
@Service
@Transactional
@Slf4j
public class UserCustomizerService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public UserCustomizerService(UserRepository userRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;

    }

    // Get the profile of a student
    public ProfileInfoDTO getProfileStudent(HttpServletRequest request) {
        String userEmail = request.getAttribute("userEmail").toString();
        
        // Create a new profile object
        ProfileInfoDTO profile = new ProfileInfoDTO();

        User user = userRepository.findByEmail(userEmail);
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setEmail(user.getEmail());
        profile.setRole(user.getRole().toString());    

        // Get student info
        StudentUser student = studentRepository.findByEmail(userEmail);

        profile.setStudentId(student.getStudentId());
        profile.setYear(student.getYear());

        // Send the profile info back
        return profile;

    }

    // Get the profile of a teacher
    public ProfileInfoDTO getProfileTeacher(HttpServletRequest request) {
        String userEmail = request.getAttribute("userEmail").toString();
        
        // Create a new profile object
        ProfileInfoDTO profile = new ProfileInfoDTO();

        User user = userRepository.findByEmail(userEmail);
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setEmail(user.getEmail());
        profile.setRole(user.getRole().toString());
        
        // Send the profile info back
        return profile;

    }
    
}
