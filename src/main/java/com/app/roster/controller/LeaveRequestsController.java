package com.app.roster.controller;

import com.app.roster.dto.LeaveRequest;
import com.app.roster.service.LeaveRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/leaveRequests")
public class LeaveRequestsController {

    private final LeaveRequestsService leaveRequestsService;

    @Autowired
    public LeaveRequestsController(LeaveRequestsService leaveRequestsService) {
        this.leaveRequestsService = leaveRequestsService;
    }
    // 휴무신청 전체조회
    @GetMapping("/getAll")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestsService.getAllLeaveRequests();
    }
    // 휴무신청 월별 조회 (date = "YYYY-MM")
    @GetMapping("/getByMonth")
    public ResponseEntity<?> getLeaveRequestsByMonth(
            @RequestParam(name = "date") String date) {
        try {
            List<LeaveRequest> leaveRequests = leaveRequestsService.getLeaveRequestsByMonth(date);
            return new ResponseEntity<>(leaveRequests, HttpStatus.OK);
        } catch (Exception e) {
            // 실제로는 더 많은 디테일한 정보를 로깅하거나 분석할 수 있습니다.
            String errorMessage = "Error getting leave requests for the month.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 휴무신청 신규등록
    @PostMapping("/create")
    public ResponseEntity<String> addLeaveRequest(@RequestBody List<LeaveRequest> leaveRequests) {
        try {
            leaveRequestsService.addLeaveRequests(leaveRequests);
            return ResponseEntity.ok("Leave requests added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding leave requests: " + e.getMessage());
        }
    }
    // 휴무신청 정보수정
    @PutMapping("/update")
    public ResponseEntity<String> updateLeaveRequest(@RequestBody List<LeaveRequest> leaveRequests) {
        try {
            leaveRequestsService.updateLeaveRequests(leaveRequests);
            return ResponseEntity.ok("Leave requests updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating leave requests: " + e.getMessage());
        }
    }
    // 휴무신청 정보삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLeaveRequest(@RequestBody List<LeaveRequest> leaveRequests) {
        try {
            leaveRequestsService.deleteLeaveRequests(leaveRequests);
            return ResponseEntity.ok("Leave requests deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting leave requests: " + e.getMessage());
        }
    }
    @PostMapping("/checkAndAdd")
    public ResponseEntity<String> checkAndAddLeaveRequests(@RequestBody LeaveRequest leaveRequest) {
        try {
            // Check if a leave request with the same employeeID and leaveDate exists in the database
            if (leaveRequestsService.checkDuplicateLeaveRequest(leaveRequest.getEmployeeID(), leaveRequest.getLeaveDate()) > 0) {
                // If exists, return an error (you can customize the error message)
                return ResponseEntity.ok(leaveRequest.getLeaveDate()+" 에 이미 해당직원이 신청되어있습니다");
            } else {
                // If not exists, add the leave request
                leaveRequestsService.addLeaveRequests(Collections.singletonList(leaveRequest));
                return ResponseEntity.ok("저장되었습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking and adding leave request: " + e.getMessage());
        }
    }

}
