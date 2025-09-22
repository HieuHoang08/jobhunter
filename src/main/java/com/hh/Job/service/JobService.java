package com.hh.Job.service;


import com.hh.Job.domain.Job;
import com.hh.Job.domain.Skill;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.domain.response.job.ResCreateJobDTO;
import com.hh.Job.repository.JobRepository;
import com.hh.Job.repository.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository  jobRepository ;
    private final SkillRepository skillRepository;
    public JobService(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    public ResCreateJobDTO createJob(Job job){
        if(job.getSkills() != null){
            List<Long> reqSkills = job.getSkills()
                    .stream().map(skill -> skill.getId())
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findAllByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        Job crrentJob = jobRepository.save(job);

        ResCreateJobDTO resCreateJobDTO = new ResCreateJobDTO();
        resCreateJobDTO.setId(crrentJob.getId());
        resCreateJobDTO.setName(crrentJob.getName());
        resCreateJobDTO.setSalary(crrentJob.getSalary());
        resCreateJobDTO.setQuantity(crrentJob.getQuantity());
        resCreateJobDTO.setLocation(crrentJob.getLocation());
        resCreateJobDTO.setLevel(crrentJob.getLevel());
        resCreateJobDTO.setStartDate(crrentJob.getStartDate());
        resCreateJobDTO.setEndDate(crrentJob.getEndDate());
        resCreateJobDTO.setCreatedAt(crrentJob.getCreatedAt());
        resCreateJobDTO.setCreatedBy(crrentJob.getCreatedBy());
        resCreateJobDTO.setActive(crrentJob.isActive());

        if(crrentJob.getSkills() != null){
            List<String> skills = crrentJob.getSkills()
                    .stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            resCreateJobDTO.setSkills(skills);
        }

        return resCreateJobDTO;
    }

    public ResCreateJobDTO updateJob(Job job){
        if(job.getSkills() != null){
            List<Long> reqSkills = job.getSkills()
                    .stream().map(skill -> skill.getId())
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findAllByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }
        Job crrentJob = jobRepository.save(job);
        ResCreateJobDTO resCreateJobDTO = new ResCreateJobDTO();
        resCreateJobDTO.setId(crrentJob.getId());
        resCreateJobDTO.setName(crrentJob.getName());
        resCreateJobDTO.setSalary(crrentJob.getSalary());
        resCreateJobDTO.setQuantity(crrentJob.getQuantity());
        resCreateJobDTO.setLocation(crrentJob.getLocation());
        resCreateJobDTO.setLevel(crrentJob.getLevel());
        resCreateJobDTO.setStartDate(crrentJob.getStartDate());
        resCreateJobDTO.setEndDate(crrentJob.getEndDate());
        resCreateJobDTO.setCreatedAt(crrentJob.getCreatedAt());
        resCreateJobDTO.setCreatedBy(crrentJob.getCreatedBy());
        resCreateJobDTO.setActive(crrentJob.isActive());
        if(crrentJob.getSkills() != null){

            List<String> skills = crrentJob.getSkills()
                    .stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            resCreateJobDTO.setSkills(skills);
        }
        return resCreateJobDTO;
    }

    public Optional<Job> fetchJobById(Long id) {

        return jobRepository.findById(id);
    }

    public void delete(long id) {
        this.jobRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAll(Specification<Job> spec, Pageable pageable){
        Page<Job> pageUser = this.jobRepository.findAll(spec, pageable);
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
}
