package com.hh.Job.service;


import org.springframework.stereotype.Service;

@Service
public class JobService {
    private final JobService jobService;

    public JobService(JobService jobService) {
        this.jobService = jobService;
    }
}
