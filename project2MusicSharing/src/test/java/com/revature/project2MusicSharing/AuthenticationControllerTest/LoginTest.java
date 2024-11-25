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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testLoginPass() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String loginJson = "{\"username\": \"Wei\", \"password\": \"password\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(loginJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testLoginFail() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String loginJson = "{\"username\": \"Wei\", \"password\": \"\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(loginJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
