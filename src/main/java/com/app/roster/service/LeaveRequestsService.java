package com.app.roster.service;

import com.app.roster.dao.LeaveRequestsMapper;
import com.app.roster.dto.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 휴가 신청 정보를 관리하는 서비스 클래스입니다.
 */
@Service
public class LeaveRequestsService {

    private final LeaveRequestsMapper leaveRequestsMapper;

    @Autowired
    public LeaveRequestsService(LeaveRequestsMapper leaveRequestsMapper) {
        this.leaveRequestsMapper = leaveRequestsMapper;
    }

    /**
     * 모든 휴가 신청 정보를 조회합니다.
     *
     * @return 모든 휴가 신청 정보 목록
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestsMapper.getAllLeaveRequests();
    }

    /**
     * 휴가 신청 정보를 추가합니다.
     *
     * @param leaveRequests 추가할 휴가 신청 정보 목록
     */
    @Transactional
    public void addLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.addLeaveRequest(leaveRequest);
        }
    }

    /**
     * 휴가 신청 정보를 업데이트합니다.
     *
     * @param leaveRequests 업데이트할 휴가 신청 정보 목록
     */
    @Transactional
    public void updateLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.updateLeaveRequest(leaveRequest);
        }
    }

    /**
     * 휴가 신청 정보를 삭제합니다.
     *
     * @param leaveRequests 삭제할 휴가 신청 정보 목록
     */
    @Transactional
    public void deleteLeaveRequests(List<LeaveRequest> leaveRequests) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            leaveRequestsMapper.deleteLeaveRequest(leaveRequest.getLeaveRequestID());
        }
    }
}
