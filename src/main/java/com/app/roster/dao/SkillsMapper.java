package com.app.roster.dao;

import com.app.roster.dto.Skill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkillsMapper {
    List<Skill> getAllSkills();

    void addSkill(Skill skill);

    void updateSkill(Skill skill);

    void deleteSkill(Skill skill);
}
