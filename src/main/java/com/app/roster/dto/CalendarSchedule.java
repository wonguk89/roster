package com.app.roster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarSchedule {
    private int sno;
    private String date;
    private int skillID;
    private int employeeID;
}