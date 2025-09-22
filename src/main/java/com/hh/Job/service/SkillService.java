package com.hh.Job.service;

import com.hh.Job.domain.Skill;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.repository.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService {
    private final SkillRepository skillRepository;


    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public boolean isNameExist(String name){
        return this.skillRepository.existsByName(name);
    }

    public Skill createSkill(Skill skill){
        return this.skillRepository.save(skill);
    }

    public Skill updateSkill(Skill skill){
        return this.skillRepository.save(skill);
    }

    public Skill fetchSkillById(long id){
        Optional<Skill> skill = this.skillRepository.findById(id);
        if(skill.isPresent()){
            return skill.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllSkills(Specification<Skill> spec, Pageable pageable){
        Page<Skill> skillPage = this.skillRepository.findAll(spec,pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(skillPage.getTotalPages());
        mt.setTotal(skillPage.getTotalElements());
        rs.setMeta(mt);
        rs.setResult(skillPage.getContent());
        return rs;
    }

    public void deleteSkill(long id){
        // delete job (inside job_skill table)
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        Skill crrSkill = skillOptional.get();
        crrSkill.getJobs().forEach(job -> job.getSkills().remove(crrSkill));

        // delete skill
        this.skillRepository.delete(crrSkill);
    }
}
