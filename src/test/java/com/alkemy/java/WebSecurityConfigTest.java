package com.alkemy.java;

import com.alkemy.java.controller.UserController;
import com.alkemy.java.model.User;
import com.alkemy.java.security.JWTAuthorizationFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class WebSecurityConfigTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    private User user = new User();

    private String token;

    UserController userController = new UserController();
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/user")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        user.setUserId(1);
        user.setUsername("maca");
        user.setPassword("123");
        String token = userController.getJWTToken(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/user").param("user","maca").param("password","123").header("Authorization", token)).andExpect(status().isOk());

    }

}

