package com.microservice.userservice.mapper;

import com.microservice.userservice.model.User;
import com.microservice.userservice.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public User toUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .build();
    }
}
