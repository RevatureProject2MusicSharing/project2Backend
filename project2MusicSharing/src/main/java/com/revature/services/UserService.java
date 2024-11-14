package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return new OutgoingUserDTO(user.getUserId(), user.getUsername(), user.getRole());
    }

    public String updateRole(int id, String newRole) {
        Optional<User> user = userDAO.findById(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user does not exist!");
        }
        user.get().setRole(newRole);
        userDAO.save(user.get());
        if(newRole.equals("admin") || newRole.equals("Admin")) {
            return "User ID: " + id + " has promoted to an admin";
        }
        else if(newRole.equals("user") || newRole.equals("User")) {
            return "User ID" + id + " has been denoted to a user";
        }
        else {
            throw new IllegalArgumentException("This is not a valid role type!");
        }

    }

    public String deleteUser(int id) {
        Optional<User> user = userDAO.findById(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user does not exist!");
        }
        userDAO.deleteById(id);
        return "User ID: " + id + " has been deleted!";
    }
}
