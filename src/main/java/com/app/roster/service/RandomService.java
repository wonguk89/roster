package com.app.roster.service;

import com.app.roster.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RandomService {

    private final EmployeeService employeeService;
    private final SkillsService skillsService;
    private final EmployeeSkillService employeeSkillService;
    private final HolidayService holidayService;
    private final LeaveRequestsService leaveRequestsService;

    @Autowired
    public RandomService(EmployeeService employeeService, SkillsService skillsService,
                         EmployeeSkillService employeeSkillService, HolidayService holidayService,
                         LeaveRequestsService leaveRequestsService) {
        this.employeeService = employeeService;
        this.skillsService = skillsService;
        this.employeeSkillService = employeeSkillService;
        this.holidayService = holidayService;
        this.leaveRequestsService = leaveRequestsService;
    }

    public List<CalendarSchedule> generateRandomSchedule(String selectedMonth) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Skill> skills = skillsService.getAllSkills();
        List<EmployeeSkill> employeeSkills = employeeSkillService.getAllEmployeeSkills();
        List<Holiday> holidays = holidayService.getAllHolidays();
        List<LeaveRequest> leaveRequests = leaveRequestsService.getAllLeaveRequests();

        // 스케줄을 담을 리스트
        List<CalendarSchedule> generatedSchedule = new ArrayList<>();

        // 선택된 월의 시작 날짜와 마지막 날짜 계산
        YearMonth selectedYearMonth = YearMonth.parse(selectedMonth);
        LocalDate firstDayOfMonth = selectedYearMonth.atDay(1);
        LocalDate lastDayOfMonth = selectedYearMonth.atEndOfMonth();

        // 월의 첫날부터 마지막 날까지 반복
        for (LocalDate date = firstDayOfMonth; date.isBefore(lastDayOfMonth.plusDays(1)); date = date.plusDays(1)) {
            // 날짜와 평일/주말 여부 출력
            System.out.print(date.format(DateTimeFormatter.ISO_DATE) + " - " +
                    (isWeekend(date.getDayOfWeek()) ? "주말" : "평일") + " ");

            // 평일에는 각 스킬 아이디별로 정해진 인원만큼 선택
            // 주말에는 더 많은 인원 선택
            int numberOfEmployees = isWeekend(date.getDayOfWeek()) ? 6 : 4;
            List<CalendarSchedule> selectedEmployees = getRandomEmployeesWithSkill(employeeSkills, skills, numberOfEmployees, employees, isWeekend(date.getDayOfWeek()));

            // 선택된 직원 및 스킬 아이디 출력
            System.out.print("Selected Employees: ");
//            for (CalendarSchedule calendarSchedule : selectedEmployees) {
//                Employee employee = getEmployeeById(employees, calendarSchedule.getEmployeeID());
//                if (employee != null) {
//                    System.out.print(employee.getName() + "(" + calendarSchedule.getSkillID() + ") ");
//                }
//
//            }
            for (CalendarSchedule calendarSchedule : selectedEmployees) {
                // 이름 대신 employeeID 출력
                System.out.print(calendarSchedule.getEmployeeID() + "(" + calendarSchedule.getSkillID() + ") ");
            }

            System.out.println();

            // date 정보를 selectedEmployees 리스트에 추가
            for (CalendarSchedule calendarSchedule : selectedEmployees) {
                calendarSchedule.setDate(date.format(DateTimeFormatter.ISO_DATE));
            }

            // selectedEmployees를 generatedSchedule에 추가
            generatedSchedule.addAll(selectedEmployees);
        }

        return generatedSchedule;
    }



    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private List<CalendarSchedule> getRandomEmployeesWithSkill(List<EmployeeSkill> employeeSkills, List<Skill> skills, int numberOfEmployees, List<Employee> employees, boolean isWeekend) {
        // 이미 선택된 직원을 기억하는 리스트
        List<Employee> alreadySelectedEmployees = new ArrayList<>();
        // 각 스킬 아이디별로 정해진 인원만큼 선택
        List<CalendarSchedule> selectedEmployees = new ArrayList<>();

        for (int skillId = 1; skillId <= 3; skillId++) {
            List<Employee> employeesWithSkill = getEmployeesWithSkill(employeeSkills, skills, skillId, employees);
            // 중복 체크를 위해 이미 선택된 직원은 제외
            employeesWithSkill.removeAll(alreadySelectedEmployees);
            Collections.shuffle(employeesWithSkill);
            int employeesToSelect = getEmployeesToSelect(numberOfEmployees, skillId, isWeekend);

            // 선택된 직원을 기억 리스트에 추가
            List<Employee> selectedEmployeesForSkill = employeesWithSkill.subList(0, Math.min(employeesToSelect, employeesWithSkill.size()));
            for (Employee employee : selectedEmployeesForSkill) {
                CalendarSchedule schedule = new CalendarSchedule();
                schedule.setSkillID(skillId);
                schedule.setEmployeeID(employee.getEmployeeID());
                selectedEmployees.add(schedule);
                alreadySelectedEmployees.add(employee);
            }

            // 출근조 별 정해진 인원이 일치하지 않으면 처음부터 다시 반복
            if (selectedEmployeesForSkill.size() != employeesToSelect) {
                // 현재 스킬에 대한 선택이 부족한 경우
                // 처음부터 다시 반복문 돌리기
                skillId = 0; // 다음 반복에서 1이 증가하면서 1로 시작하게 됩니다.
                selectedEmployees.clear(); // 기존 선택된 스케쥴 제거
                alreadySelectedEmployees.clear(); // 기존 선택된 직원 초기화
            }
        }
        return selectedEmployees;
    }

    private List<Employee> getEmployeesWithSkill(List<EmployeeSkill> employeeSkills, List<Skill> skills, int skillId, List<Employee> employees) {
        List<Employee> employeesWithSkill = new ArrayList<>();
        for (EmployeeSkill employeeSkill : employeeSkills) {
            if (employeeSkill.getSkillID() == skillId) {
                employeesWithSkill.add(getEmployeeById(employeeSkill.getEmployeeID(), employees));
            }
        }
        return employeesWithSkill;
    }

    private Employee getEmployeeById(int employeeId, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeId) {
                return employee;
            }
        }
        return null;
    }

    private int getEmployeesToSelect(int totalEmployees, int skillId, boolean isWeekend) {
        // 주말인 경우 픽스된 조건으로 인원을 선택
        if (isWeekend) {
            if (skillId == 1) {
                return 2; // 오픈에 2명 선택
            } else if (skillId == 2) {
                return 2; // 미들에 2명 선택
            } else if (skillId == 3) {
                return 2; // 마감에 2명 선택
            }
        }
        // 평일인 경우 픽스된 조건으로 인원을 선택
        else {
            if (skillId == 1) {
                return 1; // 오픈에 1명 선택
            } else if (skillId == 2) {
                return 2; // 미들에 2명 선택
            } else if (skillId == 3) {
                return 1; // 마감에 1명 선택
            }
        }
        return 0;
    }
    // 직원이 가진 스킬을 찾는 메서드
    private List<Integer> getEmployeeSkillIds(Employee employee, List<EmployeeSkill> employeeSkills) {
        List<Integer> skillIds = new ArrayList<>();
        for (EmployeeSkill employeeSkill : employeeSkills) {
            if (employeeSkill.getEmployeeID() == employee.getEmployeeID()) {
                skillIds.add(employeeSkill.getSkillID());
            }
        }
        return skillIds;
    }
    // 직원 ID로 직원을 찾는 메서드
    private Employee getEmployeeById(List<Employee> employees, int employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeId) {
                return employee;
            }
        }
        return null; // 해당 ID에 매칭되는 직원이 없을 경우
    }
}


