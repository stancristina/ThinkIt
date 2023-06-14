package org.fmi.unibuc.service;

import org.fmi.unibuc.domain.User;
import org.fmi.unibuc.repository.AuthorityRepository;
import org.fmi.unibuc.repository.UserRepository;
import org.fmi.unibuc.service.dto.UserDTO;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceEquivalencePartitioningTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthorityRepository authorityRepository;

    private UserService userService;

    @BeforeEach
    public void setUserService() {
        MockitoAnnotations.initMocks(this);

        List<User> persistedUsers = new ArrayList<>(Arrays.asList(
            new User("demouser1", "Joe", "Doe", "demouser1@gmail.com",
                "demoUrl1", true, "EN"),
            new User("demouser2", "Jack", "Gray", "demouser2@gmail.com",
                "demoUrl2", true, "EN"),
            new User("demouser3", "Dan", "Zack", "demouser3@gmail.com",
                "demoUrl3", false, "EN"),
            new User("demouser4", "Ben", "Andrew", "demouser4@gmail.com",
                "demoUrl4", false, "EN")
        ));
        when(userRepository.findOneByLogin(anyString())).thenAnswer(new Answer<Optional<User>>() {
            @Override
            public Optional<User> answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                String login = (String) args[0];
                return persistedUsers.stream().filter(p -> p.getLogin().equals(login)).findFirst();
            }
        });

        when(userRepository.findOneByEmailIgnoreCase(anyString())).thenAnswer(new Answer<Optional<User>>() {
            @Override
            public Optional<User> answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                String email = (String) args[0];
                return persistedUsers.stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst();
            }
        });

        when(userRepository.save(any())).thenReturn(null);
        doNothing().when(userRepository).delete(any());
        doNothing().when(userRepository).flush();

        userService = new UserService(userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    public void testRegisterUserU1P1() {
        UserDTO userDTO = null;
        String password = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU1P2() {
        UserDTO userDTO = null;
        String password = "demoPassword";

        Assertions.assertThrows(IllegalArgumentException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU2P1() {
        UserDTO userDTO = new UserDTO("demouser1", "Joe", "Doe", "demouser1@gmail.com",
            "demoUrl1", false, "EN");
        String password = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU2P2() {
        UserDTO userDTO = new UserDTO("demouser1", "Joe", "Doe", "demouser1@gmail.com",
            "demoUrl1", true, "EN");
        String password = "password";

        Assertions.assertThrows(UsernameAlreadyUsedException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU3P1() {
        UserDTO userDTO = new UserDTO("demouser5", "Joe", "Doe", "demouser1@gmail.com",
            "demoUrl1", false, "EN");
        String password = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU3P2() {
        UserDTO userDTO = new UserDTO("demouser5", "Joe", "Doe", "demouser1@gmail.com",
            "demoUrl1", false, "EN");
        String password = "password";
        Assertions.assertThrows(EmailAlreadyUsedException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU4P1() {
        UserDTO userDTO = new UserDTO("demouser5", "Joe", "Doe", "demouser1@gmail.com",
            "demoUrl1", false, "EN");
        String password = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> { userService.registerUser(userDTO, password); });
    }

    @Test
    public void testRegisterUserU4P2() {
        UserDTO userDTO = new UserDTO("demouser5", "Joe", "Doe", "demouser5@gmail.com",
            "demoUrl1", false, "EN");
        String password = "password";
        User user = userService.registerUser(userDTO, password);
        Assertions.assertEquals(userDTO.getLogin(), user.getLogin());
        Assertions.assertEquals(userDTO.getEmail(), user.getEmail());
        Assertions.assertEquals(userDTO.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userDTO.getLastName(), user.getLastName());
        Assertions.assertEquals(userDTO.getLangKey(), user.getLangKey());
        Assertions.assertEquals(userDTO.getImageUrl(), user.getImageUrl());
    }

}
