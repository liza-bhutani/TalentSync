package org.example.controller;

import org.example.model.LeaveRequest;
import org.example.service.LeaveService;
import org.example.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private JWTService jwtService;

    private String extractUsernameFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtService.extractUsername(token);
    }

    @PostMapping("/apply")
    public LeaveRequest applyLeave(@RequestHeader("Authorization") String token,
                                   @RequestBody LeaveRequest request) {
        String username = extractUsernameFromToken(token);
        return leaveService.applyLeave(username, request);
    }

    @GetMapping("/my")
    public List<LeaveRequest> getMyLeaves(@RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);
        return leaveService.getMyLeaves(username);
    }

    @GetMapping("/pending")
    public List<LeaveRequest> getAllPending() {
        return leaveService.getAllPendingLeaves();
    }

    @PutMapping("/status/{id}")
    public LeaveRequest updateLeaveStatus(@PathVariable Long id,
                                          @RequestParam String status) {
        return leaveService.updateLeaveStatus(id, status);
    }
}
