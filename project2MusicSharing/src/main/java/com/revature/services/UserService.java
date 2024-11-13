package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public OutgoingUserDTO addUser(User user) {
        if(user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("The username cannot be empty!");
        }
        else if(user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }
        userDAO.save(user);

        return new OutgoingUserDTO(user.getUserId(), user.getUsername(), user.getPassword());
    }
}
