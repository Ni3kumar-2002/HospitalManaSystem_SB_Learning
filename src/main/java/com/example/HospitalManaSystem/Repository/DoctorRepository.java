package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    //Spring Data JPA autoMatically creates Join query
    boolean existsByUserUsername(String username);

}