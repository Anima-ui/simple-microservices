package com.microservice.userservice.UserServiceTest;

import com.microservice.userservice.mapper.Mapper;
import com.microservice.userservice.model.User;
import com.microservice.userservice.model.UserDTO;
import com.microservice.userservice.repository.UserRepository;
import com.microservice.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("0", "Adam", "test@test.com");
        user = new User("0", "Adam", "test@test.com");
    }

    @Test
    public void testGetByEmail() {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserDTO res = userService.getByEmail("test@test.com");
        Assertions.assertEquals(res, mapper.toUserDTO(user));
        Mockito.verify(userRepository).findByEmail("test@test.com");
    }

    @Test
    public void testExistsByEmail() {
        Mockito.when(userRepository.existsByEmail(Mockito.anyString()))
                .thenAnswer(invocationOnMock -> {
            String mail = invocationOnMock.getArgument(0); // takes arguments from when() methods
            return "test@test.com".equals(mail);
        });
        boolean res = userService.existsByEmail("test@test.com");
        Assertions.assertTrue(res);
        boolean res2 = userService.existsByEmail("tes@test.com");
        Assertions.assertFalse(res2);
        Mockito.verify(userRepository).existsByEmail("test@test.com");
        Mockito.verify(userRepository).existsByEmail("tes@test.com");
    }

    @Test
    public void testDeleteUserByEmail_WhenNotFound() {
        Mockito.when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.deleteUserByEmail("notfound@test.com"));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(mapper.toUser(userDTO)).thenReturn(user);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.createUser(userDTO));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testCreateUser_Success() {
        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(mapper.toUser(userDTO)).thenReturn(user);
        Mockito.when(mapper.toUserDTO(Mockito.any())).thenReturn(userDTO);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDTO res = userService.createUser(userDTO);
        Assertions.assertEquals(res, userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

}
