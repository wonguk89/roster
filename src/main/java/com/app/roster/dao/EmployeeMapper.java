package com.app.roster.dao;

import java.util.List;

import com.app.roster.dto.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

    List<Employee> getAllEmployees();

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    // 다른 메소드들...
}