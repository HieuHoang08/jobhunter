package com.hh.Job.service;


import com.hh.Job.domain.User;
import com.hh.Job.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {

            return user.get();
        }
        return null;
    }

    public List<User> fetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User updateUser(User user) {
        User crrUser = this.fetchUserById(user.getId());
        if (crrUser != null) {

            crrUser.setUsername(user.getUsername());
            crrUser.setPassword(user.getPassword());
            crrUser.setEmail(user.getEmail());

            crrUser = this.userRepository.save(crrUser);
        }
        return crrUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
