package com.revature.services;
import com.revature.daos.SongDAO;
import com.revature.daos.UserDAO;
import com.revature.daos.PlaylistDAO;
import com.revature.models.Playlist;
import com.revature.models.dtos.IncomingPlaylistDTO;
import com.revature.models.dtos.IncomingSongDTO;
import com.revature.models.Song;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private PlaylistDAO pDAO;
    private UserDAO uDAO;
    private SongDAO sDAO;

    @Autowired
    public PlaylistService(PlaylistDAO pDAO, UserDAO uDAO,SongDAO sDAO){
        this.sDAO = sDAO;
        this.pDAO = pDAO;
        this.uDAO = uDAO;
    }
    public Playlist addPlayList(IncomingPlaylistDTO playlistDTO){

        Playlist newPlaylist = new Playlist(0, playlistDTO.getPlaylistName(), playlistDTO.isPublic());
        pDAO.save(newPlaylist);
        return newPlaylist;
        /*Trying to get this functionality installed
        * later unsure though how to create a get method
        * on this for the get User ID especially since
        * user field in method parameters takes a list
        * Many to Many is confusing me */
     //    Optional<User> u = uDAO.findById(playlistDTO.get);

    }




}
