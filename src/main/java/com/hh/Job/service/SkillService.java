package com.hh.Job.service;

import org.springframework.stereotype.Service;

@Service
public class SkillService {
    private final SkillService skillService;

    public SkillService(SkillService skillService) {
        this.skillService = skillService;
    }
}
