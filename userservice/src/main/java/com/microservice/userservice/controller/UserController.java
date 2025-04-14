package com.microservice.userservice.controller;

import com.microservice.userservice.model.UserDTO;
import com.microservice.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam @Email(message = "Invalid Email")
                                                      @NotBlank(message = "Email is required")
                                                      String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/userDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam @Email(message = "Invalid Email")
                               @NotBlank(message = "Email is required")
                               String email) {
        userService.deleteUserByEmail(email);
    }

    @DeleteMapping(path = "/allUserDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @GetMapping("/exists")
    public boolean userExistsByEmail(@RequestParam @Email(message = "Invalid Email")
                                         @NotBlank(message = "Email is required")
                                         String email) {
        return userService.existsByEmail(email);
    }
}
