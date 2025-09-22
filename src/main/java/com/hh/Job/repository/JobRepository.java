package com.hh.Job.repository;


import com.hh.Job.domain.Job;
import com.hh.Job.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long>,
        JpaSpecificationExecutor<Job> {
}
