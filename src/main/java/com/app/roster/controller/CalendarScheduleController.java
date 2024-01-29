package com.app.roster.controller;

import com.app.roster.dto.CalendarSchedule;
import com.app.roster.service.CalendarScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendarSchedule")
public class CalendarScheduleController {

    private final CalendarScheduleService calendarScheduleService;

    @Autowired
    public CalendarScheduleController(CalendarScheduleService calendarScheduleService) {
        this.calendarScheduleService = calendarScheduleService;
    }
    // 확정스케쥴 전체목록 조회
    @GetMapping("/getAll")
    public ResponseEntity<List<CalendarSchedule>> getAllCalendarSchedule() {
        List<CalendarSchedule> calendarSchedules = calendarScheduleService.getAllCalendarSchedule();
        return new ResponseEntity<>(calendarSchedules, HttpStatus.OK);
    }
    // 확정스케쥴 월별 조회 (date = "YYYY-MM")
    @GetMapping("/getByMonth")
    public ResponseEntity<?> getAllCalendarScheduleByMonth(
            @RequestParam(name = "date") String date) {
        try {
            // 프론트엔드에서 이미 날짜를 문자열로 가공해서 전달하므로 추가적인 변환 작업이 필요하지 않음
            List<CalendarSchedule> calendarSchedules = calendarScheduleService.getCalendarScheduleByMonth(date);
            return new ResponseEntity<>(calendarSchedules, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting calendar schedules for the month: " + e.getMessage());
        }
    }
    // 확정스케쥴 신규등록
    @PostMapping("/create")
    public ResponseEntity<String> addCalendarSchedule(@RequestBody List<CalendarSchedule> calendarSchedules) {
        try {
            calendarScheduleService.addCalendarSchedule(calendarSchedules);
            return ResponseEntity.ok("CalendarSchedules added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding calendarSchedules: " + e.getMessage());
        }
    }
    // 확정스케쥴 정보수정
    @PutMapping("/update")
    public ResponseEntity<String> updateCalendarSchedule(@RequestBody List<CalendarSchedule> calendarSchedules) {
        try {
            calendarScheduleService.updateCalendarSchedule(calendarSchedules);
            return ResponseEntity.ok("CalendarSchedules updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating calendarSchedules: " + e.getMessage());
        }
    }
    // 확정스케쥴 정보삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCalendarSchedule(@RequestBody List<CalendarSchedule> calendarSchedules) {
        try {
            calendarScheduleService.deleteCalendarSchedule(calendarSchedules);
            return ResponseEntity.ok("CalendarSchedules deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting calendarSchedules: " + e.getMessage());
        }
    }
}
