package com.revature.controllers;

import com.revature.models.dtos.IncomingPlaylistDTO;
import com.revature.models.Playlist;
import com.revature.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* 11/14/24 Post working but without user and playlist
* options needing to be input. Need to make functionality where
* user who is logged in is automatially the owner of the playlist
* and then we can add the songs and other users to after  */


@RestController
@RequestMapping("/playlists")
@CrossOrigin
public class PlaylistController {

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController (PlaylistService playlistService){
        this.playlistService = playlistService;
    }


    // Create a Playlist
    @PostMapping
    public ResponseEntity<Playlist> insertPlaylist(@RequestBody IncomingPlaylistDTO playlistDTO){
        Playlist p1 = playlistService.addPlayList(playlistDTO);

        return ResponseEntity.status(201).body(p1);

    }

}
