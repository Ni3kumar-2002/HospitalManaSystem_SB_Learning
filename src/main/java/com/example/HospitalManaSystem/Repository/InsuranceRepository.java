package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}