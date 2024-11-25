package com.linkaster.userService.service;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

import jakarta.servlet.http.HttpServletRequest;
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
    @Value("${address.logicGateway.url}")
    private String logicGatewayAddress;

    // RestTemplate for making requests
    private final RestTemplate restTemplate = new RestTemplate();

    // Repositories for User and Role
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    // Autowired components:
    private final KeyMaster keyMaster;
    
    private final String log_header = "UserHandlerService --- ";

    // Constructor
    @Autowired
    public UserHandlerService(UserRepository userRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, RoleRepository roleRepository, KeyMaster keyMaster) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.keyMaster = keyMaster;
    }

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
            log.error(log_header + "Role does not exist in DB");
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
        
        // Once all is set, save the user
        userRepository.save(newUser);
    
        return true;
    }

    
    // Delete user
    public void deleteUser(long user_id){
        log.info(log_header + "Deleting user with id: '" + user_id + "'...");
        userRepository.deleteById(user_id);
    }


    // Update user
    public boolean updateUser(User userToUpdate){
        // Check if user already exists
        User user = userRepository.findById(userToUpdate.getId()).orElse(null);
        
        if (user != null) {
            log.info(log_header + "User found with id: '" + user.getId() + "'. Updating now...");
            
            user.setFirstName(userToUpdate.getFirstName());
            user.setLastName(userToUpdate.getLastName());
            user.setPassword(userToUpdate.getPassword());
            user.setEmail(userToUpdate.getEmail());
            user.setRole(userToUpdate.getRole());

            userRepository.save(user);

            return true;

        } else {
            log.error(log_header + "User requested to update is not in the DB, returning error....");
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
        log.info(log_header + "UserHandler Service: Getting all users...");
        return userRepository.findAll();
    }

    // Get users by role
    public List<User> getUsersByRole(String role){
        return userRepository.findByRole(role);
    }

    /*
     * STUDENT Only Operations
     */
    //  Get student's teachers based on student ID -> Contact moduleManager INTERSERVICE COMMUNICATION
    public Iterable<TeacherDTO> getStudentTeachers(HttpServletRequest incRequest) {
        String email = incRequest.getAttribute("userEmail").toString();

        log.info(log_header + "Getting student's teachers for '" + email + "' \nContacting moduleManager...");
        StudentUser studentToFind = studentRepository.findByEmail(email);

        String studentIdToFind = studentToFind.getStudentId();

        // Get List of teachers user Id by studentId after calling moduleManager through logicGateway
        String pathToGetStudentTeachers = logicGatewayAddress + "/api/module/student/" + studentIdToFind + "/teachers";

        // Create request back to gateway
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Keep token on call for logicGateway & moduleManager re-verification of token 
        headers.set("Authorization", incRequest.getHeader("Authorization"));
        
        // Add user info to headers
        // Add claims to the req attributes (opt, but would be [id, username, role])
        headers.set("id", incRequest.getAttribute("id").toString());
        headers.set("userEmail", incRequest.getAttribute("userEmail").toString());
        headers.set("role", incRequest.getAttribute("role").toString());

        HttpEntity<String> request = new HttpEntity<>(headers);


        // Call moduleManager through logicGateway to get student's teachers
        try {
            log.info(log_header + "Making request to: " + pathToGetStudentTeachers);
            ResponseEntity<Iterable<Long>> response = restTemplate.exchange(
                pathToGetStudentTeachers, 
                HttpMethod.GET, 
                request, 
                new ParameterizedTypeReference<Iterable<Long>>() {});

            // If response is successful, return the list of teachers
            Iterable<Long> teacherIds = response.getBody();
            log.info(log_header + "Response from module-manager: " + teacherIds + "\nConverting and hetting teachers...");
            Iterable<TeacherDTO> teachers = getTeacherDTOs(teacherIds);

            log.info(log_header + "Returning teachers...");
            return teachers;

        } catch (Exception e) {
            log.error(log_header + "Error getting teachers for student: " + email);
        }

        return null;
    }

    // Get TeacherDTOs from teacherIds
    private Iterable<TeacherDTO> getTeacherDTOs(Iterable<Long> teacherIds) {
        log.info(log_header + "Getting TeacherDTOs from teacherIds...");
        List<TeacherDTO> teachers = new ArrayList<>();

        teacherIds.forEach(teacherId -> {
            TeacherUser teacher = teacherRepository.findById(teacherId).orElse(null);
            if(teacher != null) {
                teachers.add(new TeacherDTO(
                    teacher.getId(),
                    teacher.getFirstName(), 
                    teacher.getLastName(), 
                    teacher.getEmail(),
                    teacher.getSubject()));
            } else {
                log.error(log_header + "Teacher with id: " + teacherId + " not found");
            }
        });

        log.info(log_header + "Returning TeacherDTOs...");
        return teachers;
    }


    public String assignModuleManager(String userEmail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignModuleManager'");
    }


     
    /*
     * TEACHER Only Operations
     */
}
