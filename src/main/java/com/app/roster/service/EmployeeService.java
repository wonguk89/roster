package com.app.roster.service;

import java.util.List;

import com.app.roster.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.roster.dao.EmployeeMapper;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> getAllEmployees() {
        return employeeMapper.getAllEmployees();
    }

    public void addEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.addEmployee(employee);
        }
    }

    public void updateEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.updateEmployee(employee);
        }
    }

    public void deleteEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.deleteEmployee(employee);
        }
    }

}