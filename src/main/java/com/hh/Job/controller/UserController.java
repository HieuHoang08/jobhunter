package com.hh.Job.controller;


import com.hh.Job.domain.User;
import com.hh.Job.service.UserService;
import com.hh.Job.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,  PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User hhUser = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(hhUser);
    }

    @DeleteMapping("/users/{id}")
        public ResponseEntity<String> deleteUser( @PathVariable("id") Long id)
            throws IdInvalidException {

        if(id >= 1500){
            throw new IdInvalidException("Id khong lon hon 1500");
        }

        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("hhUser");
        //return ResponseEntity.ok("hh");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User fetch = this.userService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetch);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUsers());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User h2User = this.userService.updateUser(user);
        return ResponseEntity.ok(h2User);
    }
}
