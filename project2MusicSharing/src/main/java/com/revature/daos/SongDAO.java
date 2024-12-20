package com.revature.daos;

import com.revature.models.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongDAO extends JpaRepository<Song, Integer> {
    @Query(value = "SELECT * FROM p2actual.songs ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Song> findRandomSong();
    Song findBySongName(String name);

    List<Song> findByPlaylistList_PlaylistId(int playlistId);
}