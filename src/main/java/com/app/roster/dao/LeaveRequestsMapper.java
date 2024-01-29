package com.app.roster.dao;

import com.app.roster.dto.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveRequestsMapper {
    List<LeaveRequest> getAllLeaveRequests();

    List<LeaveRequest> getLeaveRequestsByMonth(String date);

    void addLeaveRequest(LeaveRequest leaveRequest);

    void updateLeaveRequest(LeaveRequest leaveRequest);

    void deleteLeaveRequest(int leaveRequestID);
}
