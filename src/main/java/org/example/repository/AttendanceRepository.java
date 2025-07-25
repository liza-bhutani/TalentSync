package org.example.repository;

import org.example.model.Attendance;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndDate(User user, LocalDate date);
    List<Attendance> findAllByUser(User user);
    List<Attendance> findAll();
}
