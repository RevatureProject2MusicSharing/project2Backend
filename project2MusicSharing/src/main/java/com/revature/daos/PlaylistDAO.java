package com.revature.DAOs;

import com.revature.models.Playlist;
import com.revature.models.Song;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PlaylistDAO extends JpaRepository <Playlist, Integer> {
    // Method to add a song to a Playlist

    //Not sure if this works
    //Playlist addSongtoPlaylist (String song);
}
