package com.microservice.userservice.UserControllerTest;

import com.microservice.userservice.controller.UserController;
import com.microservice.userservice.model.UserDTO;
import com.microservice.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("0", "Adam", "test@test.com");
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        Mockito.when(userService.getByEmail(userDTO.getEmail())).thenReturn(userDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .param("email", userDTO.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.id").value(userDTO.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .param("email", "invalid"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUserExistByEmail() throws Exception {
        Mockito.when(userService.existsByEmail(Mockito.anyString())).thenAnswer(
                invocation -> userDTO.getEmail().equals(invocation.getArgument(0))
        );

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/exists")
                .param("email", userDTO.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/exists")
                .param("email", "example@test.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testCreateUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDTO);

        String userJson = "{\"name\":\"Adam\",\"email\":\"test@test.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated());
    }
}
