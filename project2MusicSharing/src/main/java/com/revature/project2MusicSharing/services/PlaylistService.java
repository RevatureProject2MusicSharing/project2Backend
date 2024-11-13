package com.revature.project2MusicSharing.services;
import com.revature.project2MusicSharing.daos.PlaylistDAO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private PlaylistDAO pDAO;
    private UserDAO uDAO;

    @Autowired
    public PlaylistService(PlaylistDAO pDAO, UserDAO, uDAO){
        this.pDAO = pDAO;
        this.uDAO = uDAO;
    }

}
