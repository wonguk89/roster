package com.app.roster.dao;

import com.app.roster.dto.Holiday;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HolidayMapper {
    List<Holiday> getAllHolidays();

    List<Holiday> getHolidaysByMonth(String date);

    void addHoliday(Holiday holiday);

    void updateHoliday(Holiday holiday);

    void deleteHoliday(int holidayID);

    Holiday getEmployeeById(int holidayID);
}
