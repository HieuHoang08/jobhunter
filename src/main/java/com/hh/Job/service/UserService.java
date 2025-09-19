package com.hh.Job.service;


import com.hh.Job.domain.Company;
import com.hh.Job.domain.User;
import com.hh.Job.domain.response.ResCreateUserDTO;
import com.hh.Job.domain.response.ResUpdateUserDTO;
import com.hh.Job.domain.response.ResUserDTO;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;


    public UserService(UserRepository userRepository, CompanyService companyService) {

        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    public User handleCreateUser(User user) {
        // check company
        if(user.getCompany() != null){
            Optional<Company> companyOptional = this.companyService.findById(user.getCompany());
            user.setCompany(companyOptional.isPresent() ? companyOptional.get() : null);
        }

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

    public User handleUpdateUser(User user) {
        User crrUser = this.fetchUserById(user.getId());
        if (crrUser != null) {

            crrUser.setName(user.getName());
            crrUser.setPassword(user.getPassword());
            crrUser.setEmail(user.getEmail());

            // check company

            if(user.getCompany() != null) {
                Optional<Company> companyOptional = this.companyService.findById(user.getCompany());
                user.setCompany(companyOptional.isPresent() ? companyOptional.get() : null);
            }
            //update
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
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());

        resultPaginationDTO.setMeta(meta);

        List<ResUserDTO> listUser = pageUser.getContent()
                .stream()
                .map(item -> new ResUserDTO(
                        item.getId(),
                        item.getName(),
                        item.getEmail(),
                        item.getGender(),
                        item.getAddress(),
                        item.getAge(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        new ResUserDTO.CompanyUser(
                                item.getCompany() != null ? item.getCompany().getId() : 0,
                                item.getCompany() != null ? item.getCompany().getName() : null
                        )
                ))
                .collect(Collectors.toList());

        resultPaginationDTO.setResult(listUser);
        return resultPaginationDTO;
    }

    public boolean isEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToRestCreateUserDTO (User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        ResCreateUserDTO.CompanyUser com = new ResCreateUserDTO.CompanyUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        res.setAddress(user.getAddress());

        if(user.getCompany() != null){
            com.setId(user.getCompany().getId());
            com.setName(user.getCompany().getName());
            res.setCompany(com);
        }
        return res;
    }

    public ResUserDTO convertToRestUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        res.setAddress(user.getAddress());
        res.setUpdatedAt(user.getUpdatedAt());

        // map company nếu có
        if (user.getCompany() != null) {
            ResUserDTO.CompanyUser companyUser = new ResUserDTO.CompanyUser();
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompanyUser(companyUser);
        }

        return res;
    }


    public ResUpdateUserDTO convertToRestUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();

        // map company nếu có
        if (user.getCompany() != null) {
            ResUpdateUserDTO.CompanyUser com = new ResUpdateUserDTO.CompanyUser();
            com.setId(user.getCompany().getId());
            com.setName(user.getCompany().getName());
            res.setCompanyUser(com);
        }

        res.setId(user.getId());
        res.setName(user.getName());         // ✅ sửa lại cho khớp DTO
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setUpdatedAt(user.getUpdatedAt());

        return res;
    }


    public void updateUserToken(String username, String token) {
        User crrUser = this.handleGetUserByUsername(username);
        if (crrUser != null) {
            crrUser.setRefreshToken(token);
            this.userRepository.save(crrUser);
        }

    }


    public User getUserByRefreshTokenAndEmail(String refresh_token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(refresh_token, email);
    }
}

