package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;

    //Adding a password encoder to encrypted passwords
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManger;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManger,
        JwtTokenUtil jwtTokenUtil) {

        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManger = authenticationManger;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public OutgoingUserDTO addUser(User user) {
        if(user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("The username cannot be empty!");
        }
        else if(user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }

        //Password Encryption
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Use our password encoder autowired in SecurityConfig to
        if(userDAO.findByUsername(user.getUsername())!=null) {
            throw new IllegalArgumentException("This username already exists!");
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
            return "User ID: " + id + " has been denoted to a user";
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

    public List<OutgoingUserDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        List<OutgoingUserDTO> outgoingUserList = new ArrayList<>();
        for(User user: users) {
            OutgoingUserDTO outgoingUser = new OutgoingUserDTO(user.getUserId(), user.getUsername(), user.getRole());
            outgoingUserList.add(outgoingUser);
        }
        return outgoingUserList;
    }

    public String verifyUser(User user) {
        Authentication authentication = authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtTokenUtil.generateAccessToken(user);
        }
        else{
            return "failed";
        }
    }
}
