package com.app.roster.dao;

import com.app.roster.dto.CalendarSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarScheduleMapper {
    List<CalendarSchedule> getAllCalendarSchedule();

    List<CalendarSchedule> getCalendarScheduleByMonth(String date);

    void addCalendarSchedule(CalendarSchedule calendarSchedule);

    void updateCalendarSchedule(CalendarSchedule calendarSchedule);

    void deleteCalendarSchedule(int sno);
}
