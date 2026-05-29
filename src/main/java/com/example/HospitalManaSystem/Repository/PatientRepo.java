package com.example.HospitalManaSystem.Repository;

import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Entity.type.bloodGroupType;
import com.example.HospitalManaSystem.dto.BloodGroupTypeResposeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    Patient findByName(String name);

    List<Patient> findByNameStartingWith(String str);

    List<Patient> findByNameContains(String n);


    List<Patient> findByBirthDate(LocalDate birthdate);

    List<Patient> findByBirthDateOrName(LocalDate bd, String name);

    List<Patient> findByBirthDateAndName(LocalDate bd, String name);

    List<Patient> findByBirthDateAfter(LocalDate of);

    List<Patient> findByNameContainingOrderByIdDesc(String s);

    List<Patient> findByNameNotNull();

    List<Patient> findByNameNull();

    @Query("SELECT p FROM Patient p where p.bloodGroup = :bloodGroup")
    List<Patient> findByBloodGrp(@Param("bloodGroupType") bloodGroupType bloodGroup);

    @Query("select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("select p.bloodGroup, Count(p) from Patient p Group by p.bloodGroup order by Count(p) DESC")
    List<Object[]> findGroupOfBlood();

//    @Query(value = "select * from Patient order by id DESC", nativeQuery = true)
//    List<Patient> findAllPatient();

    @Query(value = "select * from patient", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update Patient p set p.name=name where p.id = :id")
    int updateTable(@Param("name") String name, @Param("id") Long id);

    @Query("select new com.example.HospitalManaSystem.dto.BloodGroupTypeResposeEntity(p.bloodGroup, Count(p)) from Patient p Group by p.bloodGroup order by Count(p) DESC")
    List<BloodGroupTypeResposeEntity> bloodGroupCountlist();

}
