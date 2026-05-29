package com.example.HospitalManaSystem.dto;

import com.example.HospitalManaSystem.Entity.type.bloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BloodGroupTypeResposeEntity {
    private bloodGroupType blood_group;
    private Long count;
}
