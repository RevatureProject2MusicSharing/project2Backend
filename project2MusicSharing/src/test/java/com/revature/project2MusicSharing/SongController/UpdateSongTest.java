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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UpdateSongTest {

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
    public void updateSongNotFound() throws Exception {
        IncomingSongDTO updatedSong = new IncomingSongDTO(
                "Happy Birthday",
                "https://www.youtube.com/watch?v=5u4xTa3LR2U",
                "Family",
                "Person",
                1);

        String songJson = objectMapper.writeValueAsString(updatedSong);

        mockMvc.perform(put("/songs/100")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateSongSuccess() throws Exception {
        IncomingSongDTO updatedSong = new IncomingSongDTO(
                "Happy Birthday Updated",
                "https://www.youtube.com/watch?v=5u4xTa3LR2U",
                "Family",
                "Person",
                1);

        String songJson = objectMapper.writeValueAsString(updatedSong);

        mockMvc.perform(put("/songs/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(status().isOk());

    }

    @Test
    public void updateSongWithInvalidToken() throws Exception {
        IncomingSongDTO updatedSong = new IncomingSongDTO(
                "Happy Birthday Updated",
                "https://www.youtube.com/watch?v=5u4xTa3LR2U",
                "Family",
                "Person",
                1);

        String songJson = objectMapper.writeValueAsString(updatedSong);

        String invalidToken = "invalid.token.structure";

        mockMvc.perform(put("/songs/1")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songJson))
                .andExpect(status().isForbidden());
    }
}
