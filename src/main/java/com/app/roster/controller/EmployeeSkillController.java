package com.app.roster.controller;

import com.app.roster.dto.EmployeeSkill;
import com.app.roster.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeeSkill")
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    @Autowired
    public EmployeeSkillController(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }

    // 임직원 가능 근무타입 전체목록 조회
    @GetMapping("/getAll")
    public List<EmployeeSkill> getAllEmployeeSkills() {
        return employeeSkillService.getAllEmployeeSkills();
    }

    // 임직원 가능 근무타입 신규등록
    @PostMapping("/create")
    public ResponseEntity<String> addEmployeeSkill(@RequestBody List<EmployeeSkill> employeeSkills) {
        try {
            employeeSkillService.addEmployeeSkill(employeeSkills);
            return ResponseEntity.ok("EmployeeSkills added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding employeeSkills: " + e.getMessage());
        }
    }

    // 임직원 가능 근무타입 정보수정
    @PutMapping("/update")
    public ResponseEntity<String> updateEmployeeSkill(@RequestBody List<EmployeeSkill> employeeSkills) {
        try {
            employeeSkillService.updateEmployeeSkill(employeeSkills);
            return ResponseEntity.ok("EmployeeSkills updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating employeeSkills: " + e.getMessage());
        }
    }

    // 임직원 가능 근무타입 정보삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployeeSkill(@RequestBody List<EmployeeSkill> employeeSkills) {
        try {
            employeeSkillService.deleteEmployeeSkill(employeeSkills);
            return ResponseEntity.ok("EmployeeSkills deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting employeeSkills: " + e.getMessage());
        }
    }
}