package com.example.HospitalManaSystem.Service;

import com.example.HospitalManaSystem.Entity.Insurance;
import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Repository.PatientRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final PatientRepo patientRepo;

    @Transactional //so we use here @...
    public Patient addInsurToPatient(Insurance insurance, Long patient_id){
        //Now patient is in persistence state
        Patient patient = patientRepo.findById(patient_id)
                .orElseThrow(()->new EntityNotFoundException("Patient not found with patient_id"+ patient_id));

        //Updating patient //Dirty checking
        patient.setInsurance(insurance);
        return patient;
    }
}
