package com.revature.services;

import com.revature.daos.SongDAO;
import com.revature.daos.PlaylistDAO;
import com.revature.models.Song;
import com.revature.models.dtos.IncomingSongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private PlaylistDAO playlistDAO;
    private SongDAO songDAO;

    @Autowired
    public SongService(SongDAO songDAO, PlaylistDAO playlistDAO) {
        this.songDAO = songDAO;
        this.playlistDAO = playlistDAO;
    }

    public Song addSong(IncomingSongDTO incomingSongDTO) {
        Song newSong = new Song(incomingSongDTO.getSongName(),
                incomingSongDTO.getYoutubeLink(), incomingSongDTO.getGenre(), incomingSongDTO.getArtistName(), null);
        songDAO.save(newSong);
        return newSong;
    }

    public List<Song> getAllSongs() { return songDAO.findAll(); }

    public List<Song> getSongsByPlaylistId(int playlistId) {
        return songDAO.findByPlaylistList_PlaylistId(playlistId);
    }

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

    public Song updateSong(int id, IncomingSongDTO incomingSongDTO) {
        Song updatedSong = songDAO.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No song found with id: " + id));
        updatedSong.setSongName(incomingSongDTO.getSongName());
        updatedSong.setYoutubeLink(incomingSongDTO.getYoutubeLink());
        updatedSong.setGenre(incomingSongDTO.getGenre());
        updatedSong.setArtistName(incomingSongDTO.getArtistName());
        songDAO.save(updatedSong);
        return updatedSong;
    }



}
