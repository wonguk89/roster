package com.app.roster.controller;

import com.app.roster.dto.LeaveRequest;
import com.app.roster.service.LeaveRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaveRequests")
public class LeaveRequestsController {

    private final LeaveRequestsService leaveRequestsService;

    @Autowired
    public LeaveRequestsController(LeaveRequestsService leaveRequestsService) {
        this.leaveRequestsService = leaveRequestsService;
    }

    @GetMapping("/getAll")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestsService.getAllLeaveRequests();
    }

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
}