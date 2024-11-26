package com.revature.services;
import com.revature.daos.SongDAO;
import com.revature.daos.PlaylistDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Playlist;
import com.revature.models.Song;
import com.revature.models.User;
import com.revature.models.dtos.IncomingPlaylistDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistService {

    private PlaylistDAO playlistDAO;
    private UserDAO userDAO;
    private SongDAO songDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistService.class);

    @Autowired
    public PlaylistService(PlaylistDAO playlistDAO, UserDAO userDAO, SongDAO songDAO) {
        this.playlistDAO = playlistDAO;
        this.userDAO = userDAO;
        this.songDAO = songDAO;
    }

    public Playlist CreatePlaylist(IncomingPlaylistDTO playlist) {
        if(playlist.getPlaylistName() == null || playlist.getPlaylistName().isBlank()) {
            throw new IllegalArgumentException("The song name cannot be empty!");
        }
        else {
            Playlist newPlaylist = new Playlist(0, playlist.getPlaylistName(), playlist.isPublic(), null, null);
            UUID id = playlist.getUserId();
            Optional<User> user = userDAO.findByUserId(id);
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

    public Playlist addUser(int id, UUID uuid) {
        Optional<Playlist> playlist = playlistDAO.findById(id);
        Optional<User> user = userDAO.findByUserId(uuid);
        if(playlist.isEmpty() || user.isEmpty()) {
            throw new IllegalArgumentException("Cannot find user or playlist id!");
        }

        Set<User> userSet = playlist.get().getUserList();
        userSet.add(user.get());
        playlistDAO.save(playlist.get());
        return playlist.get();
    }

    @Transactional
    public String addSongToPlayList(int id, int songName) {
        Optional<Song> song = songDAO.findById(songName);
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty() || song.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist or or song id!");
        }
        Set<Song> songList = playlist.get().getSongList();
        songList.add(song.get());
        playlist.get().setSongList(songList);
        System.out.println("hello: testing");
        System.out.println("ending testing");

        playlistDAO.save(playlist.get());
        return song + " has been added to playlist " + playlist.get().getPlaylistName();
    }

    public Set<Song> getAllSongsById(int id) {
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist id!");
        }
        return new HashSet<>(playlist.get().getSongList());
    }

    public Set<Song> getSongsInPlaylistByUserId(UUID id) {
        Optional<User> user = userDAO.findByUserId(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user cannot be found!");
        }
        Set<Playlist> playlist = user.get().getPlaylistList();
        List<Integer> ids = new ArrayList<>();
        for(Playlist i: playlist) {
            ids.add(i.getPlaylistId());
        }
        HashSet<Song> songSet = new HashSet<>();
        for(Integer i: ids) {
            Optional<Playlist> play = playlistDAO.findById(i);
            songSet.addAll(play.get().getSongList());
        }
        return songSet;
    }

    public String deletePlaylist(int id) {
        Optional<Playlist> playlist = playlistDAO.findById(id);
        if(playlist.isEmpty()) {
            throw new IllegalArgumentException("Cannot find playlist ID");
        }
        playlistDAO.deleteById(id);
        return "Playlist ID: " + id + " has been deleted!";
    }

}
