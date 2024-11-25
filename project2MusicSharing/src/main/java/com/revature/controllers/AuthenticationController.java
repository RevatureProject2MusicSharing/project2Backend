package com.revature.controllers;

import com.revature.models.User;
import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.services.UserService;
import com.revature.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    ResponseEntity<OutgoingUserDTO> registerUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        OutgoingUserDTO outgoingUser = new OutgoingUserDTO(addedUser.getUserId(), addedUser.getUsername(), addedUser.getRole(),jwtTokenUtil.generateAccessToken(addedUser));
        return ResponseEntity.status(201).body(outgoingUser);
    }

    @PostMapping("/login")
    ResponseEntity<OutgoingUserDTO> loginUser(@RequestBody User user) {
        OutgoingUserDTO outgoingUser = userService.loginUser(user);
        return ResponseEntity.status(200).body(outgoingUser);
    }
}
