package com.example.HospitalManaSystem.dto;

import com.example.HospitalManaSystem.Entity.type.bloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private bloodGroupType bloodGroup;
}
