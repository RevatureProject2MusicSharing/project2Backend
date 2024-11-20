package com.revature.controllers;

import com.revature.models.User;
import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.services.UserService;
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

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<OutgoingUserDTO> registerUser(@RequestBody User user) {
        OutgoingUserDTO outgoingUser = userService.addUser(user);
        return ResponseEntity.status(201).body(outgoingUser);
    }

    @PostMapping("/login")
    ResponseEntity<OutgoingUserDTO> loginUser(@RequestBody User user) {
        OutgoingUserDTO outgoingUser = userService.loginUser(user);
        return ResponseEntity.status(200).body(outgoingUser);
    }
}
