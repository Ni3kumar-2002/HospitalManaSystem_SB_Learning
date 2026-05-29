package com.example.HospitalManaSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardNewDoctorDto {
    private String username;
    private String name;
    private String specialization;
}
