package com.app.roster.service;

import com.app.roster.dao.CalendarScheduleMapper;
import com.app.roster.dto.CalendarSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 캘린더 일정 관리를 담당하는 서비스 클래스입니다.
 */
@Service
public class CalendarScheduleService {

    private final CalendarScheduleMapper calendarScheduleMapper;

    @Autowired
    public CalendarScheduleService(CalendarScheduleMapper calendarScheduleMapper) {
        this.calendarScheduleMapper = calendarScheduleMapper;
    }

    /**
     * 모든 캘린더 일정을 조회합니다.
     *
     * @return 모든 캘린더 일정 목록
     */
    public List<CalendarSchedule> getAllCalendarSchedule() {
        return calendarScheduleMapper.getAllCalendarSchedule();
    }

    /**
     * 특정 월의 캘린더 일정을 조회합니다.
     *
     * @param date 조회할 월(YYYY-MM 형식)
     * @return 해당 월의 캘린더 일정 목록
     */
    public List<CalendarSchedule> getCalendarScheduleByMonth(String date) {
        return calendarScheduleMapper.getCalendarScheduleByMonth(date);
    }

    /**
     * 캘린더 일정을 추가합니다.
     *
     * @param calendarSchedules 추가할 캘린더 일정 목록
     */
    @Transactional
    public void addCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.addCalendarSchedule(calendarSchedule);
        }
    }

    /**
     * 캘린더 일정을 업데이트합니다.
     *
     * @param calendarSchedules 업데이트할 캘린더 일정 목록
     */
    @Transactional
    public void updateCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.updateCalendarSchedule(calendarSchedule);
        }
    }

    /**
     * 캘린더 일정을 삭제합니다.
     *
     * @param calendarSchedules 삭제할 캘린더 일정 목록
     */
    @Transactional
    public void deleteCalendarSchedule(List<CalendarSchedule> calendarSchedules) {
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            calendarScheduleMapper.deleteCalendarSchedule(calendarSchedule.getSno());
        }
    }
}
