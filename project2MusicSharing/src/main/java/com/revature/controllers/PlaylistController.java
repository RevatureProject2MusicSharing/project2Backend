package com.revature.controllers;

import com.revature.models.Playlist;
import com.revature.models.Song;
import com.revature.models.dtos.IncomingPlaylistDTO;
import com.revature.services.PlaylistService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/playlists")
public class PlaylistController {
    PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    ResponseEntity<Playlist> CreatePlaylist(@RequestBody IncomingPlaylistDTO playlist) {
        return ResponseEntity.status(202).body(playlistService.CreatePlaylist(playlist));
    }

    @PatchMapping("/{id}")
    ResponseEntity<String> addSong(@PathVariable int id, @RequestBody String name) {
        return ResponseEntity.ok().body(playlistService.addSongToPlayList(id, name));
    }

    @GetMapping("/{id}")
    ResponseEntity<Set<Playlist>> getAllSongsinUser(@PathVariable int id) {
        return ResponseEntity.ok().body(playlistService.getSongsInPlaylistByUserId(id));
    }

    @GetMapping("/user/{id}")
    ResponseEntity<Set<Song>> getSongsById(@PathVariable int id) {
        return ResponseEntity.ok().body(playlistService.getAllSongsById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePlaylist(@PathVariable int id) {
        return ResponseEntity.ok().body(playlistService.deletePlaylist(id));
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}