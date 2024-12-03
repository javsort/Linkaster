package com.linkaster.userService.service;

import java.security.KeyPair;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
import com.linkaster.userService.util.KeyMaster;

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
    @Value("${address.logicGateway.url}")
    private String logicGatewayAddress;

    // RestTemplate for making requests
    private final RestTemplate restTemplate = new RestTemplate();

    // Repositories
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    // Autowired components:
    private final KeyMaster keyMaster;

    private final String log_header = "UserAuthenticatorService --- ";

    @Autowired
    public UserAuthenticatorService(UserRepository userRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, RoleRepository roleRepository, KeyMaster keyMaster) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.keyMaster = keyMaster;
    }

    // Authenticate user -> Check if user exists and if password is correct
    public boolean authenticateUser(String userEmail, String password) {
        log.info(log_header + "Authenticating user: " + userEmail);

        // Check if user exists
        if (userRepository.findByEmail(userEmail) == null) {
            log.error(log_header + "The user: '" + userEmail + "' does not exist");
            return false;
        }

        // Check if password is correct
        if(!checkPassword(userEmail, password)) {
            log.error(log_header + "Incorrect password for user: '" + userEmail + "'");
            return false;
        }

        return true;
    }

    // Slightly different to the one found in user handler. User handler is for admin tasks, this for retrieving necessary info for JWT token
    public AuthUser getAuthenticatedUser(String userEmail) {
        log.info(log_header + "Getting user: " + userEmail);

        User toGet = userRepository.findByEmail(userEmail);

        return new AuthUser(toGet.getId(), toGet.getEmail(), toGet.getRole().getRole());

    }

    // Register user -> Create new user
    public boolean registerUser(UserRegistration regRequest, String role) {

        // First check if it exists based on email
        String userEmail = regRequest.getUserEmail();

        log.info(log_header + "Registering user: " + userEmail);

        if (!verifyEmail(userEmail)) {
            log.error(log_header + "Invalid email: " + userEmail);
            return false;
        }

        // Check if user already exists
        if (userRepository.findByEmail(userEmail) != null) {
            log.error(log_header + "The user: '" + userEmail + "' already exists");
            return false;
        }

        // Create new user
        User newUser;        
        
        String firstName = regRequest.getName();
        String lastName = regRequest.getSurname();
        String email = regRequest.getUserEmail();
        
        
        // Create a KeyMaster object instance to make keys and encrypt password
        String password = keyMaster.receivePasswordToHash(regRequest.getPassword());

        // Generate key pair -> values to fill
        KeyPair keyPair;
        String publicKey;
        String privateKey;

        try {
            keyPair = keyMaster.keyGenerator();
            
            privateKey = keyMaster.encodePrivate(keyPair.getPrivate());
            publicKey = keyMaster.encodePublic(keyPair.getPublic());

            log.info(log_header + "Public key: \nLength: " + publicKey.length() + "\nKey: '" + publicKey + "'\n");
            log.info(log_header + "Private key: \nLength: " + privateKey.length() + "\nKey: '" + privateKey + "'\n");

            // Then get their encrypted versions
            
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
                    log.info(log_header + "Registering student with email: " + email);
                    
                    // Get student pertinent fields:
                    Role studentRole = roleRepository.findByRole("student");
                    String studentId = regRequest.getStudentId();
                    String course = regRequest.getStudyProg();
                    String year = regRequest.getYear();
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
                            .build();

                    studentRepository.save((StudentUser) newUser);
                    break;
                }
            case "teacher":
                {
                    log.info(log_header + "Registering teacher with email: " + email);
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

    private boolean verifyEmail(String email) {
        // First check if its email actually
        if(!email.contains("@") || !email.contains(".")) {
            return false;
        }


        // Then check if it is a lancaster email
        String domain = "lancaster.ac.uk";

        return email.endsWith("@" + domain);
    }

    private boolean checkPassword(String userEmail, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encryptedPassword = userRepository.findByEmail(userEmail).getPassword();

        return bCryptPasswordEncoder.matches(password, encryptedPassword);
    }

    public boolean createTimetable(long newUserId){
        log.info(log_header + "Pinging Timetable Service to create timetable for new user with id: '" + newUserId + "'...");

        // Create request to timetable service
        String pathToCreateTimetable = logicGatewayAddress + "/api/timetable/create/" + newUserId;

        // Create request back to gateway
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestToMessageService = new HttpEntity<>(headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                pathToCreateTimetable, 
                HttpMethod.POST, 
                requestToMessageService, 
                Boolean.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                log.error(log_header + "Error occurred while creating timetable for user: '" + newUserId + "'");
                return false;
            }

        } catch (Exception e) {
            log.error(log_header + "Error occurred while creating timetable for user: '" + newUserId + "'");
            return false;
        }
    }
    
}
