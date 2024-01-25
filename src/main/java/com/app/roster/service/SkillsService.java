package com.app.roster.service;

import com.app.roster.dao.SkillsMapper;
import com.app.roster.dto.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 기술 정보를 관리하는 서비스 클래스입니다.
 */
@Service
public class SkillsService {

    private final SkillsMapper skillsMapper;

    @Autowired
    public SkillsService(SkillsMapper skillsMapper) {
        this.skillsMapper = skillsMapper;
    }

    /**
     * 모든 기술 정보를 조회합니다.
     *
     * @return 모든 기술 정보 목록
     */
    public List<Skill> getAllSkills() {
        return skillsMapper.getAllSkills();
    }

    /**
     * 기술 정보를 추가합니다.
     *
     * @param skills 추가할 기술 정보 목록
     */
    @Transactional
    public void addSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.addSkill(skill);
        }
    }

    /**
     * 기술 정보를 업데이트합니다.
     *
     * @param skills 업데이트할 기술 정보 목록
     */
    @Transactional
    public void updateSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.updateSkill(skill);
        }
    }

    /**
     * 기술 정보를 삭제합니다.
     *
     * @param skills 삭제할 기술 정보 목록
     */
    @Transactional
    public void deleteSkill(List<Skill> skills) {
        for (Skill skill : skills) {
            skillsMapper.deleteSkill(skill);
        }
    }
}
