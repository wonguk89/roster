package com.app.roster.controller;

import com.app.roster.dto.CalendarSchedule;
import com.app.roster.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/random")
public class RandomController {

    private final RandomService randomService;

    @Autowired
    public RandomController(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generateRandomSchedule(@RequestParam(name = "selectedMonth") String selectedMonth) {
        try {
            List<CalendarSchedule> calendarSchedule = randomService.generateRandomSchedule(selectedMonth);
            return ResponseEntity.ok(calendarSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(e.getMessage()));
        }
    }
    
}