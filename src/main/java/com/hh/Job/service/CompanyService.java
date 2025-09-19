package com.hh.Job.service;


import com.hh.Job.domain.Company;
import com.hh.Job.domain.User;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.repository.CompanyRepository;
import com.hh.Job.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }


    public Optional<Company> findById(Company company) {
        if (company == null || company.getId() == null) {
            return Optional.empty();
        }
        return companyRepository.findById(company.getId());
    }
//    public Optional<Company> findById(long id){
//        return this.companyRepository.findById(id);
//    }


    //phan trang vs pageable
//    public ResultPaginationDTO fetchAllCompany(Pageable pageable) {
//        Page<Company> pageUser = this.companyRepository.findAll(pageable);
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

    //phan trang vs filter(specification)
    public ResultPaginationDTO fetchAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageUser = this.companyRepository.findAll(spec,pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(pageUser.getTotalElements());
        meta.setPages(pageUser.getTotalPages());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(pageUser.getContent());
        return resultPaginationDTO;
    }

    public void handleDeleteCompany(Long id){
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            List<User> users = this.userRepository.findByCompany(company);
            this.userRepository.deleteAll(users);
        }

        this.companyRepository.deleteById(id);;
    }

    public Company handleUpdateCompany(Company company) {
        Optional<Company> optionalCompany = this.companyRepository.findById(company.getId());
        if (optionalCompany.isPresent()) {
            Company updateCompany = optionalCompany.get();
            updateCompany.setName(company.getName());
            updateCompany.setAddress(company.getAddress());
            updateCompany.setLogo(company.getLogo());
            updateCompany.setDescription(company.getDescription());
            return companyRepository.save(updateCompany);
        }
        return null;
    }
}
