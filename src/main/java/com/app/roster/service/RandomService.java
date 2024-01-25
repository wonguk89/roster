package com.app.roster.service;

import com.app.roster.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 선택된 달에 대한 랜덤한 스케줄을 생성합니다.
     *
     * @param selectedMonth 선택된 달 (YYYY-MM 형식)
     * @return 생성된 스케줄 목록
     */
    public List<CalendarSchedule> generateRandomSchedule(String selectedMonth) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Skill> skills = skillsService.getAllSkills();
        List<EmployeeSkill> employeeSkills = employeeSkillService.getAllEmployeeSkills();
        List<Holiday> holidays = holidayService.getAllHolidays();
        List<LeaveRequest> leaveRequests = leaveRequestsService.getAllLeaveRequests();

        // holidayCnt 만큼 휴무일 랜덤지정
        List<LeaveRequest> randomHoliday = generateRandomLeaveRequestsForEmployees(selectedMonth,employees,leaveRequests);

        // 스케줄을 담을 리스트
        List<CalendarSchedule> generatedSchedule = new ArrayList<>();
        // 직원별로 선택되지 않은 횟수를 저장할 Map
        Map<Integer, Integer> unselectedCountMap = new HashMap<>();

        // 선택된 월의 시작 날짜와 마지막 날짜 계산
        YearMonth selectedYearMonth = YearMonth.parse(selectedMonth);
        LocalDate firstDayOfMonth = selectedYearMonth.atDay(1);
        LocalDate lastDayOfMonth = selectedYearMonth.atEndOfMonth();

        // 월의 첫날부터 마지막 날까지 반복
        for (LocalDate date = firstDayOfMonth; date.isBefore(lastDayOfMonth.plusDays(1)); date = date.plusDays(1)) {
            // 날짜와 평일/주말 여부 출력
            System.out.print(date.format(DateTimeFormatter.ISO_DATE) + " - " +
                    (isWeekend(date.getDayOfWeek()) ? "주말" : "평일") + " ");
            boolean isTuesdayOrWednesday = isTuesdayOrWednesday(date);
            // 평일에는 각 스킬 아이디별로 정해진 인원만큼 선택
            List<CalendarSchedule> selectedEmployees = getRandomEmployeesWithSkill(employeeSkills, skills, employees, isTuesdayOrWednesday, date, randomHoliday);


            // 선택된 직원 및 스킬 아이디 출력
            System.out.print("Selected Employees: ");

            // employeeID  대신 이름 출력 + getSkillID 대신 Skill 출력
            for (CalendarSchedule calendarSchedule : selectedEmployees) {
                Employee employee = getEmployeeById(employees, calendarSchedule.getEmployeeID());
                Skill skill = getSkillById(skills, calendarSchedule.getSkillID());

                if (employee != null && skill != null) {
                    System.out.print(employee.getName() + "[" + skill.getSkill() + "] ");
                }
            }
            // 선택되지 않은 직원의 임플로이 아이디 출력
            List<Integer> unselectedEmployeeIds = getUnselectedEmployeeIds(employees, selectedEmployees);
            System.out.print(" Unselected Employees: " + unselectedEmployeeIds);

            // 선택되지 않은 직원들이 몇 번 선택되지 않았는지 집계
            for (Integer unselectedEmployeeId : unselectedEmployeeIds) {
                unselectedCountMap.put(unselectedEmployeeId, unselectedCountMap.getOrDefault(unselectedEmployeeId, 0) + 1);
            }
            System.out.println();

            // date 정보를 selectedEmployees 리스트에 추가
            for (CalendarSchedule calendarSchedule : selectedEmployees) {
                calendarSchedule.setDate(date.format(DateTimeFormatter.ISO_DATE));
            }

            // selectedEmployees를 generatedSchedule에 추가
            generatedSchedule.addAll(selectedEmployees);
            System.out.println();
        }
        // 각 직원별로 휴무 횟수 출력
        System.out.println("Holiday Counts by Employee: " + unselectedCountMap);

        return generatedSchedule;
    }


    /**
     * 주어진 날짜가 주말인지 확인합니다.
     *
     * @param dayOfWeek 확인할 날짜의 요일
     * @return 주말이면 true, 그렇지 않으면 false
     */
    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * 주어진 스킬을 가진 직원 중에서 랜덤하게 선택합니다.
     *
     * @param employeeSkills        직원과 스킬 관계 목록
     * @param skills                전체 스킬 목록
     * @param employees             전체 직원 목록
     * @param currentDate           현재 날짜
     * @param randomHoliday         랜덤 휴무 목록
     * @return 선택된 직원 목록
     */
    private List<CalendarSchedule> getRandomEmployeesWithSkill(List<EmployeeSkill> employeeSkills, List<Skill> skills, List<Employee> employees, boolean isTuesdayOrWednesday, LocalDate currentDate, List<LeaveRequest> randomHoliday) {
        // 이미 선택된 직원을 기억하는 리스트
        List<Employee> alreadySelectedEmployees = new ArrayList<>();
        // 각 스킬 아이디별로 정해진 인원만큼 선택
        List<CalendarSchedule> selectedEmployees = new ArrayList<>();

        for (int skillId = 1; skillId <= 3; skillId++) {
            List<Employee> employeesWithSkill = getEmployeesWithSkill(employeeSkills, skills, skillId, employees);
            // 중복 체크를 위해 이미 선택된 직원은 제외
            employeesWithSkill.removeAll(alreadySelectedEmployees);

            // 휴가를 신청한 직원은 제외
            List<Integer> employeesOnHoliday = getEmployeesOnHoliday(randomHoliday, currentDate);
            employeesWithSkill.removeIf(employee -> employeesOnHoliday.contains(employee.getEmployeeID()));


            Collections.shuffle(employeesWithSkill);
            int employeesToSelect = getEmployeesToSelect(skillId,isTuesdayOrWednesday);

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

    /**
     * 특정 스킬을 가진 직원 목록을 반환합니다.
     *
     * @param employeeSkills 직원과 스킬 관계 목록
     * @param skills         전체 스킬 목록
     * @param skillId        선택할 스킬 아이디
     * @param employees      전체 직원 목록
     * @return 선택된 스킬을 가진 직원 목록
     */
    private List<Employee> getEmployeesWithSkill(List<EmployeeSkill> employeeSkills, List<Skill> skills, int skillId, List<Employee> employees) {
        List<Employee> employeesWithSkill = new ArrayList<>();
        for (EmployeeSkill employeeSkill : employeeSkills) {
            if (employeeSkill.getSkillID() == skillId) {
                employeesWithSkill.add(getEmployeeById(employeeSkill.getEmployeeID(), employees));
            }
        }
        return employeesWithSkill;
    }

    /**
     * 직원 ID로 직원을 찾습니다.
     *
     * @param employeeId 찾을 직원의 ID
     * @param employees  전체 직원 목록
     * @return 해당 ID에 매칭되는 직원 객체
     */
    private Employee getEmployeeById(int employeeId, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeId) {
                return employee;
            }
        }
        return null;
    }

    /**
     * 특정 스킬 아이디를 가진 직원들을 선택하는데 필요한 인원을 반환합니다.
     *
     * @param skillId   선택된 스킬 아이디
     * @return 선택해야 하는 직원 수
     */
    private int getEmployeesToSelect(int skillId,boolean isTuesdayOrWednesday) {

            // 화, 수인 경우 5명
            if (isTuesdayOrWednesday) {
                if (skillId == 1) {
                    return 1; // 오픈에 1명 선택
                } else if (skillId == 2) {
                    return 2; // 미들에 2명 선택
                } else if (skillId == 3) {
                    return 2; // 마감에 2명 선택
                }
            } else {
                // 그외는 6명
                if (skillId == 1) {
                    return 2; // 오픈에 1명 선택
                } else if (skillId == 2) {
                    return 2; // 미들에 2명 선택
                } else if (skillId == 3) {
                    return 2; // 마감에 1명 선택
                }
            }
        return 0;
    }

    /**
     * 현재 날짜가 화요일 또는 수요일인지 확인합니다.
     *
     * @param date 확인할 날짜
     * @return 화요일 또는 수요일이면 true, 그렇지 않으면 false
     */
    private boolean isTuesdayOrWednesday(LocalDate date) {
        // 날짜의 요일을 가져옴
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // 날짜가 화요일 또는 수요일이면 true를 반환
        return dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY;
    }

    /**
     * 주어진 ID에 해당하는 직원을 반환합니다.
     *
     * @param employees  직원 목록
     * @param employeeId 찾고자 하는 직원의 ID
     * @return ID에 해당하는 직원, 없으면 null
     */
    private Employee getEmployeeById(List<Employee> employees, int employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeId) {
                return employee;
            }
        }
        return null; // 해당 ID에 매칭되는 직원이 없을 경우
    }

    /**
     * 주어진 ID에 해당하는 스킬을 반환합니다.
     *
     * @param skills  스킬 목록
     * @param skillId 찾고자 하는 스킬의 ID
     * @return ID에 해당하는 스킬, 없으면 null
     */
    private Skill getSkillById(List<Skill> skills, int skillId) {
        for (Skill skill : skills) {
            if (skill.getSkillID() == skillId) {
                return skill;
            }
        }
        return null;
    }

    /**
     * 주어진 날짜에 휴가를 신청한 직원의 목록을 반환합니다.
     *
     * @param randomHoliday 휴가 신청 목록
     * @param currentDate   현재 날짜
     * @return 해당 날짜에 휴가를 신청한 직원 ID 목록
     */
    private List<Integer> getEmployeesOnHoliday(List<LeaveRequest> randomHoliday, LocalDate currentDate) {
        List<Integer> employeesOnHoliday = randomHoliday.stream()
                .filter(request -> {
                    // LocalDate를 String으로 변환하여 비교
                    String requestDate = request.getLeaveDate().toString();
                    String currentDateStr = currentDate.toString();
                    return requestDate.equals(currentDateStr);
                })
                .map(LeaveRequest::getEmployeeID)
                .collect(Collectors.toList());

        return employeesOnHoliday;
    }

    /**
     * 선택되지 않은 직원의 ID 목록을 반환합니다.
     *
     * @param employees         전체 직원 목록
     * @param selectedEmployees 선택된 직원 목록
     * @return 선택되지 않은 직원의 ID 목록
     */
    private List<Integer> getUnselectedEmployeeIds(List<Employee> employees, List<CalendarSchedule> selectedEmployees) {
        List<Integer> allEmployeeIds = employees.stream()
                .map(Employee::getEmployeeID)
                .collect(Collectors.toList());

        List<Integer> selectedEmployeeIds = selectedEmployees.stream()
                .map(CalendarSchedule::getEmployeeID)
                .collect(Collectors.toList());

        List<Integer> unselectedEmployeeIds = new ArrayList<>(allEmployeeIds);
        unselectedEmployeeIds.removeAll(selectedEmployeeIds);

        return unselectedEmployeeIds;
    }

    /**
     * 특정 월에 해당하는 휴가 신청 목록을 반환합니다.
     *
     * @param leaveRequests  전체 휴가 신청 목록
     * @param selectedMonth  선택된 월 (yyyy-MM 형식)
     * @return 특정 월에 해당하는 휴가 신청 목록
     */
    public List<LeaveRequest> getLeaveRequestsForMonth(List<LeaveRequest> leaveRequests, String selectedMonth) {
        return leaveRequests.stream()
                .filter(request -> request.getLeaveDate().startsWith(selectedMonth))
                .collect(Collectors.toList());
    }

    /**
     * 직원들에 대한 랜덤 휴가 신청을 생성하고 반환합니다.
     *
     * @param selectedMonth 선택된 월 (yyyy-MM 형식)
     * @param employees     전체 직원 목록
     * @param leaveRequests  전체 휴가 신청 목록
     * @return 생성된 랜덤 휴가 신청 목록
     */
    public List<LeaveRequest> generateRandomLeaveRequestsForEmployees(String selectedMonth, List<Employee> employees, List<LeaveRequest> leaveRequests) {
        // 직원별로 랜덤 휴무 생성 및 리스트에 추가
        List<LeaveRequest> allRandomLeaveRequests = new ArrayList<>();
        Map<Integer, Integer> employeeDateCountMap = new HashMap<>();
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();
        List<LeaveRequest> existingLeaveRequests = getLeaveRequestsForMonth(leaveRequests, selectedMonth);

        for (Employee employee : employees) {
            int employeeId = employee.getEmployeeID();
            List<LeaveRequest> employeeExistingLeaveRequests = getEmployeeLeaveRequestsForMonth(existingLeaveRequests, employeeId);
            countDuplicateDates(employeeExistingLeaveRequests, dateCountMap);

            // 직원의 휴무 일수를 employeeDateCountMap에 추가
            int employeeLeaveCount = employeeExistingLeaveRequests.size();
            employeeDateCountMap.put(employeeId, employeeLeaveCount);
        }
        for (Employee employee : employees) {
            int employeeId = employee.getEmployeeID();
            int holidayCnt = employee.getHolidayCnt() - employeeDateCountMap.getOrDefault(employeeId, 0); // 이미 신청한 휴무 갯수를 빼줌

            // 남은 휴가가 0 이하인 경우 랜덤 휴무를 추가하지 않음
            if (holidayCnt > 0) {
                List<LeaveRequest> employeeRandomLeaveRequests = generateRandomHolidays(selectedMonth, employeeId, holidayCnt, leaveRequests, dateCountMap);
                allRandomLeaveRequests.addAll(employeeRandomLeaveRequests);
            }
        }
        allRandomLeaveRequests.addAll(existingLeaveRequests);
        // 전체 결과 출력
        System.out.println("===== 전체 결과 =====");
        for (Employee employee : employees) {
            int employeeId = employee.getEmployeeID();
            int employeeHolidayCnt = calculateEmployeeHolidayCount(allRandomLeaveRequests, employeeId);
            System.out.println("Employee ID: " + employeeId + ", Remaining Holiday Count: " + employeeHolidayCnt);
        }

        return allRandomLeaveRequests;
    }
    /**
     * 특정 직원이 사용한 휴가일 수를 계산합니다.
     *
     * @param allRandomLeaveRequests 전체 랜덤 휴가 신청 목록
     * @param employeeId            휴가일 수를 계산할 직원 ID
     * @return 특정 직원이 사용한 휴가일 수
     */
    private int calculateEmployeeHolidayCount(List<LeaveRequest> allRandomLeaveRequests, int employeeId) {
        // 특정 직원이 사용한 휴가일 수 계산
        long employeeUsedHolidayCount = allRandomLeaveRequests.stream()
                .filter(request -> request.getEmployeeID() == employeeId)
                .count();

        return (int) employeeUsedHolidayCount;
    }

    /**
     * 주어진 월과 직원 ID에 대한 랜덤 휴가를 생성하고 반환합니다.
     *
     * @param selectedMonth     선택된 월 (yyyy-MM 형식)
     * @param employeeId        직원 ID
     * @param holidayCnt        생성할 랜덤 휴가 개수
     * @param leaveRequests     전체 휴가 신청 목록
     * @param dateCountMap      휴가 신청 날짜 카운트 맵
     * @return 생성된 랜덤 휴가 신청 목록
     */
    private List<LeaveRequest> generateRandomHolidays(String selectedMonth, int employeeId, int holidayCnt, List<LeaveRequest> leaveRequests, Map<LocalDate, Integer> dateCountMap) {
        List<LeaveRequest> randomHolidays = new ArrayList<>();
        List<LeaveRequest> employeeExistingLeaveRequests = getEmployeeLeaveRequestsForMonth(leaveRequests, employeeId);
        // employeeExistingLeaveRequests를 randomHolidays에 추가
        randomHolidays.addAll(employeeExistingLeaveRequests);
        for (int i = 0; i < holidayCnt; i++) {
            LocalDate randomDate = null;
            int loopCount = 0;
            while (loopCount < 100) {
                randomDate = getRandomDate(selectedMonth);

                // 새롭게 선택된 randomDate에 대한 조건 검사
                if (!isEmployeeAlreadyOnLeave(employeeExistingLeaveRequests, randomHolidays, randomDate) &&
                        !isExceedingMaxEmployeesOnLeave(randomDate, dateCountMap) &&
                        !isExceedingMaxInterval(randomDate, randomHolidays, 7)) {
                    break; // 조건을 만족하면 while 루프 종료
                }

                loopCount++;

                if (loopCount >= 100) {
                    // 100회 이상 반복했을 때 RuntimeException 발생
                    throw new RuntimeException("스케쥴 생성에 실패하였습니다, 다시 시도해주세요.");
                }
            }
            // 휴가 생성 및 리스트에 추가
            LeaveRequest randomHoliday = new LeaveRequest();
            randomHoliday.setEmployeeID(employeeId);
            randomHoliday.setLeaveDate(randomDate.toString());
            randomHoliday.setReason("Random Holiday");
            randomHolidays.add(randomHoliday);

            // 생성한 휴가 날짜를 dateCountMap에 업데이트
            dateCountMap.put(randomDate, dateCountMap.getOrDefault(randomDate, 0) + 1);
        }
        return randomHolidays;
    }

    /**
     * 이미 해당 날짜에 직원이 신청한 휴무가 있는지 확인합니다.
     *
     * @param employeeExistingLeaveRequests 직원이 이미 신청한 휴가 목록
     * @param randomHolidays               랜덤으로 생성된 휴가 목록
     * @param randomDate                   확인할 휴가 날짜
     * @return 이미 해당 날짜에 휴무가 있는 경우 true, 그렇지 않으면 false
     */
    private boolean isEmployeeAlreadyOnLeave(List<LeaveRequest> employeeExistingLeaveRequests, List<LeaveRequest> randomHolidays, LocalDate randomDate) {
        // 이미 해당 날짜에 직원이 신청한 휴무인지 확인
        boolean isEmployeeOnLeave = employeeExistingLeaveRequests.stream()
                .anyMatch(request -> LocalDate.parse(request.getLeaveDate()).equals(randomDate));

        // 이미 해당 날짜에 랜덤으로 생성된 휴무인지 확인
        boolean isRandomHolidayGenerated = randomHolidays.stream()
                .anyMatch(holiday -> LocalDate.parse(holiday.getLeaveDate()).equals(randomDate));

        // 특정 날짜에 이미 휴무가 있으면 true 반환
        return isEmployeeOnLeave || isRandomHolidayGenerated;
    }

    /**
     * 해당 날짜에 최대 인원을 초과하는지 확인합니다.
     *
     * @param randomDate   확인할 날짜
     * @param dateCountMap 휴가 신청 날짜 카운트 맵
     * @return 최대 인원을 초과하면 true, 그렇지 않으면 false
     */
    private boolean isExceedingMaxEmployeesOnLeave(LocalDate randomDate, Map<LocalDate, Integer> dateCountMap) {
        int count = dateCountMap.getOrDefault(randomDate, 0);
        // 화요일,수요일(근무인원5), 나머지(근무인원6)
        DayOfWeek dayOfWeek = randomDate.getDayOfWeek();
        int maxEmployeesOnLeave =(dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY) ? 3 : 2;
        return count >= maxEmployeesOnLeave;
    }

    /**
     * 주어진 월에 랜덤으로 선택된 날짜를 반환합니다.
     *
     * @param selectedMonth 선택된 월 (yyyy-MM 형식)
     * @return 랜덤으로 선택된 날짜
     */
    private LocalDate getRandomDate(String selectedMonth) {
        YearMonth yearMonth = YearMonth.parse(selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth();
        int randomDay = new Random().nextInt(daysInMonth) + 1; // 1부터 daysInMonth까지의 난수 생성
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), randomDay);
    }

    /**
     * 주어진 직원 ID와 월에 해당하는 휴가 신청 목록을 반환합니다.
     *
     * @param leaveRequests 휴가 신청 목록
     * @param employeeId    직원 ID
     * @return 주어진 직원 ID와 월에 해당하는 휴가 신청 목록
     */
    private List<LeaveRequest> getEmployeeLeaveRequestsForMonth(List<LeaveRequest> leaveRequests, int employeeId) {
        return leaveRequests.stream()
                .filter(request -> request.getEmployeeID() == employeeId)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 휴가 신청 목록에서 날짜별 휴가 신청 횟수를 카운트하고 반환합니다.
     *
     * @param employeeExistingLeaveRequests 직원이 이미 신청한 휴가 목록
     * @param dateCountMap                  휴가 신청 날짜 카운트 맵
     * @return 날짜별 휴가 신청 횟수를 담은 맵
     */
    private Map<LocalDate, Integer> countDuplicateDates(List<LeaveRequest> employeeExistingLeaveRequests, Map<LocalDate, Integer> dateCountMap) {
        for (LeaveRequest request : employeeExistingLeaveRequests) {
            String leaveDate = request.getLeaveDate();
            LocalDate leaveDay = LocalDate.parse(leaveDate);
            dateCountMap.put(leaveDay, dateCountMap.getOrDefault(leaveDay, 0) + 1);
        }

        return dateCountMap;
    }
    /**
     * 이미 추가된 휴무일 중에서 가장 가까운 이전 휴무일과 다음 휴무일을 찾아 최대 간격을 초과하는지 확인합니다.
     *
     * @param randomDate             현재 고려 중인 랜덤 휴무일
     * @param existingLeaveRequests  이미 추가된 휴무일 목록
     * @param maxInterval            최대 간격
     * @return 최대 간격을 초과하는 경우 true, 그렇지 않으면 false
     */
    private boolean isExceedingMaxInterval(LocalDate randomDate, List<LeaveRequest> existingLeaveRequests, int maxInterval) {
        // 이미 추가된 휴무일 중에서 가장 가까운 이전 휴무일과 다음 휴무일 찾기
        LocalDate closestPreviousLeaveDate = existingLeaveRequests.stream()
                .map(leaveRequest -> LocalDate.parse(leaveRequest.getLeaveDate()))
                .filter(leaveDate -> leaveDate.isBefore(randomDate))
                .max(Comparator.naturalOrder())
                .orElse(null);

        LocalDate closestNextLeaveDate = existingLeaveRequests.stream()
                .map(leaveRequest -> LocalDate.parse(leaveRequest.getLeaveDate()))
                .filter(leaveDate -> leaveDate.isAfter(randomDate))
                .min(Comparator.naturalOrder())
                .orElse(null);

        // 최대 간격을 초과하는 경우 true 반환
        return (closestPreviousLeaveDate != null && ChronoUnit.DAYS.between(closestPreviousLeaveDate, randomDate) > maxInterval) ||
                (closestNextLeaveDate != null && ChronoUnit.DAYS.between(randomDate, closestNextLeaveDate) > maxInterval);
    }
}


