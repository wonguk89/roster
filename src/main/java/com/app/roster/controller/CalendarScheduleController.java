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

    @GetMapping("/getAll")
    public ResponseEntity<List<CalendarSchedule>> getAllCalendarSchedule() {
        List<CalendarSchedule> calendarSchedules = calendarScheduleService.getAllCalendarSchedule();
        return new ResponseEntity<>(calendarSchedules, HttpStatus.OK);
    }

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
