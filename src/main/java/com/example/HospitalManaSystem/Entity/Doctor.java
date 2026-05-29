package com.example.HospitalManaSystem.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @MapsId
    private User user;
    //first User table m user create hoga
    //usko we'll link him to Doctor table

    @Column(nullable = false)
    private String specialization;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>(); //to initilize set to not NULL
}
