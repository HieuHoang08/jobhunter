package com.hh.Job.controller;


import com.hh.Job.domain.User;
import com.hh.Job.domain.dto.ResultPaginationDTO;
import com.hh.Job.service.UserService;
import com.hh.Job.util.annotation.APImessage;
import com.hh.Job.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
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

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User h2User = this.userService.updateUser(user);
        return ResponseEntity.ok(h2User);
    }


    // phan trang voi pageAble
//    @GetMapping("/users")
//    public ResponseEntity<ResultPaginationDTO> getAllUsers(
//            @RequestParam ("current") Optional<String> currentOptional,
//            @RequestParam("pageSize") Optional<String> pageSizeOptional
//            ){
//
//        String sCurrentPage = currentOptional.isPresent() ? currentOptional.get() : "";
//        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
//
//        int crr = Integer.parseInt(sCurrentPage);
//        int page = Integer.parseInt(sPageSize);
//        Pageable pageable = PageRequest.of(crr - 1, page);
//        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(pageable));
//    }

   // phan trang specification(filter)
    @GetMapping("/users")
    @APImessage("fetch all user")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser( spec,  pageable));
    }
}
