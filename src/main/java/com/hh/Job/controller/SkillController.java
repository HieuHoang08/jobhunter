package com.hh.Job.controller;


import com.hh.Job.domain.Skill;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.service.SkillService;

import com.hh.Job.util.annotation.APImessage;
import com.hh.Job.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {

        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @APImessage("Create a skill")
    public ResponseEntity<Skill> create(@Valid @RequestBody Skill skill)throws IdInvalidException {
        if(skill.getName() != null && this.skillService.isNameExist(skill.getName())){
            throw new IdInvalidException("Skill name = " + skill.getName() + " da ton tai ");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.createSkill(skill));
    }

    @PutMapping("/skills")
    @APImessage("Update a skill")
    public ResponseEntity<Skill> update(@Valid @RequestBody Skill skill)throws IdInvalidException {
        Skill currentSkill = this.skillService.fetchSkillById(skill.getId());
        if(currentSkill == null){
            throw new IdInvalidException("Skill id = " + skill.getId() + " khong ton tai ");
        }
        if(skill.getName() != null && this.skillService.isNameExist(skill.getName())){
            throw new IdInvalidException("Skill name = " + skill.getName() + " da ton tai ");
        }
        currentSkill.setName(skill.getName());
        return ResponseEntity.ok().body(this.skillService.updateSkill(skill));
    }


    @GetMapping("/skills")
    @APImessage("Fetch a skill")
    public ResponseEntity<ResultPaginationDTO> getAll(
            @Filter Specification<Skill> spec,
            Pageable pageable  ) {


        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.fetchAllSkills(spec,pageable));
    }

    @DeleteMapping("/skills/{id}")
    @APImessage("Delete a skill")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws IdInvalidException {
        Skill currentSkill = this.skillService.fetchSkillById(id);
        if(currentSkill == null){
            throw new IdInvalidException("Skill id = " + id + " khong ton tai ");
        }
        this.skillService.deleteSkill(id);
        return ResponseEntity.ok().body(null);
    }

}
