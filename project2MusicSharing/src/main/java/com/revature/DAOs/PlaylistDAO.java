package com.revature.daos;

import com.revature.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistDAO extends JpaRepository <Playlist, Integer> {


    // Method to add a song to a Playlist

    Playlist addSongtoPlaylist (String song);

}
