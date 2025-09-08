package com.hh.Job.service;


import com.hh.Job.domain.User;
import com.hh.Job.domain.dto.Meta;
import com.hh.Job.domain.dto.ResultPaginationDTO;
import com.hh.Job.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

//    public List<User> fetchAllUsers(Pageable pageable) {
//        Page<User> pageUser = this.userRepository.findAll(pageable);
//        return pageUser.getContent();
//    }

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
// phan trang theo pageable
//    public ResultPaginationDTO fetchAllUser(Pageable pageable) {
//        Page<User> pageUser = this.userRepository.findAll(pageable);
//        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
//        Meta meta = new Meta();
//
//        meta.setPage(pageUser.getNumber() + 1);
//        meta.setPageSize(pageUser.getSize());
//        meta.setTotal(pageUser.getTotalElements());
//        meta.setPages(pageUser.getTotalPages());
//
//        resultPaginationDTO.setMeta(meta);
//        resultPaginationDTO.setResult(pageUser.getContent());
//        return resultPaginationDTO;
//    }

    // phan trang theo specification(filter)

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec,pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(pageUser.getContent());

        return resultPaginationDTO;
    }
}
