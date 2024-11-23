package com.linkaster.userService.service;

import java.security.KeyPair;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.model.Role;
import com.linkaster.userService.model.StudentUser;
import com.linkaster.userService.model.TeacherUser;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.RoleRepository;
import com.linkaster.userService.repository.StudentRepository;
import com.linkaster.userService.repository.TeacherRepository;
import com.linkaster.userService.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


// Proving who you are - Authentication

/*
 * This class is responsible for authenticating users.
 * ONLY CLASS with no authorization required. Directly pinged by logicGateway to verify user credentials or register.
 */
@Service
@Transactional
@Slf4j
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class UserAuthenticatorService {

    // Repository for User
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final String log_header = "UserAuthenticatorService: ";

    // Authenticate user -> Check if user exists and if password is correct
    public boolean authenticateUser(String userEmail, String password) {
        log.info(log_header + "Authenticating user: " + userEmail);

        // Check if user exists
        if (userRepository.findByEmail(userEmail) == null) {
            log.error(log_header + "The user: '" + userEmail + "' does not exist");
            return false;
        }

        // Check if password is correct
        if (!userRepository.findByEmail(userEmail).getPassword().equals(password)) {
            log.error(log_header + "Incorrect password for user: '" + userEmail + "'");
            return false;
        }

        return true;
    }

    // Slightly different to the one found in user handler. User handler is for admin tasks, this for retrieving necessary info for JWT token
    public AuthUser getAuthenticatedUser(String userEmail) {
        log.info("Getting user: " + userEmail);

        User toGet = userRepository.findByEmail(userEmail);

        return new AuthUser(toGet.getId(), toGet.getEmail(), toGet.getRole().getRole());

    }

    // Register user -> Create new user
    public boolean registerUser(UserRegistration regRequest, String role) {

        // First check if it exists based on email
        String userEmail = regRequest.getUserEmail();

        log.info(log_header + "Registering user: " + userEmail);

        // Check if user already exists
        if (userRepository.findByEmail(userEmail) != null) {
            log.error(log_header + "The user: '" + userEmail + "' already exists");
            return false;
        }

        // Create new user
        User newUser;
        
        String firstName = regRequest.getName();
        String lastName = regRequest.getSurname();
        String password = regRequest.getPassword();
        String email = regRequest.getUserEmail();
        KeyPair keyPair = null;

        // Create new user based on the path role
        switch (role) {
            case "student":
                {
                    log.info("Registering student with email: " + email);
                    
                    // Get student pertinent fields:
                    Role studentRole = roleRepository.findByRole("student");
                    String studentId = regRequest.getStudentId();
                    String course = regRequest.getStudyProg();
                    Integer year = regRequest.getYear();
                    List<String> modules = null;
                    newUser = StudentUser.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .password(password)
                            .email(email)
                            .role(studentRole)
                            .keyPair(keyPair)
                            .studentId(studentId)
                            .course(course)
                            .year(year)
                            .registeredModules(modules)
                            .build();

                    studentRepository.save((StudentUser) newUser);
                    break;
                }
            case "teacher":
                {
                    log.info("Registering teacher with email: " + email);
                    // Get teacher pertinent fields:
                    Role teacherRole = roleRepository.findByRole("teacher");
                    List<String> modules = null;
                    newUser = TeacherUser.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .password(password)
                            .email(email)
                            .role(teacherRole)
                            .keyPair(keyPair)
                            .teachingModules(modules)
                            .build();

                    teacherRepository.save((TeacherUser) newUser);
                    break;
                }

            
            default:
                log.error(log_header + "Invalid role: " + role);
                return false;
        }
        userRepository.save(newUser);

        log.info(log_header + "User: '" + userEmail + "' registered");
        return true;
    }
    
}
