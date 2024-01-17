package com.app.roster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSkill {
    private int eno;
    private int employeeID;
    private int skillID;
}