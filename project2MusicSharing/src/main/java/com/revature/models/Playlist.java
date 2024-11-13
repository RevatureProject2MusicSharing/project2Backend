package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "playlist")
@Component
public class Playlist {

    @Id
    @GeneratedValue
    private int playlistId;


    @Column(nullable = false, unique = true)
    private String playlistName;


    @Column(nullable = true)
    private boolean isPublic;

    //boilerplate code-----------------------


    public Playlist() {
    }

    public Playlist(int playlistId, String playlistName, boolean isPublic) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.isPublic = isPublic;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}

