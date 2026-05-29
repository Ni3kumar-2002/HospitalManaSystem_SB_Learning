package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}