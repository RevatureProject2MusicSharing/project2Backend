package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.utils.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;

    //Adding a password encoder to encrypted passwords
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManger;
    private final JwtTokenUtil jwtTokenUtil;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManger,
        JwtTokenUtil jwtTokenUtil) {

        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManger = authenticationManger;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User addUser(User user) {
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

        log.info("User " + user.getUsername() + " has been added!");
        return userDAO.save(user);
    }

    public String updateRole(UUID id, String newRole) {
        Optional<User> user = userDAO.findByUserId(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user does not exist!");
        }
        user.get().setRole(newRole);
        userDAO.save(user.get());
        log.info("User ID: " + id + " has been updated!");
        if(newRole.equals("admin") || newRole.equals("Admin")) {
            return "User ID: " + id + " has promoted to an admin";
        }
        else if(newRole.equals("user") || newRole.equals("User")) {
            return "User ID: " + id + " has been denoted to a user";
        }
        else {
            System.out.println(newRole);
            throw new IllegalArgumentException("This is not a valid role type!");
        }

    }

    public String deleteUser(UUID id) {
        Optional<User> user = userDAO.findByUserId(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("This user does not exist!");
        }
        userDAO.delete(user.get());
        return "User ID: " + id + " has been deleted!";
    }

    public List<OutgoingUserDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        List<OutgoingUserDTO> outgoingUserList = new ArrayList<>();
        for(User user: users) {
            OutgoingUserDTO outgoingUser = new OutgoingUserDTO(
              
              
              
              
              UserId(), user.getUsername(), user.getRole());
            outgoingUserList.add(outgoingUser);
        }
        return outgoingUserList;
    }

    public OutgoingUserDTO loginUser(User user) {
        Authentication authentication = authenticationManger.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User verifedUser = userDAO.findByUsername(user.getUsername());
        log.info("User " + user.getUsername() + " has logged in!");
        return new OutgoingUserDTO(verifedUser.getUserId(), verifedUser.getUsername(), verifedUser.getRole(),jwtTokenUtil.generateAccessToken(verifedUser));
    }
}
