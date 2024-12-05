package com.linkaster.userService.service;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHandlerServiceTest {


    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private KeyMaster keyMaster;
    
    private UserHandlerService userHandlerService;

    private final String log_header = "UserHandlerServiceTest --- ";

    @BeforeEach
    public void setUp() {
        log.info(log_header + "Setting up mocks for UserHandlerServiceTest...");
        userRepository = mock(UserRepository.class);
        studentRepository = mock(StudentRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        roleRepository = mock(RoleRepository.class);
        keyMaster = mock(KeyMaster.class);

        
        log.info(log_header + "Creating UserHandlerService instance...");
        userHandlerService = new UserHandlerService(userRepository, studentRepository, teacherRepository, roleRepository, keyMaster);

        log.info(log_header + "Mocks set up and UserHandlerService instance created!\nBeggining tests...\n");
        //MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudentUser_Successful() throws Exception {
        // Make up fake user -> student
        log.info(log_header + "STUDENT REGISTRATION TEST: Creating fake student...");
        UserRegistration regReq = new UserRegistration();
        regReq.setName("John");
        regReq.setSurname("Doe");
        regReq.setUserEmail("john_doe@lancaster.ac.uk");
        regReq.setPassword("password");
        regReq.setStudentId("1234567");
        regReq.setYear("2");
        regReq.setStudyProg("Computer Science");

        // Make up fake role
        log.info(log_header + "Creating fake role...");
        Role studentRole = new Role();
        studentRole.setId(2);
        studentRole.setRole("student");
        studentRole.setDescription("Student role");

        // Make up fake key pair
        log.info(log_header + "Creating fake key pair...");
        KeyPair keyPair = new KeyPair(
            mock(java.security.PublicKey.class),
            mock(java.security.PrivateKey.class)
        );

        // Set up whens
        log.info(log_header + "Setting up whens...");
        when(userRepository.findByEmail("john_doe@lancaster.ac.uk")).thenReturn(null);
        when(roleRepository.findByRole("student")).thenReturn(studentRole);
        when(keyMaster.receivePasswordToHash("password")).thenReturn("hashedPassword");
        when(keyMaster.keyGenerator()).thenReturn(keyPair);
        when(keyMaster.encodePublic(keyPair.getPublic())).thenReturn("encodedPublicKey");
        when(keyMaster.encodePrivate(keyPair.getPrivate())).thenReturn("encodedPrivateKey");

        // Call the service
        log.info(log_header + "Calling the service to test...");
        boolean result = userHandlerService.createUser(regReq, "student");

        // Assert
        log.info(log_header + "Asserting...");
        assertTrue(result);
        verify(studentRepository, times(1)).save(any(StudentUser.class));
        verify(userRepository, times(1)).save(any(User.class));

        log.info(log_header + "Test passed!\n\n");

    }

    @Test
    public void testCreateStudentUser_Unsuccessful_EmailExists() {
        log.info(log_header + "Testing unsuccessful student registration due to existing email...");

        // Make up fake user registration
        UserRegistration regReq = new UserRegistration();
        regReq.setName("Jane");
        regReq.setSurname("Doe");
        regReq.setUserEmail("jane_doe@lancaster.ac.uk");
        regReq.setPassword("password");

        // Simulate existing email
        when(userRepository.findByEmail("jane_doe@lancaster.ac.uk")).thenReturn(new StudentUser());

        // Call the service
        boolean result = userHandlerService.createUser(regReq, "student");

        // Assert failure
        assertFalse(result);
        verify(studentRepository, times(0)).save(any(StudentUser.class));
        verify(userRepository, times(0)).save(any(User.class));

        log.info(log_header + "Unsuccessful registration test passed!");
}

    @Test
    public void testCreateTeacherUser_Successful() throws Exception {
        // Make up fake user -> teacher
        log.info(log_header + "TEACHER REGISTRATION TEST: Creating fake teacher...");
        UserRegistration regReq = new UserRegistration();
        regReq.setName("John");
        regReq.setSurname("Doe");
        regReq.setUserEmail("john_doe@lancaster.ac.uk");
        regReq.setPassword("password");
        regReq.setSubject("Digital Systems");

        // Make up fake role
        log.info(log_header + "Creating fake role...");
        Role teacherRole = new Role();
        teacherRole.setId(3);
        teacherRole.setRole("teacher");
        teacherRole.setDescription("Teacher role");

        // Make up fake key pair
        log.info(log_header + "Creating fake key pair...");
        KeyPair keyPair = new KeyPair(
            mock(java.security.PublicKey.class),
            mock(java.security.PrivateKey.class)
        );

        // Set up whens
        log.info(log_header + "Setting up whens...");
        when(userRepository.findByEmail("john_doe@lancaster.ac.uk")).thenReturn(null);
        when(roleRepository.findByRole("teacher")).thenReturn(teacherRole);
        when(keyMaster.receivePasswordToHash("password")).thenReturn("hashedPassword");
        when(keyMaster.keyGenerator()).thenReturn(keyPair);
        when(keyMaster.encodePublic(keyPair.getPublic())).thenReturn("encodedPublicKey");
        when(keyMaster.encodePrivate(keyPair.getPrivate())).thenReturn("encodedPrivateKey");

        // Call the service
        log.info(log_header + "Calling the service to test...");
        boolean result = userHandlerService.createUser(regReq, "teacher");

        // Assert
        log.info(log_header + "Asserting...");
        assertTrue(result);
        verify(teacherRepository, times(1)).save(any(TeacherUser.class));
        verify(userRepository, times(1)).save(any(User.class));

        log.info(log_header + "Test passed!\n\n");

    }


    
}
