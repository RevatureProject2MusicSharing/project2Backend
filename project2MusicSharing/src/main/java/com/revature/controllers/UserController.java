package com.revature.controllers;

import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PatchMapping("/{id}")
    ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody String newRole) {
        return ResponseEntity.ok().body(userService.updateRole(id, newRole));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @GetMapping
    ResponseEntity<List<OutgoingUserDTO>> allUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
