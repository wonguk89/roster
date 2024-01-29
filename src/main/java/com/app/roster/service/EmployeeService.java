package com.app.roster.service;

import java.util.List;

import com.app.roster.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.roster.dao.EmployeeMapper;

/**
 * 직원 정보를 관리하는 서비스 클래스입니다.
 */
@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 모든 직원 정보를 조회합니다.
     *
     * @return 모든 직원 목록
     */
    public List<Employee> getAllEmployees() {
        return employeeMapper.getAllEmployees();
    }

    /**
     * 직원을 추가합니다.
     *
     * @param employees 추가할 직원 목록
     */
    public void addEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.addEmployee(employee);
        }
    }

    /**
     * 직원 정보를 업데이트합니다.
     *
     * @param employees 업데이트할 직원 목록
     */
    public void updateEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.updateEmployee(employee);
        }
    }

    /**
     * 직원을 삭제합니다.
     *
     * @param employees 삭제할 직원 목록
     */
    public void deleteEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeMapper.deleteEmployee(employee);
        }
    }

    /**
     * 주어진 직원 ID에 해당하는 직원을 가져옵니다.
     *
     * @param employeeID 가져올 직원의 ID
     * @return 직원 객체, 해당 ID에 해당하는 직원이 없을 경우 null 반환
     */
    public Employee getEmployeeById(int employeeID) {
        return employeeMapper.getEmployeeById(employeeID);
    }
}
