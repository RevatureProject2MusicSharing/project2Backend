package com.revature.project2MusicSharing.SongController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.dtos.IncomingSongDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RegisterNewSong {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void loginAndGetToken() throws Exception {
        String loginJson = "{ \"username\": \"Wei\", \"password\": \"password\" }";

        String response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response);
        token = jsonResponse.get("jwt").asText();
    }

    @Test
    public void registerNewSong() throws Exception {
        IncomingSongDTO newSong = new IncomingSongDTO("Happy Birthday",
                "https://www.youtube.com/watch?v=5u4xTa3LR2U",
                "Family", "Person", 1);

        String songJson = objectMapper.writeValueAsString(newSong);

        mockMvc.perform(post("/songs")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerNewSongWithExpiredToken() throws Exception {
        IncomingSongDTO newSong = new IncomingSongDTO("Invalid Token",
                "https://www.youtube.com/watch?v=5u4xTa3LR2U",
                "Family", "Person", 1);

        String songJson = objectMapper.writeValueAsString(newSong);

        String invalidToken = "invalid.token.structure";

        mockMvc.perform(post("/songs")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(status().isForbidden());
    }
}
