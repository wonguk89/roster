package com.app.roster.service;

import com.app.roster.dao.CalendarScheduleMapper;
import com.app.roster.dto.CalendarSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalendarScheduleService {

    private final CalendarScheduleMapper calendarScheduleMapper;

    @Autowired
    public CalendarScheduleService(CalendarScheduleMapper calendarScheduleMapper) {
        this.calendarScheduleMapper = calendarScheduleMapper;
    }

    public List<CalendarSchedule> getAllCalendarSchedule() {
        return calendarScheduleMapper.getAllCalendarSchedule();
    }

    @Transactional
    public void addCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.addCalendarSchedule(calendarSchedule);
        }
    }

    @Transactional
    public void updateCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.updateCalendarSchedule(calendarSchedule);
        }
    }

    @Transactional
    public void deleteCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.deleteCalendarSchedule(calendarSchedule.getSno());
        }
    }
}
