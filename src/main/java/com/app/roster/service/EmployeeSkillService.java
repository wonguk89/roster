package com.app.roster.service;

import com.app.roster.dao.EmployeeSkillMapper;
import com.app.roster.dto.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSkillService {

    @Autowired
    private EmployeeSkillMapper employeeSkillMapper;

    public List<EmployeeSkill> getAllEmployeeSkills() {
        return employeeSkillMapper.getAllEmployeeSkills();
    }

    public void addEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.addEmployeeSkill(employeeSkill);
        }
    }

    public void updateEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.updateEmployeeSkill(employeeSkill);
        }
    }

    public void deleteEmployeeSkill(List<EmployeeSkill> employeeSkills) {
        for (EmployeeSkill employeeSkill : employeeSkills) {
            employeeSkillMapper.deleteEmployeeSkill(employeeSkill);
        }
    }
}
