package com.app.roster.service;

import com.app.roster.dto.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.roster.dao.HolidayMapper;

import java.util.List;

/**
 * 휴일 정보를 관리하는 서비스 클래스입니다.
 */
@Service
public class HolidayService {

    private final HolidayMapper holidayMapper;

    @Autowired
    public HolidayService(HolidayMapper holidayMapper) {
        this.holidayMapper = holidayMapper;
    }

    /**
     * 모든 휴일 정보를 조회합니다.
     *
     * @return 모든 휴일 정보 목록
     */
    public List<Holiday> getAllHolidays() {
        return holidayMapper.getAllHolidays();
    }

    /**
     * 선택된 달의 공휴일 목록을 조회합니다.
     *
     * @param date 선택된 달 (형식: "YYYY-MM")
     * @return 선택된 달의 공휴일 목록
     */

    public List<Holiday> getHolidaysByMonth(String date) {
        return holidayMapper.getHolidaysByMonth(date);
    }

    /**
     * 휴일 정보를 추가합니다.
     *
     * @param holidays 추가할 휴일 정보 목록
     */
    public void addHoliday(List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            holidayMapper.addHoliday(holiday);
        }
    }

    /**
     * 휴일 정보를 업데이트합니다.
     *
     * @param holidays 업데이트할 휴일 정보 목록
     */
    public void updateHoliday(List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            holidayMapper.updateHoliday(holiday);
        }
    }

    /**
     * 휴일 정보를 삭제합니다.
     *
     * @param holidays 삭제할 휴일 정보 목록
     */
    public void deleteHoliday(List<Integer> holidayIDs) {
        for (Integer holidayID : holidayIDs) {
            holidayMapper.deleteHoliday(holidayID);
        }
    }


    public Holiday getEmployeeById(int holidayID) {
        return holidayMapper.getEmployeeById(holidayID);
    }
}
