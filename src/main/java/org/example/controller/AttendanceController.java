package org.example.controller;

import org.example.model.Attendance;
import org.example.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    public Attendance checkIn(Authentication auth) {
        String username = auth.getName();
        return attendanceService.checkIn(username);
    }

    @PostMapping("/check-out")
    public Attendance checkOut(Authentication auth) {
        String username = auth.getName();
        return attendanceService.checkOut(username);
    }

    @GetMapping("/my")
    public List<Attendance> myAttendance(Authentication auth) {
        String username = auth.getName();
        return attendanceService.getMyAttendance(username);
    }

    @GetMapping("/all")
    public List<Attendance> allAttendance() {
        return attendanceService.getAllAttendance();
    }
}
