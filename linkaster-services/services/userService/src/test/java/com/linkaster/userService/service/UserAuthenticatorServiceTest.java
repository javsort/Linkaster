
package com.linkaster.userService.service;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;

import com.linkaster.userService.repository.RoleRepository;
import com.linkaster.userService.repository.StudentRepository;
import com.linkaster.userService.repository.TeacherRepository;
import com.linkaster.userService.repository.UserRepository;
import com.linkaster.userService.util.KeyMaster;

import lombok.extern.slf4j.Slf4j;

  /*
 *  Title: UserAuthenticatorServiceTest.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Slf4j
public class UserAuthenticatorServiceTest {
    private final String log_header = "UserAuthenticatorServiceTest --- ";

    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private KeyMaster keyMaster;
    
    private UserAuthenticatorService userAuthenticatorService;

    @BeforeEach
    public void setUp() {
        log.info(log_header + "Setting up mocks for UserAuthenticatorServiceTest...");
        userRepository = mock(UserRepository.class);
        studentRepository = mock(StudentRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        roleRepository = mock(RoleRepository.class);
        keyMaster = mock(KeyMaster.class);

        
        log.info(log_header + "Creating UserAuthenticatorService instance...");
        //userAuthenticatorService = new UserAuthenticatorService(userRepository, studentRepository, teacherRepository, roleRepository, keyMaster);
    }
}
