package com.app.roster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private int leaveRequestID;
    private int employeeID;
    private Date leaveDate;
    private String reason;
}