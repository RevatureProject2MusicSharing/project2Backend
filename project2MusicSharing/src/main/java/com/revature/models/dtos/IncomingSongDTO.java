package com.revature.models.dtos;


import com.revature.models.Song;

public class IncomingSongDTO {

    private String songName;
    private String youtubeLink;
    private String genre;
    private int playlistId;


    public IncomingSongDTO() {
    }

    public IncomingSongDTO(String songName, String youtubeLink, String genre, int playlistId) {
        this.songName = songName;
        this.youtubeLink = youtubeLink;
        this.genre = genre;
        this.playlistId = playlistId;

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

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toString() {
        return "IncomingSongDTO{" +
                "songName='" + songName + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", genre='" + genre + '\'' +
                ", playlistId=" + playlistId +
                '}';
    }
}
