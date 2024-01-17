package com.app.roster.service;

import com.app.roster.dao.SkillsMapper;
import com.app.roster.dto.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillsService {

    private final SkillsMapper skillsMapper;

    @Autowired
    public SkillsService(SkillsMapper skillsMapper) {
        this.skillsMapper = skillsMapper;
    }

    public List<Skill> getAllSkills() {
        return skillsMapper.getAllSkills();
    }

    @Transactional
    public void addSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.addSkill(skill);
        }
    }

    @Transactional
    public void updateSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.updateSkill(skill);
        }
    }

    @Transactional
    public void deleteSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.deleteSkill(skill);
        }
    }
}