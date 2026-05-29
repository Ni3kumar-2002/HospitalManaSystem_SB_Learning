package com.example.HospitalManaSystem;

import com.example.HospitalManaSystem.Entity.Insurance;
import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void addInsuranceTest(){
        Insurance insurance = Insurance.builder()
                .policyNumber("Hdfc_1234")
                .provider("Hdfc")
                .validUntil(LocalDate.of(2030,1,1))
                .build();

        Patient patient = insuranceService.addInsurToPatient(insurance, 1L);
        System.out.println(patient);
    }
}

//without using Cascading this error -> Insurance not saved
//Persistent instance of 'com.example.HospitalManaSystem.Entity.Patient'
// references an unsaved transient instance of 'com.example.HospitalManaSystem.Entity.Insurance'
// (persist the transient instance before flushing) [com.example.HospitalManaSystem.Entity.Patient.insurance -> com.example.HospitalManaSystem.Entity.Insurance]