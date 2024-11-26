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
import java.util.UUID;

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

    @PatchMapping("/user/{id}/{uuid}")
    ResponseEntity<Playlist> addUser(@PathVariable int id, @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(playlistService.addUser(id, uuid));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Playlist> addSong(@PathVariable int id, @RequestBody int name) {
        return ResponseEntity.ok().body(playlistService.addSongToPlayList(id, name));
    }

    @GetMapping("/user/{id}")
    ResponseEntity<Set<Song>> getAllSongsinUser(@PathVariable UUID id) {
        return ResponseEntity.ok().body(playlistService.getSongsInPlaylistByUserId(id));
    }

    @GetMapping("/{id}")
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