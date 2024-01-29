package com.app.roster.controller;


import java.util.Collections;
import java.util.List;

import com.app.roster.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.roster.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // 임직원 전체목록 조회
    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    // 임직원 신규등록
    @PostMapping("/create")
    public ResponseEntity<String> addEmployee(@RequestBody List<Employee> employees) {
        try {
            employeeService.addEmployee(employees);
            return ResponseEntity.ok("Employees added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding employees: " + e.getMessage());
        }
    }
    // 임직원 정보수정
    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody List<Employee> employees) {
        try {
            employeeService.updateEmployee(employees);
            return ResponseEntity.ok("Employees updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating employees: " + e.getMessage());
        }
    }
    // 임직원 정보삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@RequestBody List<Employee> employees) {
        try {
            employeeService.deleteEmployee(employees);
            return ResponseEntity.ok("Employees deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting employees: " + e.getMessage());
        }
    }

    // 임직원 추가 또는 업데이트
    @PostMapping("/addOrUpdate")
    public ResponseEntity<String> addOrUpdateEmployee(@RequestBody List<Employee> employees) {
        try {
            for (Employee employee : employees) {
                // Check if employee with the same ID exists in the database
                if (employeeService.getEmployeeById(employee.getEmployeeID()) != null) {
                    // If exists, update the employee
                    employeeService.updateEmployee(Collections.singletonList(employee));
                } else {
                    // If not exists, add the employee
                    employeeService.addEmployee(Collections.singletonList(employee));
                }
            }
            return ResponseEntity.ok("Employees added or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding or updating employees: " + e.getMessage());
        }
    }
}