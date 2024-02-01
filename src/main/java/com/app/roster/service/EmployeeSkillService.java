package com.app.roster.service;

import com.app.roster.dao.EmployeeSkillMapper;
import com.app.roster.dto.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 직원의 스킬 정보를 관리하는 서비스 클래스입니다.
 */
@Service
public class EmployeeSkillService {

    @Autowired
    private EmployeeSkillMapper employeeSkillMapper;

    /**
     * 모든 직원의 스킬 정보를 조회합니다.
     *
     * @return 모든 직원의 스킬 목록
     */
    public List<EmployeeSkill> getAllEmployeeSkills() {
        return employeeSkillMapper.getAllEmployeeSkills();
    }

    /**
     * 직원의 스킬을 추가합니다.
     *
     * @param employeeSkills 추가할 직원의 스킬 목록
     */
    public void addEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.addEmployeeSkill(employeeSkill);
        }
    }

    /**
     * 직원의 스킬 정보를 업데이트합니다.
     *
     * @param employeeSkills 업데이트할 직원의 스킬 목록
     */
    public void updateEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.updateEmployeeSkill(employeeSkill);
        }
    }

    /**
     * 직원의 스킬을 삭제합니다.
     *
     * @param employeeSkills 삭제할 직원의 스킬 목록
     */
    public void deleteEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.deleteEmployeeSkill(employeeSkill);
        }
    }

    public boolean existsEmployeeSkill(EmployeeSkill employeeSkill) {
        return employeeSkillMapper.existsEmployeeSkill(employeeSkill);
    }
}
