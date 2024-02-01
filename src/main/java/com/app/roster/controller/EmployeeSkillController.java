package com.app.roster.controller;

import com.app.roster.dto.Employee;
import com.app.roster.dto.EmployeeSkill;
import com.app.roster.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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


    @PostMapping("/saveOrDelete")
    public ResponseEntity<String> saveOrDelete(@RequestBody List<EmployeeSkill> employeeSkills) {
        try {
            // 저장 또는 삭제할 정보가 포함된 목록
            for (EmployeeSkill employeeSkill : employeeSkills) {
                if (employeeSkillService.existsEmployeeSkill(employeeSkill)) {
                    // 기존에 있는 경우 삭제 수행
                    employeeSkillService.deleteEmployeeSkill(Collections.singletonList(employeeSkill));
                } else {
                    // 기존에 없는 경우 추가 수행
                    employeeSkillService.addEmployeeSkill(Collections.singletonList(employeeSkill));
                }
            }

            return ResponseEntity.ok("EmployeeSkills added or deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding or deleting employeeSkills: " + e.getMessage());
        }
    }


}