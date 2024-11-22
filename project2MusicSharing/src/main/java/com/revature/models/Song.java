package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Table
@Entity(name = "songs")
public class Song {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int songId;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String youtubeLink;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String artistName;

    @ManyToMany
    @JoinTable(
        name = "song_playlist",
        joinColumns = @JoinColumn(name = "songId"),
        inverseJoinColumns = @JoinColumn(name = "playlistId")
    )
    @JsonIgnore
    private List<Playlist> playlistList;

    public Song() {
    }

    public Song(int i, String songName, String youtubeLink, String genre, String artistName, List<Playlist> playlistList) {
        this.songId = songId;
        this.songName = songName;
        this.youtubeLink = youtubeLink;
        this.genre = genre;
        this.artistName = artistName;
        this.playlistList = playlistList;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<Playlist> getPlaylistList() {
        return playlistList;
    }

    public void setPlaylistList(List<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", genre='" + genre + '\'' +
                ", playlistList=" + playlistList +
                '}';
    }
}
