package com.app.roster.controller;

import com.app.roster.dto.Holiday;
import com.app.roster.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> deleteHoliday(@RequestBody List<Holiday> holidays) {
        try {
            holidayService.deleteHoliday(holidays);
            return ResponseEntity.ok("Holidays deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting holidays: " + e.getMessage());
        }
    }
}