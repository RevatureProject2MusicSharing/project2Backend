package com.revature.services;

import com.revature.DAOs.SongDAO;
import com.revature.models.Song;
import com.revature.models.dtos.IncomingSongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private SongDAO songDAO;
    //private PlaylistDAO playlistDAO;


    @Autowired
    public SongService(SongDAO songDAO) {
        this.songDAO = songDAO;
    }
    /*@Autowired
    public SongService(SongDAO songDAO, PlaylistDAO, playlistDAO) {
        this.songDAO = songDAO;
        this.playlistDAO = playlistDAO;
    }*/

    public Song addSong(IncomingSongDTO incomingSongDTO) {
        Song newSong = new Song(0, incomingSongDTO.getSongName(),
                incomingSongDTO.getYoutubeLink(), incomingSongDTO.getGenre(), null);
        songDAO.save(newSong);
        return newSong;
    }

    public List<Song> getAllSongs() { return songDAO.findAll(); }

    public Song removeSongById(int songId) {
        Song songToDelete = songDAO.findById(songId).orElseThrow(() ->
                new IllegalArgumentException("No song found with id: " + songId));

        songDAO.deleteById(songId);
        return songToDelete;
    }

    public Song randomSongSelector() {
        return songDAO.findRandomSong().orElseThrow(() ->
                new IllegalArgumentException("No songs available"));
    }

}
