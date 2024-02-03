package com.app.roster.service;

import com.app.roster.dao.LeaveRequestsMapper;
import com.app.roster.dto.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 주어진 달에 해당하는 휴가 신청 정보를 조회합니다.
     *
     * @param date 조회하고자 하는 달 (형식: "YYYY-MM")
     * @return 해당 달의 휴가 신청 정보 목록
     */
    public List<LeaveRequest> getLeaveRequestsByMonth(String date) {
        return leaveRequestsMapper.getLeaveRequestsByMonth(date);
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

    public int checkDuplicateLeaveRequest(int employeeID, String leaveDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("employeeID", employeeID);
        params.put("leaveDate", leaveDate);
        return leaveRequestsMapper.checkDuplicateLeaveRequest(params);
    }
}
