package com.hh.Job.controller;


import com.hh.Job.domain.Job;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.domain.response.job.ResCreateJobDTO;
import com.hh.Job.service.JobService;
import com.hh.Job.util.annotation.APImessage;
import com.hh.Job.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController

@RequestMapping("/api/v1")
public class JobController {

    private final JobService jobService;

    public JobController( JobService jobService) {

        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    @APImessage("create a job")
    public ResponseEntity<ResCreateJobDTO> create(@Valid @RequestBody Job job){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(job));
    }

    @PutMapping("/jobs")
    @APImessage("Update a job")
    public ResponseEntity<ResCreateJobDTO> update(@Valid @RequestBody Job job)throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(job.getId());
        if(!currentJob.isPresent()){
            throw new IdInvalidException( "Job not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobService.updateJob(job));
    }

    @DeleteMapping("/jobs/{id}")
    @APImessage("Delete a job")
    public ResponseEntity<ResCreateJobDTO> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if(!currentJob.isPresent()){
            throw new IdInvalidException( "Job not found");
        }
        this.jobService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/jobs/{id}")
    @APImessage("Get a job by id")
    public ResponseEntity<Job> getJobById(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = jobService.fetchJobById(id);
        if (currentJob.isEmpty()) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok(currentJob.get());
    }


    @GetMapping("/jobs")
    @APImessage("Get all job")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(@Filter Specification<Job> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.jobService.fetchAll(spec, pageable));
    }

}
