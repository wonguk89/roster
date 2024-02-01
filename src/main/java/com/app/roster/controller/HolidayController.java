package com.app.roster.controller;

import com.app.roster.dto.Employee;
import com.app.roster.dto.Holiday;
import com.app.roster.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/holiday")
public class HolidayController {

    private final HolidayService holidayService;

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    // 모든 공휴일 목록 조회
    @GetMapping("/getAll")
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    // 선택된 달의 공휴일 목록 조회
    @GetMapping("/getByMonth")
    public ResponseEntity<?> getHolidaysByMonth(
            @RequestParam(name = "date") String date) {
        try {
            // 날짜를 문자열로 가공해서 전달하므로 추가적인 변환 작업이 필요하지 않음
            List<Holiday> holidays = holidayService.getHolidaysByMonth(date);
            return new ResponseEntity<>(holidays, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting holidays for the month: " + e.getMessage());
        }
    }

    // 새로운 공휴일 등록
    @PostMapping("/create")
    public ResponseEntity<String> addHoliday(@RequestBody List<Holiday> holidays) {
        try {
            holidayService.addHoliday(holidays);
            return ResponseEntity.ok("Holidays added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding holidays: " + e.getMessage());
        }
    }

    // 공휴일 정보 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateHoliday(@RequestBody List<Holiday> holidays) {
        try {
            holidayService.updateHoliday(holidays);
            return ResponseEntity.ok("Holidays updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating holidays: " + e.getMessage());
        }
    }

    // 공휴일 정보 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHoliday(@RequestParam(name = "holidayID") List<Integer> holidayIDs) {
        try {
            holidayService.deleteHoliday(holidayIDs);
            return ResponseEntity.ok("Holidays deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting holidays: " + e.getMessage());
        }
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<String> addOrUpdate(@RequestBody List<Holiday> holidays) {
        try {
            for (Holiday holiday : holidays) {
                // Check if employee with the same ID exists in the database
                if (holidayService.getEmployeeById(holiday.getHolidayID()) != null) {
                    // If exists, update the employee
                    holidayService.updateHoliday(Collections.singletonList(holiday));
                } else {
                    // If not exists, add the employee
                    holidayService.addHoliday(Collections.singletonList(holiday));
                }
            }
            return ResponseEntity.ok("Holidays added or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding or updating Holidays: " + e.getMessage());
        }
    }
}