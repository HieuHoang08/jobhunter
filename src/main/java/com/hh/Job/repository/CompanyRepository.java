package com.hh.Job.repository;

import com.hh.Job.domain.Company;
import com.hh.Job.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>,
        JpaSpecificationExecutor<Company> {


}
