package org.example.service;

import org.example.model.Attendance;
import org.example.model.User;
import org.example.repository.AttendanceRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    public Attendance checkIn(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setUser(user);
                    a.setDate(today);
                    return a;
                });
        attendance.setCheckInTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
                .orElseThrow(() -> new RuntimeException("Cannot check-out before check-in"));
        attendance.setCheckOutTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getMyAttendance(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return attendanceRepository.findAllByUser(user);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}
