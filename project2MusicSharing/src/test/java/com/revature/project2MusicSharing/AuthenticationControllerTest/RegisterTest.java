package com.revature.project2MusicSharing.AuthenticationControllerTest;

import com.revature.controllers.AuthenticationController;
import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegisterTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserService userService;

    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authenticationController = new AuthenticationController(userService, jwtTokenUtil);

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        testUser = new User();
        testUser.setUsername("Wei");
        testUser.setPassword("password");
        testUser.setRole("Admin");
    }

    @Test
    void EmptyUserNameInput() throws Exception {
        try {
            String requestBody = "{\"username\": \"\", \"password\": \"password\", \"role\": \"Admin\"}";
            when(userDAO.findByUsername(testUser.getUsername())).thenReturn(null);

            when(passwordEncoder.encode(any())).thenReturn(testUser.getPassword());

            when(userDAO.save(any(User.class))).thenReturn(testUser);

            mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value("Wei"))
                    .andExpect(jsonPath("$.role").value("Admin"))
                    .andExpect(status().isCreated());
        }
        catch (Exception e) {
            assertEquals("The username cannot be empty!", e.getCause().getMessage());
        }
    }

    @Test
    void testAddUserSuccessfully() throws Exception {
        String requestBody = "{\"username\": \"Wei\", \"password\": \"password\", \"role\": \"Admin\"}";
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(null);

        when(passwordEncoder.encode(any())).thenReturn(testUser.getPassword());

        when(userDAO.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testAddUserUsernameAlreadyExists() {
        try {
            String requestBody = "{\"username\": \"Wei\", \"password\": \"password\", \"role\": \"Admin\"}";
            when(userDAO.findByUsername(testUser.getUsername())).thenReturn(testUser);

            mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));
        } catch (Exception e) {
            assertEquals("This username already exists!", e.getCause().getMessage());
        }
    }
}
