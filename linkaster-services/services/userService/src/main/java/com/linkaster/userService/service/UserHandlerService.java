package com.linkaster.userService.service;

import java.security.KeyPair;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.userService.dto.TeacherDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.model.Role;
import com.linkaster.userService.model.StudentUser;
import com.linkaster.userService.model.TeacherUser;
import com.linkaster.userService.model.User;
import com.linkaster.userService.repository.RoleRepository;
import com.linkaster.userService.repository.StudentRepository;
import com.linkaster.userService.repository.TeacherRepository;
import com.linkaster.userService.repository.UserRepository;
import com.linkaster.userService.util.KeyMaster;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 * This class is responsible for handling all user related operations.
 * It interacts with the UserRepository and RoleRepository.
 * Creates Users and assigns roles while also providing user admin management
 */
@Service
@Transactional
@Slf4j
public class UserHandlerService {

    // Repositories for User and Role
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private RoleRepository roleRepository;

    // Autowired components:
    @Autowired
    private KeyMaster keyMaster;

    
    private final String log_header = "UserHandlerService --- ";

    // Create a new user -> ADMIN VERSION (skips email verif and such)
    public boolean createUser(UserRegistration regRequest, String role) {
        String userEmail = regRequest.getUserEmail();

        log.info(log_header + "Creating user... for:" + userEmail);

        // Check if user already exists
        if (userRepository.findByEmail(userEmail) != null) {
            log.error(log_header + "The user: '" + userEmail + "' already exists");
            return false;
        }

        // Assign role
        Role toAssign = roleRepository.findByRole(role);
        if(toAssign == null) {
            log.error("Role does not exist in DB");
            return false;
        }

        // Create new user
        User newUser;        
        
        String firstName = regRequest.getName();
        String lastName = regRequest.getSurname();
        String email = regRequest.getUserEmail();

        // Hash password through keyMaster
        String password = keyMaster.receivePasswordToHash(regRequest.getPassword());

        // Generate key pair -> values to fill
        KeyPair keyPair;
        String publicKey;
        String privateKey;

        try {
            keyPair = keyMaster.keyGenerator();
            
            privateKey = keyMaster.encodePrivate(keyPair.getPrivate());
            publicKey = keyMaster.encodePublic(keyPair.getPublic());
        } catch (Exception e) {
            log.error(log_header + "Error generating key pair");
            return false;
        }

        if(publicKey.length() < 1 || privateKey.length() < 1) {
            log.error(log_header + "Encoded keys are empty");
            return false;
        }

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
                            .publicKey(publicKey)
                            .privateKey(privateKey)
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
                            .publicKey(publicKey)
                            .privateKey(privateKey)
                            .teachingModules(modules)
                            .build();

                    teacherRepository.save((TeacherUser) newUser);
                    break;
                }

            
            default:
                log.error(log_header + "Invalid role: " + role);
                return false;
        }
        
        // Once all is set, save the user
        userRepository.save(newUser);
    
        return true;
    }

    
    // Delete user
    public void deleteUser(long user_id){
        log.info("Deleting user with id: '" + user_id + "'...");
        userRepository.deleteById(user_id);
    }


    // Update user
    public boolean updateUser(User userToUpdate){
        // Check if user already exists
        User user = userRepository.findById(userToUpdate.getId()).orElse(null);
        
        if (user != null) {
            log.info("User found with id: '" + user.getId() + "'. Updating now...");
            
            user.setFirstName(userToUpdate.getFirstName());
            user.setLastName(userToUpdate.getLastName());
            user.setPassword(userToUpdate.getPassword());
            user.setEmail(userToUpdate.getEmail());
            user.setRole(userToUpdate.getRole());

            userRepository.save(user);

            return true;

        } else {
            log.error("User requested to update is not in the DB, returning error....");
            return false;
        }
    }

    // Get user
    public User getUser(Long id){
        // Check if user already exists
        User user = userRepository.findById(id).orElse(null);
        
        if(user == null){
            log.error("User does not exist in DB");
            return null;
        } else {
            return user;
        }
    }

    // Get all users
    public List<User> getAllUsers(){
        log.info("UserHandler Service: Getting all users...");
        return userRepository.findAll();
    }

    // Get users by role
    public List<User> getUsersByRole(String role){
        return userRepository.findByRole(role);
    }


    /*
     * STUDENT Only Operations
     */
    // Get student's teachers based on student ID
    public List<TeacherDTO> getStudentTeachers(String email) {
        User student = studentRepository.findByEmail(email);

        // Curr debugging
        TeacherDTO teacher = new TeacherDTO();
        List<TeacherDTO> teachers = List.of(teacher);

        return teachers;
    }


     
    /*
     * TEACHER Only Operations
     */
}
