package com.revature.models.dtos;
import java.util.Set;

import com.revature.models.Song;
import com.revature.models.User;


/* 11/14/24 So DTO on this has maybe some functionality
* issues due to the many to many tables
* being defined in weird ways. We have Sets
* as two of the attributes because the playlist
* has multiple songs and users. May create issues
* if so feel free to edit  */




public class IncomingPlaylistDTO {

    private String playlistName;
    private boolean isPublic;
    private Set<Song> songList;
    private Set<User> userList;


    public IncomingPlaylistDTO() {
    }

    public IncomingPlaylistDTO(String playlistName, boolean isPublic, Set<Song> songList, Set<User> userList) {
        this.playlistName = playlistName;
        this.isPublic = isPublic;
        this.songList = songList;
        this.userList = userList;
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

    public Set<Song> getSongList() {
        return songList;
    }

    public void setSongList(Set<Song> songList) {
        this.songList = songList;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "IncomingPlaylistDTO{" +
                "playlistName='" + playlistName + '\'' +
                ", isPublic=" + isPublic +
                ", songList=" + songList +
                ", userList=" + userList +
                '}';
    }
}
