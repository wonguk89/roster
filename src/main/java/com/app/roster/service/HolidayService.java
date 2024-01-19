package com.app.roster.service;

import com.app.roster.dto.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.roster.dao.HolidayMapper;

import java.util.List;

@Service
public class HolidayService {

    private final HolidayMapper holidayMapper;

    @Autowired
    public HolidayService(HolidayMapper holidayMapper) {
        this.holidayMapper = holidayMapper;
    }

    public List<Holiday> getAllHolidays() {
        return holidayMapper.getAllHolidays();
    }

    public void addHoliday(List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            holidayMapper.addHoliday(holiday);
        }
    }

    public void updateHoliday(List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            holidayMapper.updateHoliday(holiday);
        }
    }

    public void deleteHoliday(List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            holidayMapper.deleteHoliday(holiday);
        }
    }
}
