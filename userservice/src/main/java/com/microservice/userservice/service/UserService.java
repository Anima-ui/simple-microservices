package com.microservice.userservice.service;

import com.microservice.userservice.mapper.Mapper;
import com.microservice.userservice.model.User;
import com.microservice.userservice.model.UserDTO;
import com.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserDTO getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return mapper.toUserDTO(user.get());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.toUser(userDTO);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        User savedUser = userRepository.save(user);
        return mapper.toUserDTO(savedUser);
    }

    public void deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.delete(user.get());
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
