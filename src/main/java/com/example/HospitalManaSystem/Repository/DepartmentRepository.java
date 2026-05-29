package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}