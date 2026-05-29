package com.example.HospitalManaSystem.Service;

import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Repository.PatientRepo;
import com.example.HospitalManaSystem.dto.PatientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Transactional
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient Not " +
                "Found with id: " + patientId));
        return new PatientResponseDto(patient.getId(), patient.getName(), patient.getGender(), patient.getBirthDate(), patient.getBloodGroup());
    }

    @PreAuthorize("hasRole('ADMIN')")  //here hasAuthority also
//    @Secured("ROLE_PATIENT")  //you can only specify role here only
    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepo.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> new PatientResponseDto(patient.getId(), patient.getName(), patient.getGender(), patient.getBirthDate(), patient.getBloodGroup()))
                .toList();
    }

    //PageRequest.of is used to create Pageable object

    public void ServicePatient(){


//        Patient p1 = patientRepo.findById(1L).orElseThrow();
//        Patient p2 = patientRepo.findById(1L).orElseThrow();
//        p1.setName("nancy");
//
//        System.out.println(p1 == p2);
//        System.out.println(p1);
//        Patient(id=1, name=nancy, email=nk@gmail.com, birthDate=2002-03-26, gender=M) but got rollback
//          Dirty checking Proof
    }

}

//        Patient p = new Patient();
//        p.setName("nan");
//        p.setBirthDate(LocalDate.parse("2001-02-18"));
//        p.setGender("F");
//        p.setEmail("nan@gamil.com");
//
//        patientRepo.save(p);
//        p.setName("Nancy");