package com.revature.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;



/* 11/14/24 Added many to many relationships for
* users and songs list so songs can be put onto playlist and
* users can ask for permissions. Old ToString is down at the
* bottom and may need to be changed possibly
* as well as the constructors
*  */

/* added true as the default for whether playlist
* is public or private*/


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
    private boolean isPublic = true;

    @ManyToMany(mappedBy = "playlistList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Song> songList;

    @ManyToMany
    @JoinTable(
        name = "playlist_user",
        joinColumns = @JoinColumn(name = "playlistId"),
        inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> userList;

    //boilerplate code-----------------------


    public Playlist() {
    }

    public Playlist(int playlistId, String playlistName, boolean isPublic, Set<Song> songList, Set<User> userList) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.isPublic = isPublic;
        this.songList = songList;
        this.userList = userList;
    }
// Made two NEW Constructors for the playlist and the
    // USer and Song List
    /* Not sure if it is needed */

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



//    @Override
//    public String toString() {
//        return "Playlist{" +
//                "playlistId=" + playlistId +
//                ", playlistName='" + playlistName + '\'' +
//                ", isPublic=" + isPublic +
//                '}';
//    }


    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", isPublic=" + isPublic +
                ", songList=" + songList +
                ", userList=" + userList +
                '}';
    }
}

