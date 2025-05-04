package com.microservice.userservice.UserRepositoryTest;

import com.microservice.userservice.model.User;
import com.microservice.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    User user;

    @BeforeEach
    void setUp() {
        user = new User("0", "John Doe", "john.doe@example.com");
    }

    @Test
    public void testFindByEmail() {
        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(userOptional.isPresent());
    }
}
