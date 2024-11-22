package com.revature.controllers;

import com.revature.models.Song;
import com.revature.models.dtos.IncomingSongDTO;
import com.revature.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;

@RestController
@RequestMapping("/songs")
@CrossOrigin
public class SongController {

    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> allSongs = songService.getAllSongs();
        return ResponseEntity.ok(allSongs);
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<Song>> getSongsByPlaylist(@PathVariable int playlistId) {
        List<Song> songs = songService.getSongsByPlaylistId(playlistId);
        return ResponseEntity.ok(songs);
    }

    @PostMapping
    public ResponseEntity<Song> registerSong(@RequestBody IncomingSongDTO incomingSongDTO) {
        Song song = songService.addSong(incomingSongDTO);
        return (ResponseEntity.status(201).body(song));
    }

    @GetMapping("/random")
    public ResponseEntity<Song> getRandomSong() {
        Song randomSong = songService.randomSongSelector();
        return ResponseEntity.ok(randomSong);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Song> deleteSongById(@PathVariable int songId) {
        return ResponseEntity.ok(songService.removeSongById(songId));
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Song> updateSongById
            (@PathVariable int songId, @RequestBody IncomingSongDTO incomingSongDTO) {
        return ResponseEntity.ok(songService.updateSong(songId, incomingSongDTO));
    }


    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
