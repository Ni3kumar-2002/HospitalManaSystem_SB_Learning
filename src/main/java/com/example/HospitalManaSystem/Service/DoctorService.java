package com.example.HospitalManaSystem.Service;

import com.example.HospitalManaSystem.Entity.Doctor;
import com.example.HospitalManaSystem.Entity.User;
import com.example.HospitalManaSystem.Entity.type.RoleType;
import com.example.HospitalManaSystem.Repository.DoctorRepository;
import com.example.HospitalManaSystem.Repository.UserRepository;
import com.example.HospitalManaSystem.dto.DoctorResponseDto;
import com.example.HospitalManaSystem.dto.OnBoardNewDoctorDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnBoardNewDoctorDto onBoardNewDoctorDto) {
        //existsByUserUsername -> join doctor table with Usertabe main column is Username
        if(doctorRepository.existsByUserUsername(onBoardNewDoctorDto.getUsername())){
            throw new IllegalArgumentException("Doctor Already Exists");
        }
        User user = User.builder()
                .username(onBoardNewDoctorDto.getUsername())
                .password(passwordEncoder.encode("12345")) //default password then we say doctor to change the Password ASAP
                .build();

        //now onBoarding
        Doctor newDoctor = Doctor.builder()
                .name(onBoardNewDoctorDto.getName())
                .specialization(onBoardNewDoctorDto.getSpecialization())
                .user(user)
                .build();

        //Now assign Role Doctor
        user.setRoles(Set.of(RoleType.DOCTOR));

        userRepository.save(user);
        doctorRepository.save(newDoctor);
        return new DoctorResponseDto(newDoctor.getId(), newDoctor.getName(), newDoctor.getSpecialization());
    }
}
