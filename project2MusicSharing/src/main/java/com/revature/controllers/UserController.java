package com.revature.controllers;

import com.revature.models.dtos.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<OutgoingUserDTO> registerUser(User user) {
        return ResponseEntity.ok().body(userService.addUser(user));
    }

    @PatchMapping("/{id}")
    ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody String newRole) {
        return ResponseEntity.ok().body(userService.updateRole(id, newRole));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }
}
