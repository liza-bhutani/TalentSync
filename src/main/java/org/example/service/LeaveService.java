package org.example.service;

import org.example.model.LeaveRequest;
import org.example.model.User;
import org.example.repository.LeaveRequestRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public LeaveRequest applyLeave(String username, LeaveRequest leaveRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        leaveRequest.setUser(user);
        leaveRequest.setStatus("PENDING");
        leaveRequest.setEmail(user.getEmail());
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getMyLeaves(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return leaveRequestRepository.findByUser(user);
    }

    public List<LeaveRequest> getAllPendingLeaves() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    public LeaveRequest updateLeaveStatus(Long leaveId, String status) {
        LeaveRequest request = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        request.setStatus(status);
        return leaveRequestRepository.save(request);
    }
}
