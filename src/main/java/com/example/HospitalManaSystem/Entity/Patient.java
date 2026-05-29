package com.example.HospitalManaSystem.Entity;

import com.example.HospitalManaSystem.Entity.type.bloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //this value has no scope due to MapsId of user

    @Column(nullable = false, length = 40)
    private String name;

    @OneToOne
    @MapsId  //A child entity uses the parent entity’s primary key as its own primary key.
    private User user; //It'll become Primary key of Patient table


    private String email;
    private LocalDate birthDate;
    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private bloodGroupType bloodGroup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_Insurance_id")
    private Insurance insurance;

    @ToString.Exclude //Now it will not be printed, for manually way, you can override the toString()
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Appointment> appointments; //A patient can have multiple appointments
}

//@OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
//    @Override
//    public String toString() {
//        return "Patient{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", birthDate=" + birthDate +
//                ", gender='" + gender + '\'' +
//                '}';
//    }

