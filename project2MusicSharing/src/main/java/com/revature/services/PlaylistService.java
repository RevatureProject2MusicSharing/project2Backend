package com.revature.services;
import com.revature.DAOs.SongDAO;
import com.revature.DAOs.UserDAO;
import com.revature.DAOs.PlaylistDAO;

import com.revature.models.Playlist;
import com.revature.models.Song;
import com.revature.models.User;
import com.revature.models.dtos.IncomingPlaylistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistService {

    private PlaylistDAO playlistDAO;
    private UserDAO userDAO;
    private SongDAO songDAO;

    @Autowired
    public PlaylistService(PlaylistDAO playlistDAODAO, UserDAO userDAO, SongDAO songDAO) {
        this.playlistDAO = playlistDAODAO;
        this.userDAO = userDAO;
        this.songDAO = songDAO;
    }

    public Playlist CreatePlaylist(IncomingPlaylistDTO playlist) {
        if(playlist.getPlaylistName() == null || playlist.getPlaylistName().isBlank()) {
            throw new IllegalArgumentException("The song name cannot be empty!");
        }
        else {
            Playlist newPlaylist = new Playlist(0, playlist.getPlaylistName(), playlist.isPublic(), null);
            int id = playlist.getUserId();
            Optional<User> user = userDAO.findById(id);
            if(user.isEmpty()) {
                throw new IllegalArgumentException("This user does not exist!");
            }
            Set<User> users = new HashSet<>();
            users.add(user.get());
            newPlaylist.setUserList(users);
            playlistDAO.save(newPlaylist);
            return newPlaylist;
        }
    }

    public String addSongToPlayList(int id, String songName) {
        Song song = songDAO.findBySongName(songName);
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist id!");
        }
        Set<Song> songList = playlist.get().getSongList();
        Set<Song> newSongList = new HashSet<>(songList);
        newSongList.add(song);
        return song + " has been added to playlist " + playlist.get().getPlaylistName();
    }

    public Set<Song> getAllSongsById(int id) {
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist id!");
        }
        return new HashSet<>(playlist.get().getSongList());
    }

    public Set<Playlist> getSongsInPlaylistByUserId(int id) {
        Optional<User> user = userDAO.findById(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user cannot be found!");
        }
        return user.get().getPlaylistList();

        //not yet done
    }

    public String deletePlaylist(int id) {
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist ID");
        }
        userDAO.deleteById(id);
        return "Playlist ID: " + id + " has been deleted!";
    }




}
