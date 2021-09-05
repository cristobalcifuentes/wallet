package com.alkemy.java;

import com.alkemy.java.model.User;
import com.alkemy.java.repo.IUserRepo;
import com.alkemy.java.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private IUserRepo repo;

    @MockBean
    private IUserRepo userRepo;

    @Autowired
    private IUserService userService;

    @Test
    public void createUser() {
        User us = new User();
        us.setUserId(2);
        us.setUsername("usuaria");
        us.setPassword(bcrypt.encode("123"));

        User newUser = repo.save(us);

        assertTrue(newUser.getPassword().equalsIgnoreCase(us.getPassword()));
    }

    @Test
    public void listAllUsersTest() {

        User user1 = new User();
        user1.setUsername("Test user name 1");
        user1.setUserId(1);
        user1.setPassword(bcrypt.encode("123"));

        User user2 = new User();
        user2.setUsername("Test user name 2");
        user2.setUserId(2);
        user2.setPassword(bcrypt.encode("123"));

        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);

        when(userRepo.findAll()).thenReturn(users);

        assertEquals(2, userService.getAllUsers().size());
    }
}
