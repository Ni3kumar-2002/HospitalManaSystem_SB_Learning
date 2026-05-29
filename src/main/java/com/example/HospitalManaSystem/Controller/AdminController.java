package com.example.HospitalManaSystem.Controller;

import com.example.HospitalManaSystem.Service.DoctorService;
import com.example.HospitalManaSystem.Service.PatientService;
import com.example.HospitalManaSystem.dto.DoctorResponseDto;
import com.example.HospitalManaSystem.dto.OnBoardNewDoctorDto;
import com.example.HospitalManaSystem.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")  //set base url for all endpoints inside controller
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    private final DoctorService doctorService;

//    /admin/patients?page=0&size=2

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnBoardNewDoctorDto onBoardNewDoctorDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onBoardNewDoctorDto));
    }
    //To make this mapping success
    //first login as User -> we already setted a default behavior that new user will be "patient"
    //then i update user table, assign myself as ADMIN
    //then called onBoardNewDoctor
    //got successful result

}
