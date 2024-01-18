package com.app.roster.service;

import com.app.roster.dao.LeaveRequestsMapper;
import com.app.roster.dto.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveRequestsService {

    private final LeaveRequestsMapper leaveRequestsMapper;

    @Autowired
    public LeaveRequestsService(LeaveRequestsMapper leaveRequestsMapper) {
        this.leaveRequestsMapper = leaveRequestsMapper;
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestsMapper.getAllLeaveRequests();
    }

    @Transactional
    public void addLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.addLeaveRequest(leaveRequest);
        }
    }

    @Transactional
    public void updateLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.updateLeaveRequest(leaveRequest);
        }
    }

    @Transactional
    public void deleteLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.deleteLeaveRequest(leaveRequest.getLeaveRequestID());
        }
    }
}