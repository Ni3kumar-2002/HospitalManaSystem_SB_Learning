package com.example.HospitalManaSystem;

import com.example.HospitalManaSystem.Entity.Patient;
import com.example.HospitalManaSystem.Entity.type.bloodGroupType;
import com.example.HospitalManaSystem.Repository.PatientRepo;
import com.example.HospitalManaSystem.Service.PatientService;
import com.example.HospitalManaSystem.dto.BloodGroupTypeResposeEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PatientTest {
    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientService patiService;


    @Test
    public void projectionWithBloodGroupCount(){
        List<BloodGroupTypeResposeEntity> countlist = patientRepo.bloodGroupCountlist();
        for(BloodGroupTypeResposeEntity c: countlist){
            System.out.println(c);
        }
    }


    @Test
    public void findByCustomQueries(){

        int updated = patientRepo.updateTable("aman", 1L);
        System.out.println(updated);
//        List<Patient> patients = patientRepo.findByBloodGrp(bloodGroupType.O_POSITIVE);
//        List<Patient> patients = patientRepo.findBornAfterDate(LocalDate.of(1988, 1,1));
//        List<Patient> patients = patientRepo.findAllPatient();
//        for (Patient patient: patients){
//            System.out.println(patient);
//        }
//        List<Object[]> pGroup = patientRepo.findGroupOfBlood();
//        for(Object[] obj: pGroup){
//            System.out.println(obj[0]+"  "+obj[1]);
//        }
    }

    @Test
    public void JPADerivedMethod(){

//        List<Patient> patients = patientRepo.findByBirthDateAfter(LocalDate.of(1991,05,05));

//        List<Patient> patients = patientRepo.findByNameContainingOrderByIdDesc("Di");
        List<Patient> patients = patientRepo.findByNameNull();
        for(Patient p: patients){
            System.out.println(p);
        }
//        List<Patient> ans = patientRepo.findByBirthDateAndName(LocalDate.of(1995,8,20), "Diya Patel");
//        List<Patient> ans = patientRepo.findByBirthDate(LocalDate.of(1995,8,20));
//        System.out.println(ans);
    }
    //        Patient p = patientRepo.findByName("Diya Patel");
//        Patient(id=41, name=Diya Patel, email=diya.patel@example.com, birthDate=1995-08-20, gender=FEMALE, createdAt=null, blood_group=A_POSITIVE)

//        List<Patient> patients = patientRepo.findByNameStartingWith("Di");
//        Patient(id=41, name=Diya Patel, email=diya.patel@example.com, birthDate=1995-08-20, gender=FEMALE, createdAt=null, blood_group=A_POSITIVE)
//        Patient(id=42, name=Disjunt Verma, email=disjunt.verma@example.com, birthDate=1988-03-15, gender=MALE, createdAt=null, blood_group=A_POSITIVE)

//        List<Patient> patients = patientRepo.findByNameContains("n");

//        System.out.println(p);


//    @Test
//    public void PatientRepoTesting() {
//        List<Patient> patient = patientRepo.findAll();
//        System.out.println(patient);
//
////        output:
////        Hibernate:
////        select
////        p1_0.id,
////                p1_0.birth_date,
////                p1_0.email,
////                p1_0.gender,
////                p1_0.name
////        from
////        patient p1_0
////[Patient(id=1, name=Nitin, email=nk@gmail.com, birthDate=2002-03-26, gender=M),
//// Patient(id=2, name=Niki, email=mn@gmail.com, birthDate=2006-07-24, gender=f)]
////    }
//
//    }
    @Test
    @Transactional
    public void AnotherTest(){
        patiService.ServicePatient();
//        Hibernate:
//        select
//        p1_0.id,
//                p1_0.birth_date,
//                p1_0.email,
//                p1_0.gender,
//                p1_0.name
//        from
//        patient p1_0
//        where
//        p1_0.id=?
//                true
    }

    @Test
    @Transactional
    public void SetingPatient(){
        Patient p = new Patient();
        p.setName("nan");
        p.setBirthDate(LocalDate.parse("2001-02-18"));
        p.setGender("F");
        p.setEmail("nan@gamil.com");

        patientRepo.save(p);
//      Before  Patient(id=8, name=nan, email=nan@gamil.com, birthDate=2001-02-18, gender=F)
        System.out.println(p);
        p.setName("Nancy");
//      After  Patient(id=8, name=Nancy, email=nan@gamil.com, birthDate=2001-02-18, gender=F)
        System.out.println(p);

//        only one insert query run with updated name, means changes reflected in Persistent
//                context's object and after at the end of method, it ocmmited

//        Hibernate:
//        insert
//                into
//        patient
//                (birth_date, email, gender, name)
//        values
//                (?, ?, ?, ?)
    }
}