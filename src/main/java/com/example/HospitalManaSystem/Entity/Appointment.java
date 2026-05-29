package com.example.HospitalManaSystem.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "Patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "Doctor_id",nullable = false)
    private Doctor doctor;
}
