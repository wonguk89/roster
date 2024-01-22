package com.app.roster.controller;

import com.app.roster.dto.CalendarSchedule;
import com.app.roster.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/random")
public class RandomController {

    private final RandomService randomService;

    @Autowired
    public RandomController(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping("/generate")
    public List<CalendarSchedule> generateRandomSchedule(@RequestParam(name = "selectedMonth") String selectedMonth) {
        List<CalendarSchedule> calendarSchedule = randomService.generateRandomSchedule(selectedMonth);
        return calendarSchedule;
    }
}