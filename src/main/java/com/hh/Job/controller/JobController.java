package com.hh.Job.controller;


import com.hh.Job.service.JobService;
import com.hh.Job.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1")
public class JobController {

    private final JobService jobService;

    public JobController( JobService jobService) {
        this.jobService = jobService;
    }
}
