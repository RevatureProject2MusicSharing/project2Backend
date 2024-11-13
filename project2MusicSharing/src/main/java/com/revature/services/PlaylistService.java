package com.revature.services;
import com.revature.daos.PlaylistDAO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
