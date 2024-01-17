package com.app.roster.dao;

import com.app.roster.dto.EmployeeSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeSkillMapper {
    List<EmployeeSkill> getAllEmployeeSkills();

    void addEmployeeSkill(EmployeeSkill employeeSkill);

    void updateEmployeeSkill(EmployeeSkill employeeSkill);

    void deleteEmployeeSkill(EmployeeSkill employeeSkill);
}
