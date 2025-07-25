package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private String status; // PRESENT, HALF_DAY, ABSENT, etc.

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
